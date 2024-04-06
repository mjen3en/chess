package ui.websocket;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

import chess.ChessGame;
import com.google.gson.Gson;
import ui.ResponseException;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;


public class WebSocketFacade extends Endpoint {

    private final Session session;
    NotificationHandler notificationHandler;

    public ChessGame currentGame;

    String visitorName;

    public WebSocketFacade(String url) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            //this.notificationHandler = notificationHandler;


            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
                    switch (notification.getServerMessageType()){
                        case LOAD_GAME -> reloadBoard(notification);
                        //case NOTIFICATION -> ;
                        //case ERROR ->;
                    }

                    notificationHandler.notify();
                }
            });
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void joinGame(String authToken, int gameID, String playerColor) throws ResponseException {
        var color = translateColor(playerColor);
        try {
            var command = new UserGameCommand(authToken, UserGameCommand.CommandType.JOIN_PLAYER, color, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException ex){
            throw new ResponseException(500, ex.getMessage());
        }

    }

    public ChessGame.TeamColor translateColor (String playerColor) {
        ChessGame.TeamColor x;
        switch(playerColor){
            case "white" -> x = ChessGame.TeamColor.WHITE;
            case "black" -> x = ChessGame.TeamColor.BLACK;
            default -> x = null;
        }
        return x;
    }


    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        
    }

    private void reloadBoard(ServerMessage notification){
        //somehow reloads the board
        currentGame = notification.getGame();

        //tells GamePlayClient to redraw


    }

    public void setCurrentGame(ChessGame game){
        currentGame = game;
    }

    public ChessGame getCurrentGame(){ return currentGame;}
}
