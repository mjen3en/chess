package service;


import chess.ChessGame;
import dataAccess.MemoryGameDAO;
import model.GameData;

public class GameService {

    public GameData getGame(ChessGame.TeamColor clientColor, Integer gameID){
        return new GameData();
    }

    public void clear(){
        //call memory game dao and clears
        return;
    }
}
