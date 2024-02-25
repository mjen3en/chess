package service;


import chess.ChessGame;
import dataAccess.*;
import model.GameData;
import request.CreateGameRequest;
import request.ListGamesRequest;
import result.ClearResult;
import result.CreateGameResult;
import result.ListGamesResult;

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

        return new ClearResult("{}");

    }

    public CreateGameResult createGame(CreateGameRequest request, String authToken) throws DataAccessException{
        //get authorization
        if (!(authDAO.checkAuthToken(authToken))){
            throw new DataAccessException("unauthorized");
        }
        //create game
        GameData game = new GameData(0,"", "", request.gameName(), new ChessGame());
        //game.setGameName(request.gameName());

        //insert game and create game result with returned gameID
        return new CreateGameResult(insertGame(game));
    }

    public ListGamesResult listGames(ListGamesRequest request, String authToken) throws DataAccessException{
        //get authorization
        if (!(authDAO.checkAuthToken(authToken))){
            throw new DataAccessException("unauthorized");
        }

        //get and return list of games
        return new ListGamesResult(gameDAO.getGameList());

    }

    public int insertGame(GameData game){
        return gameDAO.insertGame(game);
    }

    public int getMapSize(){
        return gameDAO.getGameMap().size();
    }
}
