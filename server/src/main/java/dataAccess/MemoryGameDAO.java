package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO  implements  GameDAO{

    public static HashMap<Integer, GameData> games = new HashMap<>();
    int numGames = 0;

    public HashMap getGameMap(){
        return games;
    }



    @Override
    public void clear() {
        games.clear();
    }

    @Override
    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    @Override
    public Integer insertGame(GameData gameData) {
        numGames++;
        gameData.setGameId(numGames);
        games.put(gameData.getGameId(), gameData);
        return gameData.getGameId();
    }




}
