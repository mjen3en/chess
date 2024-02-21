package service;

import dataAccess.MemoryGameDAO;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    @Test
    void clear() {


        GameService test =  new GameService(new MemoryGameDAO());
        GameData game1 = new GameData();
        GameData game2 = new GameData();
        GameData game3 = new GameData();
        test.insertGame(game1);
        test.insertGame(game2);
        test.insertGame(game3);

        Assertions.assertEquals(3, test.getMapSize());

        test.clear();

        Assertions.assertEquals(0,test.getMapSize());
    }
}