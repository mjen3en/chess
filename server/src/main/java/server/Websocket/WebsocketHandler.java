package server.Websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.MySQLAuthDAO;
import dataAccess.MySQLGameDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
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
            // resign
        }
    }

    private void joinGame(UserGameCommand action,  Session session) throws IOException, DataAccessException {
        AuthDAO aDAO = new MySQLAuthDAO();
        String visitorName =  aDAO.getAuthData(action.getAuthString()).username;
        //check authToken
        checkAuth(action.getAuthString());
        //check joined
        verifyJoined(visitorName, action.getGameID(), action.getColor());
        connections.add(visitorName, session);

        //send LOAD_GAME thing
        var loadNotification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, "");
        loadNotification.setGame(getGame(action.getGameID()));
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
}
