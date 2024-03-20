package ui;

import java.util.Arrays;

public class PostLoginClient implements Client{

    PostLoginClient(String serverURL){}

    @Override
    public String eval(String input) {
//        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                default -> help();
            };
//        } catch (ResponseException ex) {
//            return ex.getMessage();
//        }
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
}
