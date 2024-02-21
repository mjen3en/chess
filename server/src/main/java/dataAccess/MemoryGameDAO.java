package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Map;

public class MemoryGameDAO  implements  GameDAO{

    Map<Integer, GameData> games;



    @Override
    public void delete() {
        games.clear();
    }

    @Override
    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    @Override
    public Integer insertGame(GameData gameData) {
        gameData.setGameId(games.size() + 1);
        games.put(gameData.getGameId(), gameData);

        return gameData.getGameId();
    }


}
