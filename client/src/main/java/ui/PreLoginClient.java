package ui;

import com.sun.nio.sctp.NotificationHandler;
import java.util.Arrays;


public class PreLoginClient implements Client{
    private String visitorName = null;

    public String authToken;
    private ServerFacade sf;

    private String serverUrl;




    public PreLoginClient(String url){
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
                  case "quit" -> "Be seeing you";
                  case "clear" -> clear();
                  default -> help();
            };
        } catch (ResponseException ex) {
            Repl.state = State.SIGNEDOUT;
            return ex.getMessage();
        }
    }

    public String help(){
        return EscapeSequences.SET_TEXT_COLOR_WHITE + """
                register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                login <USERNAME> <PASSWORD> - to play chess
                quit - stop playing chess
                help - show possible commands
                """;
    }

    @Override
    public String getAuth(){
        return authToken;
    }


    private String login(String ... params) throws ResponseException {
        if (params.length >= 2){
            String username = params[0];
            String password = params[1];
            sf = new ServerFacade(serverUrl);
            authToken = sf.login(username, password);
            Repl.state = State.SIGNEDIN;
        } else {
            throw new ResponseException(400, "need <USERNAME> <PASSWORD> <EMAIL>");
        }
        return "Login Successful";
    }



    private String register(String ... params) throws ResponseException {
        if (params.length >= 3){
            Repl.state = State.SIGNEDIN;
            String username = params[0];
            String password = params[1];
            String email = params[2];
            sf = new ServerFacade(serverUrl);
            authToken = sf.register(username, password, email);
            Repl.state = State.SIGNEDIN;
        } else {
            throw new ResponseException(400, "need <USERNAME> <PASSWORD> <EMAIL>");
        }
        return "Registration Successful";
    }

    private String clear() throws ResponseException{
        sf = new ServerFacade(serverUrl);
        sf.clear();

        return "Clear Successful (you sly dawg)";

    }


}
