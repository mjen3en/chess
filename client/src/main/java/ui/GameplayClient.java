package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import com.sun.nio.sctp.HandlerResult;
import com.sun.nio.sctp.Notification;
import com.sun.nio.sctp.NotificationHandler;
import ui.websocket.WebSocketFacade;

import java.util.Arrays;

public class GameplayClient implements Client{
    WebSocketFacade wsFacade;

    ChessGame currentGame;

    String playerColor;

    public GameplayClient(String serverURL, ui.websocket.NotificationHandler notificationHandler, ChessGame startingGame, String playerColor ) {
//        try {
//            wsFacade = new WebSocketFacade(serverURL, notificationHandler);
//        } catch (Exception ex){
//            var msg = ex.toString();
//            System.out.print(msg);
//        }
        currentGame = startingGame;
        this.playerColor = playerColor;

    }
    @Override
    public String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "redraw" -> redraw();
            case "leave" -> leave();
            case "makemove" -> makeMove(params);
            case "resign" -> resign();
            case "legalmoves" -> showLegalMoves(params);
            default -> help();
        };
    }

    private String showLegalMoves(String[] params) {
        return "";
    }

    private String resign() {
        return "";
    }

    private String makeMove(String[] params) {
        return "";
    }

    private String leave() {
        return "";
    }

    private String redraw() {
        PrintBoard board = new PrintBoard(currentGame.getBoard(),playerColor);
        board.drawBoard();
        return "";
    }

    @Override
    public String help() {
        return """
                redraw - redraw the current chessboard
                leave - leave the game
                makemove - make a chess move
                resign - give up
                legalmoves <POSITION> - watch a game on the server
                """;
    }

    @Override
    public String getAuth() {
        return null;
    }
}
