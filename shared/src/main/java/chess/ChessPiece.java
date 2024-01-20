package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPosition boardPosition = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        PieceMovesCalculator moves = new PieceMovesCalculator(board, boardPosition);
        if (type == PieceType.BISHOP) {
            return moves.BishopMovesCalculator(board, myPosition);

        }
        if (type == PieceType.KING){
            return moves.KingMovesCalculator(board, myPosition);
        }

        if (type == PieceType.KNIGHT){
            return moves.KnightMovesCalculator(board, myPosition);
        }

        if (type == PieceType.PAWN){
            return moves.PawnMovesCalculator(board, myPosition);
        }
        return new ArrayList<>();


    }


   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }*/
}








