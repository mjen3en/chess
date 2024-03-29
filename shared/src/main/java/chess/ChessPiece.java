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
    private PieceType type;

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
            return moves.bishopMovesCalculator(board, myPosition);

        }
        if (type == PieceType.KING){
            return moves.kingMovesCalculator(board, myPosition);
        }

        if (type == PieceType.KNIGHT){
            return moves.knightMovesCalculator(board, myPosition);
        }

        if (type == PieceType.PAWN){
            return moves.pawnMovesCalculator(board, myPosition);
        }

        if (type == PieceType.ROOK){
            return moves.rookMovesCalculator(board, myPosition);
        }
        if (type == PieceType.QUEEN){
            return moves.queenMovesCalculator(board, myPosition);
        }

        return new ArrayList<>();


    }

    public void changePieceType(ChessPiece.PieceType pieceType){
        type = pieceType;
    }


   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }


   @Override
    public String toString() {

       if (type == PieceType.BISHOP) {
           return pieceColor.toString() + " BISHOP";

       }
       if (type == PieceType.KING) {
           return pieceColor.toString() + " KING";
       }

       if (type == PieceType.KNIGHT) {
           return pieceColor.toString() + " KNIGHT";
       }

       if (type == PieceType.PAWN) {
           return pieceColor.toString() + " PAWN";
       }

       if (type == PieceType.ROOK) {
           return pieceColor.toString() + " ROOK";
       }
       if (type == PieceType.QUEEN) {
           return pieceColor.toString() + " QUEEN";
       }
       return "";
    }
}








