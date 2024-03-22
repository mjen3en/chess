package clientTests;

import org.junit.jupiter.api.*;
import server.Server;
import ui.GameInfo;
import ui.ResponseException;
import ui.ServerFacade;

import java.util.HashMap;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @BeforeEach
    void clearServer() throws ResponseException {
        facade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerPositive() throws ResponseException {
        String authToken = facade.register("Micah", "Jensen", "Email");

        Assertions.assertTrue(authToken.length() > 10);
    }

    @Test
    public void registerNegative() throws ResponseException {
        String authToken = facade.register("Micah", "Jensen", "Email");
        Assertions.assertThrows(Exception.class, () ->facade.register("Micah", "Jensen", "Email"));
    }

    @Test
    public void loginPositive() throws ResponseException {
        String authToken = facade.register("Micah", "Jensen", "Email");
        facade.logout(authToken);
        String authToken2 = facade.login("Micah", "Jensen");
        Assertions.assertNotEquals(authToken, authToken2);

    }

    @Test
    public void loginNegative(){
        Assertions.assertThrows(Exception.class, ()-> facade.login("Micah", "Jensen"));
    }

    @Test
    public void logoutPositive() throws ResponseException {
        String authToken = facade.register("Micah", "Jensen", "Email");
        Assertions.assertDoesNotThrow(() ->facade.logout(authToken));
    }

    @Test
    public void logoutNegative() throws ResponseException {
        String authToken = facade.register("Micah", "Jensen", "Email");
        Assertions.assertDoesNotThrow(() ->facade.logout(authToken));
        Assertions.assertThrows(Exception.class, () -> facade.logout(authToken));
    }

    @Test
    public void createGamePositive() throws ResponseException {
        String authToken = facade.register("Micah", "Jensen", "Email");
        Assertions.assertDoesNotThrow(() ->facade.createGame("coolGame",authToken));
    }

    @Test
    public void createGameNegative() throws ResponseException {
        String authToken = facade.register("Micah", "Jensen", "Email");
        facade.logout(authToken);
        Assertions.assertThrows(Exception.class, () ->facade.createGame("coolGame",authToken));
    }

    @Test
    public void listGamesPositive() throws ResponseException {
        String authToken = facade.register("Micah", "Jensen", "Email");
        Assertions.assertDoesNotThrow(() ->facade.createGame("coolGame",authToken));
        Assertions.assertDoesNotThrow(() ->facade.createGame("coolerGame",authToken));
        Assertions.assertDoesNotThrow(() ->facade.createGame("coolestGame",authToken));
        HashMap<Integer, GameInfo> map = facade.listGames(authToken);
        Assertions.assertEquals("coolGame", map.get(1).gameName());
        Assertions.assertEquals("coolerGame", map.get(2).gameName());
        Assertions.assertEquals("coolestGame", map.get(3).gameName());
    }

    @Test
    public void listGamesNegative() throws ResponseException {
        String authToken = facade.register("Micah", "Jensen", "Email");
        Assertions.assertDoesNotThrow(() -> facade.createGame("coolGame", authToken));
        Assertions.assertDoesNotThrow(() -> facade.createGame("coolerGame", authToken));
        Assertions.assertDoesNotThrow(() -> facade.createGame("coolestGame", authToken));
        facade.logout(authToken);
        Assertions.assertThrows(Exception.class, ()-> facade.listGames(authToken));
    }

    @Test
    public void joinGamePositive() throws ResponseException {
        String authToken = facade.register("Micah", "Jensen", "Email");
        Assertions.assertDoesNotThrow(() -> facade.createGame("coolGame", authToken));
        facade.listGames(authToken);
        facade.joinGame(authToken, 1, "white");
        String authToken2 = facade.register("Taylor", "Jensen", "Email");
        facade.joinGame(authToken2, 1, "black");
        HashMap<Integer, GameInfo> map = facade.listGames(authToken);
        Assertions.assertEquals("Micah", map.get(1).player1());
        Assertions.assertEquals("Taylor", map.get(1).player2());

    }

    @Test
    public void joinGameNegative() throws ResponseException {
        String authToken = facade.register("Micah", "Jensen", "Email");
        Assertions.assertDoesNotThrow(() -> facade.createGame("coolGame", authToken));
        facade.listGames(authToken);

        facade.joinGame(authToken, 1, "white");
        String authToken2 = facade.register("Taylor", "Jensen", "Email");
        Assertions.assertThrows(Exception.class, () ->facade.joinGame(authToken2, 1, "white"));
    }
}