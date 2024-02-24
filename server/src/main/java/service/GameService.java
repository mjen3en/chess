package service;


import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.UserDAO;
import model.GameData;
import result.ClearResult;

public class GameService {
    //MemoryGameDAO gameDao = new MemoryGameDAO();

    private final GameDAO gameDAO;
    private final UserDAO userDAO;

    private final AuthDAO authDAO;

    public GameService(GameDAO gDAO, UserDAO uDAO, AuthDAO aDAO) {
        gameDAO = gDAO;
        userDAO = uDAO;
        authDAO = aDAO;
    }

    public ClearResult clear(){
        gameDAO.clear();
        userDAO.clear();
        authDAO.clear();

        return new ClearResult("");

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
        return gameDAO.getGameMap().size();
    }
}
