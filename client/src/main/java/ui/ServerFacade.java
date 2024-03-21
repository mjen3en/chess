package ui;

import com.google.gson.Gson;
import com.sun.net.httpserver.Request;

import java.io.*;
import java.net.*;
import request.*;
import result.*;


public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public void createGame(String gameName, String authToken) throws ResponseException{
        var path = "/game";
        var request = new CreateGameRequest(gameName, authToken);
        var response = this.makeRequest("POST", path, request, CreateGameResult.class);
    }

    public void logout(String authToken) throws ResponseException {
        var path = "/session";
        var request = new LogoutRequest(authToken);
        var response = this.makeRequest("DELETE", path, request, LogoutResult.class);
    }


    public String login(String username, String password) throws ResponseException {
        var path = "/session";
        var request = new LoginRequest(username, password);
        var response = this.makeRequest("POST", path, request,  LoginResult.class);
        return response.authToken();
    }

    public void clear() throws ResponseException{
        var path = "/db";
        makeRequest("DELETE", path, "", ClearResult.class);
    }

    public String register(String username, String password, String email) throws ResponseException {
        var path = "/user";
        var request = new RegisterRequest(username, password, email);
            var response = this.makeRequest("POST", path, request, RegisterResult.class);
            return response.authToken();
    }
    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http);

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
        return status == 200;
    }
}
