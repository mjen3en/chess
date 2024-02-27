package service;

import dataAccess.*;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;

class ServiceTests {


    @Test
    void clear() {


//        GameService test =  new GameService(new MemoryGameDAO(), new MemoryUserDAO(), new MemoryAuthDAO());
////        GameData game1 = new GameData();
////        GameData game2 = new GameData();
////        GameData game3 = new GameData();
//        test.insertGame(game1);
//        test.insertGame(game2);
//        test.insertGame(game3);
//
//        Assertions.assertEquals(3, test.getMapSize());
//
//        test.clear();
//
//        Assertions.assertEquals(0,test.getMapSize());
    }

    @Test
    void registerPositiveCase() throws DataAccessException {
        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();

        RegistrationService test = new RegistrationService(authDAO, userDAO);


        test.register(new RegisterRequest("micah", "jensen", "website@gmail.com"));
        Assertions.assertEquals(1, authDAO.getAuthMap().size());
        Assertions.assertEquals(1, userDAO.getUserMap().size());

        test.register(new RegisterRequest("sadie", "jensen", "urmom@gmail.com"));

        test.register(new RegisterRequest("tay", "fartface", "urmom@gmail.com"));

        test.register(new RegisterRequest("dad", "mom", "urmom@gmail.com"));

        Assertions.assertEquals(4, authDAO.getAuthMap().size());
        Assertions.assertEquals(4, userDAO.getUserMap().size());




    }

    @Test
    void registerNegativeCase() throws DataAccessException{
        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();

        RegistrationService test = new RegistrationService(authDAO, userDAO);

        Exception exp = Assertions.assertThrows(DataAccessException.class, ()-> test.register(new RegisterRequest("micah", null, "website@gmail.com")));
        Assertions.assertEquals("Error: bad request", exp.getMessage());
    }

    @Test
    void loginPositiveCase() throws DataAccessException{
        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();

        RegistrationService test = new RegistrationService(authDAO, userDAO);
        LoginService test1 = new LoginService(authDAO, userDAO);


        test.register(new RegisterRequest("micah", "jensen", "website@gmail.com"));
        test1.login(new LoginRequest("micah", "jensen"));


    }

    @Test
    void loginNegativeCase() throws DataAccessException{
        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();

        LoginService test = new LoginService(authDAO, userDAO);

        Exception exp = Assertions.assertThrows(DataAccessException.class, ()-> test.login(new LoginRequest("micah", "jensen")));
        Assertions.assertEquals("Error: unauthorized", exp.getMessage());
    }

    @Test
    void logout
}