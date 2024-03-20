package ui;

import com.sun.nio.sctp.HandlerResult;
import com.sun.nio.sctp.Notification;
import com.sun.nio.sctp.NotificationHandler;

import java.io.PrintStream;
import java.util.Scanner;




public class Repl {

    public static State state = State.SIGNEDOUT;
    private PreLoginClient preClient;
    private PostLoginClient postClient;

    public Repl(String serverURL) {
        preClient = new PreLoginClient(serverURL);
        postClient = new PostLoginClient(serverURL);


    }

    public void run() {
        resetText(System.out);
        System.out.println(EscapeSequences.BLACK_KING + " Welcome to chess. Register to start");


        var result = "";

        while (!result.equals("quit")) {
            switch (state) {
                case SIGNEDOUT -> result = userInterface(preClient);
                case SIGNEDIN -> result = userInterface(postClient);
                // switch to game play UI
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

        return result;

    }

    public void resetText(PrintStream out){
        out.println(EscapeSequences.SET_BG_COLOR_BLACK);
        out.println(EscapeSequences.SET_TEXT_COLOR_WHITE);
    }

}
