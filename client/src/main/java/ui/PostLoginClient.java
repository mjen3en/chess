package ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PostLoginClient implements Client{

    private ServerFacade sf;

    private String serverUrl;

    public String authToken;

    private HashMap gameMap;

    private String visitorName;

    PostLoginClient(String serverURL, String authToken){
        this.serverUrl = serverURL;
        this.authToken = authToken;
        this.visitorName = visitorName;
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
                case "joingame" -> joinGame(params);
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

    private String joinGame(String ... params) throws ResponseException {
        sf = new ServerFacade(serverUrl);
        //update gameMap
        gameMap = sf.listGames(authToken);
        if (params.length >= 2) {
            int gameNum = Integer.valueOf(params[0]);
            String color = params[1];
            if (color.equals("BLACK") && color.equals("WHITE")) {
                throw new ResponseException(400, "Please specify color BLACK or WHITE");
            }

            sf.joinGame(authToken, gameNum, color);
        } else {
            throw new ResponseException(400, "<GAMEID> <TEAMCOLOR>");
        }

        return "Join Game Successful";
    }

    private String listGames() throws ResponseException {
        sf = new ServerFacade(serverUrl);
        gameMap = sf.listGames(authToken);

        return printMap(gameMap);
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
                joingame <GAMEID> <COLOR> - join a game currently on the server
                observe <GAMEID> - watch a game on the server
                """;
    }

    @Override
    public String getAuth() {
        return authToken;
    }

    private String printMap(HashMap<Integer, GameInfo> gameMap){
        String list = "";
        for (Map.Entry<Integer, GameInfo> i : gameMap.entrySet()){
            GameInfo info = i.getValue();
            String addition = i.getKey().toString() + " " + info.gameName() + ": " + info.player1() + " vs " + info.player2() + " \n";
            list = list + addition;
        }
        return list;
    }
}
