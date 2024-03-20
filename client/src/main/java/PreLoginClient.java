import com.sun.nio.sctp.NotificationHandler;
import java.util.Arrays;


public class PreLoginClient implements Client{
    private String visitorName = null;
    private ServerFacade sf;

    private String serverUrl;




    public PreLoginClient(String url, NotificationHandler notificationHandler){
          this.serverUrl = url;
//        this.notificationHandler = notificationHandler;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                  case "login" -> login(params);
                  case "register" -> register(params);
                  case "quit" -> "quit";
                  default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String help(){
        return """
                register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                login <USERNAME> <PASSWORD> - to play chess
                quit - stop playing chess
                help - show possible commands
                """;
    }


    private String login(String ... params) throws ResponseException {
        if (params.length >= 3){
            Repl.state = State.SIGNEDIN;
            String username = params[1];
            String password = params[2];
            sf = new ServerFacade(serverUrl);
            String authToken = sf.login(username, password);
            System.out.println(authToken);
        }
        return "";
    }



    private String register(String ... params) throws ResponseException {
        if (params.length >= 4){
            Repl.state = State.SIGNEDIN;
            String username = params[1];
            String password = params[2];
            String email = params[3];
            sf = new ServerFacade(serverUrl);
            String authToken = sf.register(username, password, email);
            System.out.println(authToken);
        }
        return "";
    }


}
