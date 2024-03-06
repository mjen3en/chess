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
        //Assertions.assertThrows(()-> test.insertGame(game1));

        //Assertions.assertEquals(1, id1);

        var gameMap = assertDoesNotThrow(()-> test.getGameMap());
        Assertions.assertEquals(3, gameMap.size());

        //list games
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
    void getGameList() {
    }

    @Test
    void updateGame() {
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