package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import org.junit.jupiter.api.AfterAll;
import ui.websocket.WebSocketFacade;

import java.util.Arrays;

public class GameplayClient implements Client{
    WebSocketFacade ws;

    //ChessGame currentGame;
    Integer gameID;

    String playerColor;
    String authToken;



    public GameplayClient(String serverURL, ui.websocket.NotificationHandler notificationHandler, ChessGame startingGame, String playerColor, String authToken, Integer gameID) {
        try {
            ws = new WebSocketFacade(serverURL, authToken, playerColor, gameID);

            //this.currentGame = startingGame;
            ws.setCurrentGame(startingGame);

            this.authToken = authToken;
            this.gameID = gameID;
            //wsFacade.joinGame(authToken, );
        } catch (Exception ex){
            var msg = ex.toString();
            System.out.print(msg);
        }
        //currentGame = startingGame;
        this.playerColor = playerColor;

    }
    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> redraw();
                case "leave" -> leave();
                case "move" -> makeMove(params);
                case "resign" -> resign();
                case "legalmoves" -> showLegalMoves(params);
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private String showLegalMoves(String[] params) {
        int col = translateLet(params[0]);
        int row = Integer.parseInt(params[1]);

        ChessPosition position = new ChessPosition(row, col);

        PrintBoard pb = new PrintBoard(ws.currentGame.getBoard(), playerColor, position);
        pb.drawBoard();

        return "";
    }

    private String resign() {
        return "";
    }

    private String makeMove(String[] params) throws ResponseException {
        int col = translateLet(params[0]);
        int row = Integer.parseInt(params[1]);
        ChessPosition start = new ChessPosition(row, col);
        int col2 = translateLet(params[2]);
        int row2 = Integer.parseInt(params[3]);
        ChessPosition end = new ChessPosition(row2, col2);

        ChessMove move = new ChessMove(start, end, null);

        ws.makeMove(move);

        return "";
    }

    private String leave() throws ResponseException {
        ws.leaveGame();

        return "You left the game";
    }

    private String redraw() {
        PrintBoard board = new PrintBoard(ws.currentGame.getBoard(),playerColor, null);
        board.drawBoard();
        return "";
    }


    @Override
    public String help() {
        return """
                redraw - redraw the current chessboard
                leave - leave the game
                move <COLUMN ROW> <COLUMN ROW> - make a chess move
                resign - give up
                legalmoves <COLUMN ROW> - watch a game on the server
                """;
    }

    @Override
    public String getAuth() {
        return null;
    }


    private Integer translateLet(String i){
        Integer col = 0;
        switch (i) {
            case "a" -> col = 1;
            case "b" -> col = 2;
            case "c" -> col = 3;
            case "d" -> col = 4;
            case "e" -> col = 5;
            case "f" -> col = 6;
            case "g" -> col = 7;
            case "h" -> col = 8;
        }
        return col;
    }
}
