package dataAccess;

import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLAuthDAO implements  AuthDAO{

    public MySQLAuthDAO() throws DataAccessException{
        configureDatabase();
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE auth";
        executeUpdate(statement);
    }

    @Override
    public String insertAuth(String username) throws DataAccessException {
        String authToken = createAuthToken();
        var statement = "INSERT INTO auth (username, authtoken) VALUES (?, ?)";
        executeUpdate(statement, username, authToken);
        return authToken;
    }

    @Override
    public HashMap getAuthMap() throws DataAccessException {
        var map = new HashMap<String, AuthData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authtoken, username FROM auth";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        map.put(rs.getString("authtoken"), readAuth(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return map;
    }

    @Override
    public AuthData getAuthData(String authToken) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authtoken, username FROM auth WHERE authtoken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        var statement = "DELETE FROM auth WHERE authtoken=?";
        executeUpdate(statement, authToken);
    }

    @Override
    public boolean checkAuthToken(String authToken) throws DataAccessException{
        var authData = getAuthData(authToken);
        if (authData != null){
            return true;
        }
        return false;
    }

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }

    private String createAuthToken(){
        return UUID.randomUUID().toString();
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `username` varchar(256),
              `authtoken` varchar(256),
              PRIMARY KEY (authtoken),
              INDEX(username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var authToken = rs.getString("authtoken");
        var username = rs.getString("username");
        var auth = new AuthData();
        auth.setAuthToken(authToken);
        auth.setUsername(username);
        return auth;
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                        //else if (param instanceof  p) ps.setString(i + 1, p.toString());
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }
}
