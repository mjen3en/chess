package webSocketMessages.serverMessages;

import chess.ChessGame;
import com.google.gson.Gson;

public class LoadGameMessage extends ServerMessage{
    public ChessGame game;
    public LoadGameMessage(ChessGame game) {
        super(ServerMessageType.LOAD_GAME, "Game updated");
        this.game = game;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
