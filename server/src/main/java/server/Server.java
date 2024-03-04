package server;

import com.google.gson.Gson;
import dataAccess.*;
import request.*;
import result.*;
import service.GameService;
import service.LoginService;
import service.RegistrationService;
import spark.*;

import java.util.Objects;

public class Server {

    GameDAO gDao = new MemoryGameDAO();
    AuthDAO aDao = new MySQLAuthDAO();
    UserDAO uDao = new MySQLUserDAO();
    GameService gameService = new GameService(gDao, uDao, aDao);
    RegistrationService registrationService = new RegistrationService(aDao, uDao);
    LoginService loginService = new LoginService(aDao, uDao);

    public Server() throws DataAccessException {
    }


    public int run(int desiredPort) {

        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.post("/game", this::creategame);
        Spark.get("/game", this::listgames);
        Spark.put("/game", this::joingame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object joingame(Request request, Response response) {
        var gson = new Gson();
        JoinGameRequest req = (JoinGameRequest)gson.fromJson(request.body(), JoinGameRequest.class);
        String authToken = request.headers("authorization");
        try {
            JoinGameResult result = gameService.joinGame(req, authToken);
            response.status(200);
            return gson.toJson(result);
        } catch (DataAccessException ex){
            setResponseCode(ex, response);
            return gson.toJson(new JsonErrorMessage(ex.getMessage()));
        }
    }

    private Object listgames(Request request, Response response) {
        var gson = new Gson();
        ListGamesRequest req = (ListGamesRequest)gson.fromJson(request.body(), ListGamesRequest.class);
        String authToken = request.headers("authorization");
        try {
            ListGamesResult result = gameService.listGames(req, authToken);
            response.status(200);
            return gson.toJson(result);
        } catch (DataAccessException ex){
            setResponseCode(ex, response);
            return gson.toJson(new JsonErrorMessage(ex.getMessage()));
        }
    }

    private Object creategame(Request request, Response response) {
        var gson = new Gson();
        CreateGameRequest req = (CreateGameRequest)gson.fromJson(request.body(), CreateGameRequest.class);
        String authToken = request.headers("authorization");
        try {
            CreateGameResult result = gameService.createGame(req, authToken);
            response.status(200);
            return gson.toJson(result);
        } catch (DataAccessException ex){
            setResponseCode(ex, response);
            return gson.toJson(new JsonErrorMessage(ex.getMessage()));
        }
    }

    private Object logout(Request request, Response response) {
        var gson = new Gson();
        LogoutRequest req = (LogoutRequest) gson.fromJson(request.body(), LogoutRequest.class);
        String authToken = request.headers("authorization");
        try {
            LogoutResult result = loginService.logout(req, authToken);
            response.status(200);
            return gson.toJson(result);
        } catch (DataAccessException ex){
            setResponseCode(ex, response);
            return gson.toJson(new JsonErrorMessage(ex.getMessage()));
        }
    }

    private Object login(Request request, Response response) {
        var gson = new Gson();
        LoginRequest req = (LoginRequest)gson.fromJson(request.body(), LoginRequest.class);
        try {
            LoginResult result = loginService.login(req);
            response.status(200);
            return gson.toJson(result);
        }
        catch (DataAccessException ex) {
            setResponseCode(ex, response);
            return gson.toJson(new JsonErrorMessage(ex.getMessage()));
        }
    }


    private Object register(Request request, Response response) {
        var gson = new Gson();
        RegisterRequest req = (RegisterRequest)gson.fromJson(request.body(), RegisterRequest.class);
        try {
            RegisterResult result = registrationService.register(req);
            response.status(200);
            return gson.toJson(result);
        }
        catch (DataAccessException ex){
            setResponseCode(ex, response);
            return gson.toJson(new JsonErrorMessage(ex.getMessage()));
        }

    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clear(Request request, Response response) throws DataAccessException{
        var res = gameService.clear();
        response.status(200);
        var serializer = new Gson();
        var json = serializer.toJson(res);
        return json;

    }

    public int port() {
        return Spark.port();
    }

    private void setResponseCode(DataAccessException ex, Response response){
        if (Objects.equals(ex.getMessage(), "Error: bad request")) {
            response.status(400);
        } else if (Objects.equals(ex.getMessage(), "Error: already taken")){
            response.status(403);
        } else if (Objects.equals(ex.getMessage(), "Error: unauthorized")){
            response.status(401);
        } else {
            response.status(500);
        }
    }
}

