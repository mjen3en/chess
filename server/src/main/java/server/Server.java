package server;

import dataAccess.GameDAO;
import dataAccess.MemoryGameDAO;
import service.GameService;
import spark.*;

public class Server {

    GameDAO gDao = new MemoryGameDAO();
    GameService gameService = new GameService(gDao);

    public int run(int desiredPort) {

        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);

        Spark.awaitInitialization();
        return Spark.port();
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

}
