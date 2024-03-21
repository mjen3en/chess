package ui;

import java.util.Arrays;

public class PostLoginClient implements Client{

    private ServerFacade sf;

    private String serverUrl;

    public String authToken;

    PostLoginClient(String serverURL, String authToken){
        this.serverUrl = serverURL;
        this.authToken = authToken;
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "logout" -> logout(authToken);
                case "creategame" -> createGame(params);
                case "listgames" -> listGames();
                case "joingame" -> joinGame();
                case "observe" -> joinObserve();
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    private String joinObserve() {
        return "";
    }

    private String joinGame() {
        return "";
    }

    private String listGames() {
        return "";
    }

    private String createGame(String ... params) throws ResponseException {
        if (params.length >= 1){
            String gameName = params[0];
            sf = new ServerFacade(serverUrl);
            sf.createGame(gameName, authToken);
        } else {
            throw new ResponseException(400, "needs Game Name");
        }

        return "Game Creation Successful";
    }

    private String logout(String authToken) throws ResponseException {
        sf = new ServerFacade(serverUrl);
        sf.logout(authToken);
        Repl.state = State.SIGNEDOUT;
        return "Logout Successful";
    }

    @Override
    public String help() {
        return """
                logout - stop playing for now
                creategame <GAMENAME> - make a new game
                listgames - list all games on the server
                joingame <GAMEID> - join a game currently on the server
                observe <GAMEID> - watch a game on the server
                """;
    }

    @Override
    public String getAuth() {
        return authToken;
    }
}
