package dataAccessTests;

import model.AuthData;
import org.junit.jupiter.api.Assertions;
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
    void insertAuthTestPositive() {
        var test = assertDoesNotThrow(() ->new MySQLAuthDAO());
        assertDoesNotThrow(() -> test.insertAuth("micah"));

    }

    @Test
    void insertAuthTestNegative() {
        var test = assertDoesNotThrow(() ->new MySQLAuthDAO());
        assertDoesNotThrow(() -> test.insertAuth("micah"));
        var ex = Assertions.assertThrows(DataAccessException.class, ()->test.insertAuth("micah")) ;
        Assertions.assertEquals(ex.getMessage(), "unable to update database: %s, %s");

    }

    @Test
    void getAuthMap() {
    }

    @Test
    void getAuthDataPositive() {
        var test = assertDoesNotThrow(() ->new MySQLAuthDAO());
        String testToken = assertDoesNotThrow(() -> test.insertAuth("micah"));
        AuthData expected = new AuthData();
        expected.setUsername("micah");
        expected.setAuthToken(testToken);
        var result = assertDoesNotThrow(() -> test.getAuthData(testToken));
        Assertions.assertEquals(expected.getAuthToken(), result.getAuthToken());
        Assertions.assertEquals(expected.getUsername(), result.getUsername());
    }

    @Test
    void getAuthDataNegative() {
        var test = assertDoesNotThrow(() ->new MySQLAuthDAO());
        var actual = Assertions.assertDoesNotThrow(()-> test.getAuthData("fakeAuthString"));
        Assertions.assertEquals(null, actual);

    }

    @Test
    void deleteAuth() {
    }

    @Test
    void checkAuthTokenPositive() {
        var test = assertDoesNotThrow(() ->new MySQLAuthDAO());
        String testToken = assertDoesNotThrow(() -> test.insertAuth("micah"));
        boolean testCheck = assertDoesNotThrow(() -> test.checkAuthToken(testToken));
        Assertions.assertEquals(true, testCheck);
    }

    @Test
    void checkAuthTokenNegative() {
        var test = assertDoesNotThrow(() ->new MySQLAuthDAO());
        String testToken = "fakeString";
        boolean testCheck = assertDoesNotThrow(() -> test.checkAuthToken(testToken));
        Assertions.assertEquals(false, testCheck);
    }
}