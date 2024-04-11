package server.Websocket;

import chess.*;
import com.google.gson.Gson;
import dataAccess.*;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Objects;

@WebSocket
public class WebsocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    public UserGameCommand action;

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        action = new Gson().fromJson(message, UserGameCommand.class);
        try {
            switch (action.getCommandType()) {
                // implement user commands
                case JOIN_PLAYER -> joinGame(action, session);
                //case JOIN_OBSERVER ->
                case MAKE_MOVE -> makeMove(action, session);
                case LEAVE -> leaveGame(action, session);
                // resign
            }
        } catch(Exception ex){
            var errorMessage = new ErrorMessage("ERROR" + ex.getMessage());
            session.getRemote().sendString(new Gson().toJson(errorMessage));

        }

    }

    public void errorMessage(Exception ex){
        String notify = ex.getMessage();
        try {
            String username = getUsername(action);
            String message = "Error: " + notify;
            var notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);

            connections.messageForYou(username, notification);
        } catch(Exception exception){
            // idk
        }
    }

    private void makeMove(UserGameCommand action, Session session) throws DataAccessException, InvalidMoveException, IOException {
        Integer gameID = action.getGameID();
        ChessMove move = action.getMove();

        //check auth
        checkAuth(action.getAuthString(), session);
        String username = getUsername(action);

        GameDAO gDAO = new MySQLGameDAO();
        GameData data = gDAO.getGame(gameID);
        ChessGame game = data.getGame();
        ChessPiece piece = game.getBoard().getPiece(move.getStartPosition());

        //check opponent
        if (data.getWhiteUsername() == null || data.getBlackUsername() == null){
            var errorMessage = new ErrorMessage("ERROR: Can't move without opponent");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }

        game.getBoard().refreshPieceMaps();
        game.makeMove(action.getMove());


        //updateGame
        GameData updatedGame = new GameData(data.getGameID(), data.getWhiteUsername(), data.getBlackUsername(), data.getGameName(), game);
        gDAO.updateGame(updatedGame);

        //send load game
        var loadGameMessage = new LoadGameMessage(updatedGame.getGame());
        connections.broadcast("", loadGameMessage);

        //send notification
        String start = move.getStartPosition().toString();
        String end = move.getEndPosition().toString();
        String message = String.format("%1s moved %2s from %3s to %4s", username, piece.toString(), start, end);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast("", notification);

    }

    private void leaveGame(UserGameCommand action, Session session) throws DataAccessException, IOException {
        checkAuth(action.getAuthString(), session);
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
        var message = String.format("%s has left the game", toDelete);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(toDelete, notification);


    }

    private void joinGame(UserGameCommand action,  Session session) throws IOException, DataAccessException {
        String visitorName = getUsername(action);
        //check authToken
        checkAuth(action.getAuthString(), session);
        //check joined
        String message = String.format("%s has entered the game", visitorName);
        //if observing
        if (action.getColor() == null){
            connections.add(visitorName, session);
            message = String.format("%s is observing the game", visitorName);
        } else {

            verifyJoined(visitorName, action.getGameID(), action.getColor(), session);
            connections.add(visitorName, session);
        }

        //send LOAD_GAME thing
        var loadNotification = new LoadGameMessage(getGame(action.getGameID()));
        //loadNotification.setGame(getGame(action.getGameID()));
        connections.broadcast("", loadNotification);

        // notify other players/observers
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(visitorName, notification);

    }

    private void checkAuth(String authToken, Session session) throws DataAccessException, IOException {
        var dao = new MySQLAuthDAO();
        if (!dao.checkAuthToken(authToken)){
            var errorMessage = new ErrorMessage("ERROR: Unauthorized");
            session.getRemote().sendString(new Gson().toJson(errorMessage));

        }

    }

    public void verifyJoined(String visitorName, int gameId, ChessGame.TeamColor color, Session session) throws DataAccessException, IOException {
        var dao = new MySQLGameDAO();
        GameData gameData = dao.getGame(gameId);
        switch(color){
            case WHITE -> checkWhiteUsername(visitorName, gameData, session);
            case BLACK -> checkBlackUsername(visitorName, gameData, session);
        }
    }

    private void checkWhiteUsername(String visitorName, GameData gameData, Session session) throws IOException {
        if(!Objects.equals(gameData.getWhiteUsername(), visitorName)){
            throw new IOException("Unauthorized");
        }

    }

    private void checkBlackUsername(String visitorName, GameData gameData, Session session) throws IOException {
        if(!Objects.equals(gameData.getBlackUsername(), visitorName)){
            throw new IOException("Unauthorized");
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
        if (oldName == null){
            return null;
        }

        if (oldName.equals(toDelete)){
            return null;
        }
        return oldName;

    }

}
