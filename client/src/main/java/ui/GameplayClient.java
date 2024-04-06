package ui;

import chess.ChessGame;
import chess.ChessPosition;
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
            ws = new WebSocketFacade(serverURL);

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
        var tokens = input.toLowerCase().split(" " );
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
        int col = 1;
        int row = Integer.parseInt(params[1]);
        switch (params[0]) {
            case "a" -> col = 1;
            case "b" -> col = 2;
            case "c" -> col = 3;
            case "d" -> col = 4;
            case "e" -> col = 5;
            case "f" -> col = 6;
            case "g" -> col = 7;
            case "h" -> col = 8;
        }

        ChessPosition position = new ChessPosition(row, col);

        PrintBoard pb = new PrintBoard(ws.currentGame.getBoard(), playerColor, position);
        pb.drawBoard();

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
        PrintBoard board = new PrintBoard(ws.currentGame.getBoard(),playerColor, null);
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
                legalmoves <ROW,COLUMN> - watch a game on the server
                """;
    }

    @Override
    public String getAuth() {
        return null;
    }
}
