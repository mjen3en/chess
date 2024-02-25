package dataAccess;

import model.GameData;

import java.util.HashMap;
import java.util.List;

public interface GameDAO {


    GameData getGame(int gameID);
//    void joinGame();
//    void updateGame();
//    AuthData getAuth();
//
    //GameData createGame(String gameName);
    Integer insertGame(GameData gameData);
//    List<ChessGame> listGames();

    void clear();

    HashMap getGameMap();

    List getGameList();
}
