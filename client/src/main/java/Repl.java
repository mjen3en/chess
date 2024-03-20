import com.sun.nio.sctp.HandlerResult;
import com.sun.nio.sctp.Notification;
import com.sun.nio.sctp.NotificationHandler;

import java.util.Scanner;

import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;


public class Repl {

    public static State state = State.SIGNEDOUT;
    private PreLoginClient preClient;
    //private PostLoginClient postClient;

    public Repl(String serverURL) {
        preClient = new PreLoginClient("string", new NotificationHandler() {
            @Override
            public HandlerResult handleNotification(Notification notification, Object attachment) {
                return null;
            }
        });
    }

    public void run() {
        System.out.println("\uD83D\uDC36 Welcome to chess. Register to start");
        System.out.print(preClient.help());

        var result = "";

        while (!result.equals("quit")) {
            switch (state) {
                case SIGNEDOUT -> result = userInterface(preClient);
                //case SIGNEDIN -> result = userInterface(postClient);
                // switch to game play UI
            }
        }

        System.out.println();
    }



//    private void printPrompt() {
//        System.out.print("\n"  +  RESET + ">>> " + GREEN);
//    }

    //turn while loop into function Pre Login U1
    // make UI function generic and pass in client as argument?

    private String userInterface(Client client){
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            //printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }

        return result;

    }
}
