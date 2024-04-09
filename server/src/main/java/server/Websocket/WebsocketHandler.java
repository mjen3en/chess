package server.Websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.*;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Objects;

@WebSocket
public class WebsocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            // implement user commands
            // join_player
            case JOIN_PLAYER -> joinGame(action, session);
            // make_move
            // leave
            case LEAVE -> leaveGame(action, session);
            // resign
        }
    }

    private void leaveGame(UserGameCommand action, Session session) throws DataAccessException, IOException {
        checkAuth(action.getAuthString());
        GameDAO gDAO = new MySQLGameDAO();
        GameData oldGame = gDAO.getGame(action.getGameID());

        String toDelete = getUsername(action);

        //checks to see if user is white or black
        String whiteUsername = compareUsernames(toDelete, oldGame.getWhiteUsername());
        String blackUsername = compareUsernames(toDelete, oldGame.getBlackUsername());

        //update game
        GameData updatedGame = new GameData(action.getGameID(), whiteUsername, blackUsername, oldGame.getGameName(), oldGame.getGame());
        gDAO.updateGame(updatedGame);

        //send notification
        var message = String.format("%s has entered the game", toDelete);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(toDelete, notification);



    }

    private void joinGame(UserGameCommand action,  Session session) throws IOException, DataAccessException {
        String visitorName = getUsername(action);
        //check authToken
        checkAuth(action.getAuthString());
        //check joined
        verifyJoined(visitorName, action.getGameID(), action.getColor());
        connections.add(visitorName, session);

        //send LOAD_GAME thing
        var loadNotification = new LoadGameMessage(getGame(action.getGameID()));
        //loadNotification.setGame(getGame(action.getGameID()));
        connections.broadcast(visitorName, loadNotification);

        // notify other players/observers
        var message = String.format("%s has entered the game", visitorName);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(visitorName, notification);

    }

    private void checkAuth(String authToken) throws DataAccessException {
        var dao = new MySQLAuthDAO();
        if (!dao.checkAuthToken(authToken)){
            throw new DataAccessException("Unauthorized");
        }

    }

    public void verifyJoined(String visitorName, int gameId, ChessGame.TeamColor color) throws DataAccessException, IOException {
        var dao = new MySQLGameDAO();
        GameData gameData = dao.getGame(gameId);
        switch(color){
            case WHITE -> checkWhiteUsername(visitorName, gameData);
            case BLACK -> checkBlackUsername(visitorName, gameData);
        }
    }

    private void checkWhiteUsername(String visitorName, GameData gameData) throws IOException {
        if(!Objects.equals(gameData.getWhiteUsername(), visitorName)){
            throw new IOException();
        }
    }

    private void checkBlackUsername(String visitorName, GameData gameData) throws IOException {
        if(!Objects.equals(gameData.getBlackUsername(), visitorName)){
            throw new IOException();
        }
    }

    private ChessGame getGame(Integer gameID) throws DataAccessException {
        var dao = new MySQLGameDAO();
        GameData gameData = dao.getGame(gameID);
        return gameData.getGame();
    }

    private String getUsername(UserGameCommand action) throws DataAccessException {
        AuthDAO aDAO = new MySQLAuthDAO();
        return aDAO.getAuthData(action.getAuthString()).username;
    }

    private String compareUsernames(String toDelete, String oldName){
        if (oldName.equals(toDelete)){
            return null;
        }
        return oldName;

    }
}
