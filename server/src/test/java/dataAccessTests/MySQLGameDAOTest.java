package dataAccessTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MySQLGameDAO;
import model.*;
import dataAccess.MySQLUserDAO;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MySQLGameDAOTest {

    @Test
    void getGame() {
    }

    @BeforeEach
    void init() {
        var test = assertDoesNotThrow(() ->new MySQLGameDAO());
        assertDoesNotThrow(() ->test.clear());

    }

    @Test
    void insertGamePositive() {
        var test = assertDoesNotThrow(() ->new MySQLGameDAO());

        var game1 = new GameData(0, "micah", "tay", "coolgame", new ChessGame());
        var game2 = new GameData(0, "kaden", "eli", "coolergame", new ChessGame());
        var game3 = new GameData(0, "kevin", "koby", "coolestgame", new ChessGame());
        int id1 = assertDoesNotThrow(()-> test.insertGame(game1));
        int id2 = assertDoesNotThrow(()-> test.insertGame(game2));
        int id3 =  assertDoesNotThrow(()-> test.insertGame(game3));

        Assertions.assertEquals(1, id1);
        Assertions.assertEquals(2, id2);
        Assertions.assertEquals(3, id3);

        var gameMap = assertDoesNotThrow(()-> test.getGameMap());
        Assertions.assertEquals(3, gameMap.size());

        //list games
    }

    @Test
    void insertGameNegative() {
        var test = assertDoesNotThrow(() ->new MySQLGameDAO());

        GameData game1 = null;
        Assertions.assertThrows(NullPointerException.class, ()-> test.insertGame(game1));
    }

    @Test
    void clear() {
        var test = assertDoesNotThrow(() ->new MySQLGameDAO());
        var game1 = new GameData(0, "micah", "tay", "coolgame", new ChessGame());
        var game2 = new GameData(0, "kaden", "eli", "coolergame", new ChessGame());
        var game3 = new GameData(0, "kevin", "koby", "coolestgame", new ChessGame());
        int id1 = assertDoesNotThrow(()-> test.insertGame(game1));
        int id2 = assertDoesNotThrow(()-> test.insertGame(game2));
        int id3 =  assertDoesNotThrow(()-> test.insertGame(game3));

        assertDoesNotThrow(()->test.clear());
        var gameMap = assertDoesNotThrow(()-> test.getGameMap());
        Assertions.assertEquals(0, gameMap.size());

    }

    @Test
    void getGameMapPositive() {
        var test = assertDoesNotThrow(() ->new MySQLGameDAO());

        var game1 = new GameData(0, "micah", "tay", "coolgame", new ChessGame());
        var game2 = new GameData(0, "kaden", "eli", "coolergame", new ChessGame());
        var game3 = new GameData(0, "kevin", "koby", "coolestgame", new ChessGame());
        int id1 = assertDoesNotThrow(()-> test.insertGame(game1));
        int id2 = assertDoesNotThrow(()-> test.insertGame(game2));
        int id3 =  assertDoesNotThrow(()-> test.insertGame(game3));

        Assertions.assertEquals(1, id1);
        Assertions.assertEquals(2, id2);
        Assertions.assertEquals(3, id3);

        HashMap<Integer, GameData> gameMap = assertDoesNotThrow(()-> test.getGameMap());
        Assertions.assertEquals(3, gameMap.size());
        Assertions.assertEquals(game1.getGameName(), gameMap.get(id1).getGameName());
        Assertions.assertEquals(game2.getGameName(), gameMap.get(id2).getGameName());
        Assertions.assertEquals(game3.getGameName(), gameMap.get(id3).getGameName());
    }

    @Test
    void getGameMapNegative() {
        var test = assertDoesNotThrow(() ->new MySQLGameDAO());
        HashMap<Integer, GameData> gameMap = assertDoesNotThrow(()-> test.getGameMap());
        Assertions.assertEquals(0, gameMap.size());

    }

    @Test
    void getGameListPositive() {
        var test = assertDoesNotThrow(() ->new MySQLGameDAO());

        var game1 = new GameData(0, "micah", "tay", "coolgame", new ChessGame());
        var game2 = new GameData(0, "kaden", "eli", "coolergame", new ChessGame());
        var game3 = new GameData(0, "kevin", "koby", "coolestgame", new ChessGame());
        int id1 = assertDoesNotThrow(()-> test.insertGame(game1));
        int id2 = assertDoesNotThrow(()-> test.insertGame(game2));
        int id3 =  assertDoesNotThrow(()-> test.insertGame(game3));

        var list = assertDoesNotThrow(()->test.getGameList());
        Assertions.assertEquals(3, list.size());

    }

    @Test
    void getGameListNegative() {
        var test = assertDoesNotThrow(() ->new MySQLGameDAO());
        var list = assertDoesNotThrow(()->test.getGameList());
        Assertions.assertEquals(0, list.size());

    }

    @Test
    void updateGamePositive() {
        var test = assertDoesNotThrow(() ->new MySQLGameDAO());
        var game1 = new GameData(0, null, null, "coolgame", new ChessGame());
        int id1 = assertDoesNotThrow(()-> test.insertGame(game1));
        var game2 = new GameData(1, "Micah", "Taylor", "coolgame", new ChessGame());



        assertDoesNotThrow(()-> test.updateGame(game2));
        HashMap<Integer, GameData> gameMap = assertDoesNotThrow(()-> test.getGameMap());
        var result = assertDoesNotThrow(() ->test.getGame(id1));
        Assertions.assertEquals("Micah", result.getWhiteUsername());
        Assertions.assertEquals("Taylor", result.getBlackUsername());
    }

    @Test
    void updateGameNegative() {
        var test = assertDoesNotThrow(() ->new MySQLGameDAO());
        var game1 = new GameData(0, null, null, "coolgame", new ChessGame());
        int id1 = assertDoesNotThrow(()-> test.insertGame(game1));
        GameData game2 = null;



        assertThrows(NullPointerException.class, ()-> test.updateGame(game2));
    }

    @Test
    void checkIfGameExistsPositive() throws DataAccessException {
        var test = assertDoesNotThrow(() ->new MySQLGameDAO());
        var game1 = new GameData(0, "micah", "tay", "coolgame", new ChessGame());
        int id1 = assertDoesNotThrow(()-> test.insertGame(game1));
        Assertions.assertTrue(test.checkIfGameExists(id1));
    }

    @Test
    void checkIfGameExistsNegative() throws DataAccessException {
        var test = assertDoesNotThrow(() ->new MySQLGameDAO());
        Assertions.assertFalse(test.checkIfGameExists(7));
    }
}