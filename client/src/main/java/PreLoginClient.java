import com.sun.nio.sctp.NotificationHandler;


public class PreLoginClient implements Client{



    public PreLoginClient(String serverURL, NotificationHandler notificationHandler){
//        server = new ServerFacade(serverUrl);
//        this.serverUrl = serverUrl;
//        this.notificationHandler = notificationHandler;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                  case "login" -> login(params);
                  case "help" -> help(params);
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


    private String login(String ... params){
        if (params.length >= 3){
            //state = State.SIGNEDIN;

            //websocket stuff and crap
        }
        return "";
    }



    private String register(){
        return "";
    }


}
