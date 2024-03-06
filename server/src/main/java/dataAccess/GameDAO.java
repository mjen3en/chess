package dataAccess;

import model.GameData;

import java.util.HashMap;
import java.util.List;

public interface GameDAO {


    GameData getGame(int gameID) throws DataAccessException;
    Integer insertGame(GameData gameData) throws DataAccessException;

    void clear() throws DataAccessException;

    HashMap getGameMap();

    List getGameList();

    void updateGame(GameData gameData);

    boolean checkIfGameExists(int gameID);
}
