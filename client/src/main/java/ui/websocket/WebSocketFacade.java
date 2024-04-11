package ui.websocket;

import javax.websocket.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import ui.PrintBoard;
import ui.ResponseException;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import static ui.EscapeSequences.*;


public class WebSocketFacade extends Endpoint {

    private final Session session;
    NotificationHandler notificationHandler;

    static public ChessGame currentGame;

    String playerColor;

    String authToken;

    int gameID;

    ChessGame.TeamColor color;

    public WebSocketFacade(String url, String authToken, String playerColor, Integer gameID) throws ResponseException {
        this.authToken = authToken;
        this.playerColor = playerColor;
        this.gameID = gameID;

        color = translateColor(playerColor);

        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            //this.notificationHandler = notificationHandler;


            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.setDefaultMaxSessionIdleTimeout(5 * 60 * 1000);
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    LoadGameMessage notification = new Gson().fromJson(message, LoadGameMessage.class);
                    switch (notification.getServerMessageType()){
                        case LOAD_GAME -> reloadBoard(notification);
                        case NOTIFICATION -> sendNotification(notification);
                        case ERROR -> sendError(notification);
                    }

                    //notificationHandler.notify();
                }
            });
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void sendError(LoadGameMessage notification) {
        System.out.println();
        System.out.print(SET_TEXT_COLOR_RED);
        System.out.println(notification.message());
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print("\n" + ">>> ");

    }

    public void joinGame() throws ResponseException {
        try {
            var command = new UserGameCommand(authToken, UserGameCommand.CommandType.JOIN_PLAYER, color, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException ex){
            throw new ResponseException(500, ex.getMessage());
        }

    }

    public void leaveGame() throws ResponseException {
        try {
            var command = new UserGameCommand(authToken, UserGameCommand.CommandType.LEAVE, color, gameID);
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
        System.out.println();
        System.out.print(SET_TEXT_COLOR_GREEN);
        System.out.print(notification.message());
        System.out.print((SET_TEXT_COLOR_WHITE));
        System.out.print("\n" + ">>> ");


    }

    public void setCurrentGame(ChessGame game){
        currentGame = game;
    }

    public void makeMove(ChessMove move) throws ResponseException {
        //check turns
//        ChessGame.TeamColor turn = currentGame.getTeamTurn();
//        if (turn != color){
//            throw new ResponseException(500, "not authorized to act now");
//        }

        //check move color
        ChessGame.TeamColor pieceColor = currentGame.getBoard().getPiece(move.getStartPosition()).getTeamColor();

        if(pieceColor != color){
            //get mad
            throw new ResponseException(500, "not authorized to move piece");
        }


        try {
            var command = new UserGameCommand(authToken, UserGameCommand.CommandType.MAKE_MOVE, color, gameID);
            command.setMove(move);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException ex){
            throw new ResponseException(500, ex.getMessage());
        }


    }



    public ChessGame getCurrentGame(){ return currentGame;}

    private static void setTextGreen(PrintStream out) {
        //out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_GREEN);
    }


}
