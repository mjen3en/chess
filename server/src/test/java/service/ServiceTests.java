package service;

import chess.ChessGame;
import dataAccess.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.*;
import java.util.HashMap;

class ServiceTests {


    @Test
    void clear() throws DataAccessException{

        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();
        GameService test =  new GameService(new MemoryGameDAO(), new MemoryUserDAO(), new MemoryAuthDAO());
        RegistrationService test1 = new RegistrationService(authDAO, userDAO);
        var register = test1.register(new RegisterRequest("micah", "jensen", "website@gmail.com"));
        test.createGame(new CreateGameRequest("gameName", register.authToken()));
        test.createGame(new CreateGameRequest("gameName2", register.authToken()));
        test.createGame(new CreateGameRequest("gameName3", register.authToken()));

        Assertions.assertEquals(3, test.getMapSize());

        test.clear();

        Assertions.assertEquals(0,test.getMapSize());
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
        var userData = new UserData("micah", "jensen", "website@gmail.com");

        Assertions.assertEquals(1, userDAO.getUserMap().size());


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
    void logoutPositiveCase() throws DataAccessException{
        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();

        RegistrationService test = new RegistrationService(authDAO, userDAO);
        LoginService test1 = new LoginService(authDAO, userDAO);
        var authToken = test.register(new RegisterRequest("micah", "jensen", "website@gmail.com"));
        test1.logout(new LogoutRequest(authToken.authToken()));
        Assertions.assertEquals(0, authDAO.getAuthMap().size());
        Assertions.assertEquals(0, userDAO.getUserMap().size());
    }

    @Test
    void logoutNegativeCase() throws DataAccessException {
        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();

        RegistrationService test = new RegistrationService(authDAO, userDAO);
        LoginService test1 = new LoginService(authDAO, userDAO);
        var authToken = test.register(new RegisterRequest("micah", "jensen", "website@gmail.com"));
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> test1.logout(new LogoutRequest("")));
        Assertions.assertEquals("Error: unauthorized", exp.getMessage());

    }

    @Test
    void listGamesPositiveCase() throws DataAccessException{
        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        RegistrationService test = new RegistrationService(authDAO, userDAO);
        LoginService test1 = new LoginService(authDAO, userDAO);
        GameService test3 = new GameService(gameDAO, userDAO, authDAO);

        var registration = test.register(new RegisterRequest("micah", "jensen", "website@gmail.com"));
        test3.createGame(new CreateGameRequest("gameName", registration.authToken()));
        var expectedGamesList = new HashMap<>();
        expectedGamesList.put(0, new GameData(1,null, null, "gameName", new ChessGame()));

        Assertions.assertEquals(expectedGamesList.values().size(), test3.listGames(new ListGamesRequest(registration.authToken())).games().size());
    }

    @Test
    void listGamesNegativeCase() throws DataAccessException{

        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        RegistrationService test = new RegistrationService(authDAO, userDAO);
        LoginService test1 = new LoginService(authDAO, userDAO);
        GameService test3 = new GameService(gameDAO, userDAO, authDAO);

        var registration = test.register(new RegisterRequest("micah", "jensen", "website@gmail.com"));
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> test3.createGame(new CreateGameRequest("gameName", "")));
        Assertions.assertEquals("Error: unauthorized", exp.getMessage());
    }

    @Test
    void joinGamePositiveCase() throws DataAccessException {
        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        RegistrationService test = new RegistrationService(authDAO, userDAO);
        LoginService test1 = new LoginService(authDAO, userDAO);
        GameService test3 = new GameService(gameDAO, userDAO, authDAO);

        var registration = test.register(new RegisterRequest("micah", "jensen", "website@gmail.com"));
        test3.createGame(new CreateGameRequest("gameName", registration.authToken()));
        test3.joinGame(new JoinGameRequest("WHITE", 1), registration.authToken() );
        var gameUsername = test3.listGames(new ListGamesRequest(registration.authToken())).games().get(0).getWhiteUsername();
        Assertions.assertEquals("micah", gameUsername);

    }

    @Test
    void joinGameNegativeCase() throws DataAccessException{
        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        RegistrationService test = new RegistrationService(authDAO, userDAO);
        LoginService test1 = new LoginService(authDAO, userDAO);
        GameService test3 = new GameService(gameDAO, userDAO, authDAO);

        var registration = test.register(new RegisterRequest("micah", "jensen", "website@gmail.com"));
        test3.createGame(new CreateGameRequest("gameName", registration.authToken()));
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> test3.joinGame(new JoinGameRequest("WHITE", 3), registration.authToken()));
        Assertions.assertEquals("Error: bad request", exp.getMessage());

    }

    @Test
    void createGamePositiveCase() throws DataAccessException {
        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        RegistrationService test = new RegistrationService(authDAO, userDAO);
        LoginService test1 = new LoginService(authDAO, userDAO);
        GameService test3 = new GameService(gameDAO, userDAO, authDAO);

        var registration = test.register(new RegisterRequest("micah", "jensen", "website@gmail.com"));
        test3.createGame(new CreateGameRequest("gameName", registration.authToken()));
        Assertions.assertEquals(1, test3.getMapSize());

    }

    @Test
    void createGameNegativeCase() throws DataAccessException{
        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        RegistrationService test = new RegistrationService(authDAO, userDAO);
        LoginService test1 = new LoginService(authDAO, userDAO);
        GameService test3 = new GameService(gameDAO, userDAO, authDAO);

        var registration = test.register(new RegisterRequest("micah", "jensen", "website@gmail.com"));
        Exception exp = Assertions.assertThrows(DataAccessException.class, ()->test3.createGame(new CreateGameRequest(null, registration.authToken())));
        Assertions.assertEquals("Error: bad request", exp.getMessage());

    }
}