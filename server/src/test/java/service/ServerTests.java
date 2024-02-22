package service;

import org.junit.jupiter.api.BeforeAll;
import server.Server;

public class ServerTests {

    static private Server chessServer;
    //static ServerFacade server;

    @BeforeAll
    static void startServer(){
        chessServer = new Server();
        chessServer.run(0);
        var url = "http://localhost:" + chessServer.port();
    }
    
}
