package service;


import chess.ChessGame;
import dataAccess.GameDAO;
import dataAccess.MemoryGameDAO;
import model.GameData;

public class GameService {
    //MemoryGameDAO gameDao = new MemoryGameDAO();

    private final GameDAO gameDAO;

    public GameService(GameDAO gDAO) {
        gameDAO = gDAO;
    }

    public void clear(){
        gameDAO.clear();
    }

    public int createGame(){
        //get authorization

        //create game

        //insert game

        return 0;
    }

    public int insertGame(GameData game){
        return gameDAO.insertGame(game);
    }

    public int getMapSize(){
        return gameDAO.getMapSize();
    }
}
