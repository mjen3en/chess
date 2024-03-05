package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.MySQLAuthDAO;
import dataAccess.MySQLUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MySQLUserDAOTest {

    @BeforeEach
    void init() {
        var test = assertDoesNotThrow(() ->new MySQLUserDAO());
        assertDoesNotThrow(() ->test.clear());

    }

    @Test
    void getUserPositive() {
        var test = assertDoesNotThrow(()-> new MySQLUserDAO());
        var expected = new UserData("micah", "jensen", "cooltown@gmail");
        assertDoesNotThrow(() ->test.insertUser(expected));
        var result = assertDoesNotThrow(() -> test.getUser("micah"));
        Assertions.assertEquals(expected.username, result.username);
        Assertions.assertEquals(expected.password, result.password);
        Assertions.assertEquals(expected.email, result.email);
    }

    @Test
    void getUserNegative() {
        var test = assertDoesNotThrow(()-> new MySQLUserDAO());
        var result =  Assertions.assertDoesNotThrow(() -> test.getUser(null));
        Assertions.assertEquals(null, result);
    }

    @Test
    void insertUserPositive() {
        var test = assertDoesNotThrow(()-> new MySQLUserDAO());
        assertDoesNotThrow(() ->test.insertUser(new UserData("micah", "jensen", "cooltown@gmail")));
        var result = assertDoesNotThrow(()-> test.getUserMap());
        var userResult = assertDoesNotThrow(()-> test.getUser("micah"));
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("micah", userResult.getUsername());
        Assertions.assertEquals("jensen", userResult.getPassword());
        Assertions.assertEquals("cooltown@gmail", userResult.email);
    }

    @Test
    void insertUserNegative() {
        var test = assertDoesNotThrow(()-> new MySQLUserDAO());
        Assertions.assertThrows(DataAccessException.class, () ->test.insertUser(new UserData(null, "jensen", "cooltown@gmail")));


    }

    @Test
    void getUserMapPositive() {
        var test = assertDoesNotThrow(()-> new MySQLUserDAO());
        assertDoesNotThrow(() ->test.insertUser(new UserData("micah", "jensen", "cooltown@gmail")));
        assertDoesNotThrow(() ->test.insertUser(new UserData("sadie", "jensen", "cooltown@gmail")));
        assertDoesNotThrow(() ->test.insertUser(new UserData("taylor", "jensen", "cooltown@gmail")));
        var result = assertDoesNotThrow(()-> test.getUserMap());
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void getUserMapNegative() {
        var test = assertDoesNotThrow(()-> new MySQLUserDAO());
        var result = assertDoesNotThrow(()-> test.getUserMap());
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void clear()  {
        var test = assertDoesNotThrow(()-> new MySQLUserDAO());
        assertDoesNotThrow(() ->test.insertUser(new UserData("micah", "jensen", "cooltown@gmail")));
        assertDoesNotThrow(() ->test.insertUser(new UserData("sadie", "jensen", "cooltown@gmail")));
        assertDoesNotThrow(() ->test.insertUser(new UserData("taylor", "jensen", "cooltown@gmail")));
        assertDoesNotThrow(() ->test.clear());
        var result = assertDoesNotThrow(()-> test.getUserMap());
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void deleteUserPositive() {
        var test = assertDoesNotThrow(()-> new MySQLUserDAO());
        assertDoesNotThrow(() ->test.insertUser(new UserData("micah", "jensen", "cooltown@gmail")));
        var result = assertDoesNotThrow(()-> test.getUserMap());
        Assertions.assertEquals(1, result.size());
        assertDoesNotThrow(()-> test.deleteUser("micah"));
        var result1 = assertDoesNotThrow(()-> test.getUserMap());
        Assertions.assertEquals(0, result1.size());

    }

    @Test
    void deleteUserNegative() {
        var test = assertDoesNotThrow(()-> new MySQLUserDAO());
        assertDoesNotThrow(()-> test.deleteUser("micah"));
        var result1 = assertDoesNotThrow(()-> test.getUserMap());
        Assertions.assertEquals(0, result1.size());

    }
}