package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLGameDAO implements GameDAO{

    public MySQLGameDAO() throws DataAccessException {
        configureDatabase();
    }

    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }
    public Integer insertGame(GameData gameData) throws DataAccessException {
        var gson = new Gson();
        var game = gson.toJson(gameData.getGame());
        var statement = "INSERT INTO game (whiteusername, blackusername, gamename, game) VALUES (?, ?, ?, ?)";
        int id = executeUpdate(statement, gameData.getWhiteUsername(), gameData.getBlackUsername(), gameData.getGameName(), game);
        return id;
    }

    public void clear() throws DataAccessException {
        var statement = "TRUNCATE auth";
        executeUpdate(statement);
    }

    public HashMap getGameMap(){return null;}

    public List getGameList(){return null;}

    public void updateGame(GameData gameData){}

    public boolean checkIfGameExists(int gameID){return false;}

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  game (
              `id` int NOT NULL AUTO_INCREMENT,
	          `whiteusername` varchar(256),
              `blackusername` varchar(256),
              'gamename' varchar(256),
              'game' TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(whiteusername),
              INDEX(blackusername),
              INDEX(gamename),
              INDEX(game)
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

    private GameData readGame(ResultSet rs) throws SQLException {
        int gameID = rs.getInt("gameid");
        String wUser = rs.getString("whiteusername");
        String bUser = rs.getString("blackusername");
        String gameName = rs.getString("gamename");
        var json = new Gson();
        var game = json.fromJson(rs.getString("game"), ChessGame.class);
        return new GameData(gameID, wUser, bUser, gameName, game);

    }
}
