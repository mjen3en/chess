package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.GameData;
import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLGameDAO implements GameDAO{

    public MySQLGameDAO() throws DataAccessException {
        configureDatabase();
    }

    public GameData getGame(int gameID) throws DataAccessException {
        //FIX ME
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id, whiteusername, blackusername, gamename, game  FROM games WHERE id=?";
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
        var statement = "INSERT INTO games (whiteusername, blackusername, gamename, game) VALUES (?, ?, ?, ?)";
        int id = executeUpdate(statement, gameData.getWhiteUsername(), gameData.getBlackUsername(), gameData.getGameName(), game);
        return id;
    }

    public void clear() throws DataAccessException {
        var statement = "TRUNCATE games";
        executeUpdate(statement);
    }

    public HashMap getGameMap() throws DataAccessException{
        var map = new HashMap<Integer, GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id, whiteusername, blackusername, gamename, game FROM games";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        map.put(rs.getInt("id"), readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return map;
    }

    public List getGameList() throws DataAccessException {
        return new ArrayList<GameData>(getGameMap().values());
    }

    public void updateGame(GameData gameData) throws DataAccessException {
        var gson = new Gson();
        var game = gson.toJson(gameData.getGame());
        var statement = "UPDATE games SET whiteusername =?, blackusername = ?, game =? WHERE id =?";
        executeUpdate(statement, gameData.getWhiteUsername(), gameData.getBlackUsername(), game, gameData.getGameID());
    }

    public boolean checkIfGameExists(int gameID) throws DataAccessException {
        HashMap<Integer, GameData> gameMap = getGameMap();
        if (gameMap.get(gameID) != null){
            return true;
        }
        return false;
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `id` int NOT NULL AUTO_INCREMENT,
	          `whiteusername` varchar(256),
              `blackusername` varchar(256),
              `gamename` varchar(256),
              `game` json DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(whiteusername),
              INDEX(blackusername),
              INDEX(gamename)
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
        int gameID = rs.getInt("id");
        String wUser = rs.getString("whiteusername");
        String bUser = rs.getString("blackusername");
        String gameName = rs.getString("gamename");
        var json = new Gson();
        var game = json.fromJson(rs.getString("game"), ChessGame.class);
        return new GameData(gameID, wUser, bUser, gameName, game);

    }
}
