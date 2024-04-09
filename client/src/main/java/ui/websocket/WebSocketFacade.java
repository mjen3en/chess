package ui.websocket;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

import chess.ChessGame;
import com.google.gson.Gson;
import ui.PrintBoard;
import ui.ResponseException;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;


public class WebSocketFacade extends Endpoint {

    private final Session session;
    NotificationHandler notificationHandler;

    public ChessGame currentGame;

    String playerColor;

    String visitorName;

    Boolean updateNeeded;

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
                    LoadGameMessage notification = new Gson().fromJson(message, LoadGameMessage.class);
                    switch (notification.getServerMessageType()){
                        case LOAD_GAME -> reloadBoard(notification);
                        case NOTIFICATION -> sendNotification(notification);
                        //case ERROR ->;
                    }

                    //notificationHandler.notify();
                }
            });
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void joinGame(String authToken, int gameID, String playerColor) throws ResponseException {
        this.playerColor = playerColor;
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

    private void reloadBoard(LoadGameMessage notification){
        //saves the board
        currentGame = notification.game;

        //redraws the board
        var pb = new PrintBoard(currentGame.getBoard(), playerColor, null);
        System.out.println();
        pb.drawBoard();
        System.out.println(notification.message());
    }

    private void sendNotification(ServerMessage notification){
        System.out.print(notification.message());

    }

    public void setCurrentGame(ChessGame game){
        currentGame = game;
    }


    public ChessGame getCurrentGame(){ return currentGame;}
}
