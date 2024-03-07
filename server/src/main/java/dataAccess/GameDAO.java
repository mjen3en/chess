package dataAccess;

import model.GameData;

import java.util.HashMap;
import java.util.List;

public interface GameDAO {


    GameData getGame(int gameID) throws DataAccessException;
    Integer insertGame(GameData gameData) throws DataAccessException;

    void clear() throws DataAccessException;

    HashMap getGameMap() throws DataAccessException;

    List getGameList() throws DataAccessException;

    void updateGame(GameData gameData) throws DataAccessException;

    boolean checkIfGameExists(int gameID) throws DataAccessException;
}
