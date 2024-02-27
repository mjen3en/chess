package dataAccess;

import model.GameData;

import java.util.HashMap;
import java.util.List;

public interface GameDAO {


    GameData getGame(int gameID);
    Integer insertGame(GameData gameData);

    void clear();

    HashMap getGameMap();

    List getGameList();

    void updateGame(GameData gameData);

    boolean checkIfGameExists(int gameID);
}
