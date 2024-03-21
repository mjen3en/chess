package service;


import chess.ChessGame;
import dataAccess.*;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;
import result.ClearResult;
import result.CreateGameResult;
import result.JoinGameResult;
import result.ListGamesResult;

import java.util.Objects;

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

    public ClearResult clear() throws DataAccessException{
        gameDAO.clear();
        userDAO.clear();
        authDAO.clear();

        return new ClearResult("{}");

    }

    public CreateGameResult createGame(CreateGameRequest request) throws DataAccessException{
        //get authorization
        if (!(authDAO.checkAuthToken(request.authToken()))){
            throw new DataAccessException("Error: unauthorized");
        }

        if (request.gameName() == null){
            throw new DataAccessException("Error: bad request");
        }

        //create game
        GameData game = new GameData(0,null, null, request.gameName(), new ChessGame());

        //insert game and create game result with returned gameID
        return new CreateGameResult(insertGame(game));
    }

    public ListGamesResult listGames(ListGamesRequest request) throws DataAccessException{
        //get authorization
        if (!(authDAO.checkAuthToken(request.authToken()))){
            throw new DataAccessException("Error: unauthorized");
        }

        //get and return list of games
        return new ListGamesResult(gameDAO.getGameList());

    }

    public JoinGameResult joinGame(JoinGameRequest request, String authToken) throws DataAccessException{
        //check authorization
        if (!(authDAO.checkAuthToken(authToken))){
            throw new DataAccessException("Error: unauthorized");
        }

        //check if game exists
        if (!(gameDAO.checkIfGameExists(request.gameID()))){
            throw new DataAccessException("Error: bad request");
        }


        // get username
        String username = authDAO.getAuthData(authToken).getUsername();

        //get game
       var updatedGame = gameDAO.getGame(request.gameID());

        //join game
        setColor(request, updatedGame, username);

        //update game
        gameDAO.updateGame(updatedGame);
        return new JoinGameResult();

    }

    public int insertGame(GameData game) throws DataAccessException {
        return gameDAO.insertGame(game);
    }

    public int getMapSize() throws DataAccessException {
        return gameDAO.getGameMap().size();
    }

    private void setColor(JoinGameRequest request, GameData updatedGame, String username) throws DataAccessException{
        if (Objects.equals(request.playerColor(), "WHITE")) {
            if (!(Objects.equals(updatedGame.getWhiteUsername(),null))){
                throw new DataAccessException("Error: already taken");
            }
            updatedGame.setWhiteUsername(username);
        } else if (Objects.equals(request.playerColor(), "BLACK")){
            if (!(Objects.equals(updatedGame.getBlackUsername(),null))){
                throw new DataAccessException("Error: already taken");
            }
            updatedGame.setBlackUsername(username);
        } else if (!(Objects.equals(request.playerColor(), null))){
            throw new DataAccessException("Error: bad request");
        }
    }
}
