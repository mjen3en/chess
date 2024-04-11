package ui;

import chess.ChessGame;
import ui.websocket.NotificationHandler;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;




public class Repl implements NotificationHandler {

    public static State state = State.SIGNEDOUT;
    private PreLoginClient preClient;
    public PostLoginClient postClient;

    public GameplayClient gameplayClient;

    String authToken;

    String serverURL;

    public Repl(String serverURL) {
        preClient = new PreLoginClient(serverURL);
        this.serverURL = serverURL;


    }

    public void run() {
        resetText(System.out);
        System.out.println(EscapeSequences.BLACK_KING + " Welcome to chess. Register to start");


        var result = "";

        while (!result.equals("Be seeing you")) {
            switch (state) {
                case SIGNEDOUT -> result = userInterface(preClient);
                case SIGNEDIN -> result = userInterface(postClient);
                case INGAME -> result = userInterface(gameplayClient);
            }
            if (state == State.SIGNEDIN){
                postClient = new PostLoginClient(serverURL, preClient.getAuth());
            }
        }

        System.out.println();
    }


    private void printPrompt() {
        System.out.print("\n" + ">>> ");
    }

//    turn while loop into function Pre Login U1
//     make UI function generic and pass in client as argument?

    private String userInterface(Client client) {
        System.out.print(client.help());
        Scanner scanner = new Scanner(System.in);
        var result = "";
        printPrompt();
        String line = scanner.nextLine();

        try {
            result = client.eval(line);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + result + EscapeSequences.SET_TEXT_COLOR_WHITE + "\n");
        } catch (Throwable e) {
            state = State.SIGNEDOUT;
            var msg = e.toString();
            System.out.print(msg);
        }

        if (Objects.equals(result, "You left the game")){
            state = State.SIGNEDIN;
        }

        if (Objects.equals(result, "Join Game Successful")){
            state = State.INGAME;
            gameplayClient = new GameplayClient(serverURL, this, postClient.getCurrentGame(), postClient.getVisitorColor(), postClient.authToken, postClient.trueGameID);
        }

        return result;

    }

    public void resetText(PrintStream out){
        out.println(EscapeSequences.SET_BG_COLOR_BLACK);
        out.println(EscapeSequences.SET_TEXT_COLOR_WHITE);
    }


    @Override
    public void notify(ServerMessage notification) {
        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + notification.message());
        printPrompt();

    }



}
