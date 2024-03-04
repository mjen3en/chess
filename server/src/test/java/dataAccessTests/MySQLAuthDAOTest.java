package dataAccessTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import dataAccess.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;

class MySQLAuthDAOTest {
    static Server server;

//    @BeforeAll
//    static void startServer() {
//        server = new Server(new MemoryGameDAO(), new MySQLAuthDAO(), new MySQLUserDAO());
//        server.run(0);
//        var url = "http://localhost:" + petServer.port();
//        server = new ServerFacade(url);
//    }

    @Test
    void clear() {

       var test = assertDoesNotThrow(() ->new MySQLAuthDAO());

       assertDoesNotThrow(() -> test.insertAuth("micah"));
       assertDoesNotThrow(() -> test.insertAuth("sadie"));
       assertDoesNotThrow(() -> test.insertAuth("taylor"));
       assertDoesNotThrow(()->test.clear());


    }

    @Test
    void insertAuthTest() {
    }

    @Test
    void getAuthMap() {
    }

    @Test
    void getAuthData() {
    }

    @Test
    void deleteAuth() {
    }

    @Test
    void checkAuthToken() {
    }
}