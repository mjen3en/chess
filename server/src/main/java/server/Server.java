package server;

import dataAccess.MemoryGameDAO;
import spark.*;

public class Server {

    MemoryGameDAO gameDAO = new MemoryGameDAO();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
