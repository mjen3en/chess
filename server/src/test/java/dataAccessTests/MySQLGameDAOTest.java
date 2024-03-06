package dataAccessTests;

import chess.ChessGame;
import dataAccess.MySQLGameDAO;
import model.GameData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MySQLGameDAOTest {

    @Test
    void getGame() {
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

        //list games




    }

    @Test
    void clear() {
    }

    @Test
    void getGameMap() {
    }

    @Test
    void getGameList() {
    }

    @Test
    void updateGame() {
    }

    @Test
    void checkIfGameExists() {
    }
}