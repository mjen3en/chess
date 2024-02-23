package server;

import com.google.gson.Gson;
import dataAccess.*;
import request.*;
import result.*;
import service.GameService;
import service.RegistrationService;
import spark.*;

public class Server {

    GameDAO gDao = new MemoryGameDAO();
    AuthDAO aDao = new MemoryAuthDAO();
    UserDAO uDao = new MemoryUserDAO();
    GameService gameService = new GameService(gDao, uDao, aDao);
    RegistrationService registrationService = new RegistrationService(aDao, uDao);


    public int run(int desiredPort) {

        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);
        Spark.post("/db", this::register);

        Spark.awaitInitialization();
        return Spark.port();
    }


    private Object register(Request request, Response response) {
        var gson = new Gson();
        RegisterRequest req = (RegisterRequest)gson.fromJson(request.body(), RegisterRequest.class);
        RegisterResult result = registrationService.register(req.username(), req.password(), req.email());
        response.status(200);
        return result;
    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clear(Request request, Response response) {
        gameService.clear();
        response.status(200);
        return "";
    }

    public int port() {
        return Spark.port();
    }
}
