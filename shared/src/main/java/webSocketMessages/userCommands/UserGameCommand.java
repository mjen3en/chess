package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * 
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {


    public UserGameCommand(String authToken, UserGameCommand.CommandType type, ChessGame.TeamColor color, int gameID) {

        this.authToken = authToken;
        this.commandType = type;
        this.gameID = gameID;
        this.color = color;

    }

    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    private final ChessGame.TeamColor color;

    public ChessGame.TeamColor getColor() {
        return color;
    }

    public int getGameID() {
        return gameID;
    }

    private final int gameID;
    protected CommandType commandType;

    private final String authToken;

    public ChessMove getMove() {
        return move;
    }

    public void setMove(ChessMove move) {
        this.move = move;
    }

    public ChessMove move;



    public String getAuthString() {
        return authToken;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand))
            return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}