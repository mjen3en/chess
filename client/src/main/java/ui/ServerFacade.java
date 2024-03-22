package ui;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.Headers;
import model.GameData;
import request.*;
import result.*;


public class ServerFacade {

    private final String serverUrl;

    private HashMap<Integer, GameData> trueGameMap;

    private HashMap<Integer, GameInfo> clientGameMap;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public void joinGame(String authToken, int gameId, String playerColor) throws ResponseException {
        var path = "/game";
        String gameName = clientGameMap.get(gameId).gameName();
        int trueId = findTrueGameId(trueGameMap, gameName);
        var request = new JoinGameRequest(playerColor, trueId);
        var response = this.makeRequest("PUT", path, request, JoinGameResult.class, authToken);
    }

    public HashMap listGames(String authToken) throws ResponseException {
        var path = "/game";
        var request = new ListGamesRequest(authToken);
        var response = this.makeRequest("GET", path, request, ListGamesResult.class, authToken);
        trueGameMap = response.games();
        clientGameMap = populateGameMap(response.games());
        return clientGameMap;
    }

    public void createGame(String gameName, String authToken) throws ResponseException{
        var path = "/game";
        var request = new CreateGameRequest(gameName, authToken);
        var response = this.makeRequest("POST", path, request, CreateGameResult.class, authToken);
    }

    public void logout(String authToken) throws ResponseException {
        var path = "/session";
        var request = new LogoutRequest(authToken);
        var response = this.makeRequest("DELETE", path, request, LogoutResult.class, authToken);
    }


    public String login(String username, String password) throws ResponseException {
        var path = "/session";
        var request = new LoginRequest(username, password);
        var response = this.makeRequest("POST", path, request,  LoginResult.class, "");
        return response.authToken();
    }

    public void clear() throws ResponseException{
        var path = "/db";
        makeRequest("DELETE", path, "", ClearResult.class, "");
    }

    public String register(String username, String password, String email) throws ResponseException {
        var path = "/user";
        var request = new RegisterRequest(username, password, email);
            var response = this.makeRequest("POST", path, request, RegisterResult.class, "");
            return response.authToken();
    }
    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            http.setRequestProperty("authorization", authToken);

            if (method != "GET") {
                writeBody(request, http);
            }

            //sends to the server
            http.connect();

            //ch
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

    private HashMap populateGameMap(HashMap<Integer,GameData> gameDataMap){
        ArrayList<GameData> games = new ArrayList<>(gameDataMap.values());
        int gameNum = 1;
        HashMap<Integer, GameInfo> gameMap = new HashMap<Integer, GameInfo>();
        for (GameData i : games){
            GameInfo game = new GameInfo(i.getGameName(), i.getWhiteUsername(), i.getBlackUsername());
            gameMap.put(gameNum, game);
            gameNum++;
        }
        return gameMap;
    }

    private int findTrueGameId(HashMap<Integer,GameData> gameDataMap, String gameName){
        for (Map.Entry<Integer, GameData> entry : gameDataMap.entrySet()){
            if(entry.getValue().getGameName().equals(gameName)){
                return entry.getKey();
            }
        }
        return 0;
    }
}
