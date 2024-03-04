package dataAccess;

import model.AuthData;

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
    public HashMap getAuthMap() {
        return null;
    }

    @Override
    public AuthData getAuthData(String authToken) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {
        return;
    }

    @Override
    public boolean checkAuthToken(String authToken) {
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
              `authtoken` varchar(256) ',
              PRIMARY KEY (`authtoken`),
              INDEX(username),
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

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
