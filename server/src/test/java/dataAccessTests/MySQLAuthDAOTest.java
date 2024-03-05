package dataAccessTests;

import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dataAccess.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;

class MySQLAuthDAOTest {
    static Server server;

    @BeforeEach
    void init() {
        var test = assertDoesNotThrow(() ->new MySQLAuthDAO());
        assertDoesNotThrow(() ->test.clear());

    }

    @Test
    void clear() {

       var test = assertDoesNotThrow(() ->new MySQLAuthDAO());

       assertDoesNotThrow(() -> test.insertAuth("micah"));
       assertDoesNotThrow(() -> test.insertAuth("sadie"));
       assertDoesNotThrow(() -> test.insertAuth("taylor"));
       assertDoesNotThrow(()->test.clear());
        var result = assertDoesNotThrow(()-> test.getAuthMap());

        Assertions.assertEquals(0, result.size());


    }

    @Test
    void insertAuthTestPositive() {
        var test = assertDoesNotThrow(() ->new MySQLAuthDAO());
        assertDoesNotThrow(() -> test.insertAuth("micah"));
        var result = assertDoesNotThrow(()-> test.getAuthMap());

        Assertions.assertEquals(1, result.size());

    }

    @Test
    void insertAuthTestNegative() {
        var test = assertDoesNotThrow(() ->new MySQLAuthDAO());
        var testToken = assertDoesNotThrow(() -> test.insertAuth(""));
        var resultAuthData = assertDoesNotThrow(() -> test.getAuthData(testToken));
        var result = assertDoesNotThrow(()-> test.getAuthMap());

        Assertions.assertEquals("", resultAuthData.getUsername());
        Assertions.assertEquals(1, result.size());

    }

    @Test
    void getAuthMapPositive() {
        var test = assertDoesNotThrow(() ->new MySQLAuthDAO());

        assertDoesNotThrow(() -> test.insertAuth("micah"));
        assertDoesNotThrow(() -> test.insertAuth("sadie"));
        assertDoesNotThrow(() -> test.insertAuth("taylor"));
        var result = assertDoesNotThrow(()-> test.getAuthMap());

        Assertions.assertEquals(3, result.size());

    }

    @Test
    void getAuthMapNegative() {
        var test = assertDoesNotThrow(() ->new MySQLAuthDAO());
        var result = assertDoesNotThrow(()-> test.getAuthMap());

        Assertions.assertEquals(0, result.size());

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
    void deleteAuthPositive() {
        var test = assertDoesNotThrow(() ->new MySQLAuthDAO());
        String testToken = assertDoesNotThrow(() -> test.insertAuth("micah"));
        assertDoesNotThrow(() ->test.deleteAuth(testToken));
        var result = assertDoesNotThrow(() ->test.checkAuthToken(testToken));
        Assertions.assertEquals(false, result);
    }

    @Test
    void deleteAuthNegative() {
        var test = assertDoesNotThrow(() ->new MySQLAuthDAO());
        assertDoesNotThrow(() ->test.deleteAuth("testToken"));
        var result = assertDoesNotThrow(()-> test.getAuthMap());

        Assertions.assertEquals(0, result.size());
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