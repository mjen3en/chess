package dataAccess;

import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemoryGameDAO  implements  GameDAO{

    public static HashMap<Integer, GameData> gameMap = new HashMap<>();
    int numGames = 0;

    public HashMap getGameMap(){
        return gameMap;
    }


    @Override
    public void clear() {
        gameMap.clear();
    }

    @Override
    public GameData getGame(int gameID) {
        return gameMap.get(gameID);
    }

    @Override
    public Integer insertGame(GameData gameData) {
        numGames++;
        GameData newData = new GameData(numGames, gameData.getWhiteUsername(), gameData.getBlackUsername(), gameData.getGameName(), gameData.getGame());
        gameMap.put(newData.getGameID(), newData);
        return newData.getGameID();


    }


    @Override
    public List getGameList() {
        return new ArrayList<GameData>(gameMap.values());
    }
}
