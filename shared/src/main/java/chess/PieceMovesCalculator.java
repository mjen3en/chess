package chess;


import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Stream;

//Generates valid moves based on pieceType and position
public class PieceMovesCalculator {


    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {

    }


    public Collection bishopMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        var validMoves = new HashSet<ChessMove>();
        validMoves.addAll(iterateSameDiag(1, myPosition, board));
        validMoves.addAll(iterateSameDiag(-1, myPosition, board));
        validMoves.addAll(iterateDiffDiag(1, myPosition, board));
        validMoves.addAll(iterateDiffDiag(-1, myPosition, board));
        return validMoves;

    }

    public Collection kingMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        var validMoves = new HashSet<ChessMove>();

        validMoves.addAll(generateKingMove(1, board, myPosition));
        validMoves.addAll(generateKingMove(-1, board, myPosition));

        if (!(myPosition.getColumn() - 1 == -1)) {
            ChessPosition newPosition = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        if (!(myPosition.getColumn() + 1 == 8)) {
            ChessPosition newPosition = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 2);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }


        return validMoves;
    }

    private Collection<ChessMove> generateKingMove(int f, ChessBoard board, ChessPosition myPosition){
        var validMoves = new HashSet<ChessMove>();
        int rowAdjusted = myPosition.getRow() + 1;
        int colAdjusted = myPosition.getColumn() + 1;
        if (!(rowAdjusted + f > 8 || rowAdjusted + f < 1)) {
            ChessPosition newPositionUp = new ChessPosition(rowAdjusted + f, colAdjusted + 1);
            ChessPosition newPositionCen = new ChessPosition(rowAdjusted + f, colAdjusted);
            ChessPosition newPositionDown = new ChessPosition(rowAdjusted + f, colAdjusted - 1);
            if (colAdjusted + 1 < 9) {
                if (board.getPiece(newPositionUp) == null) {
                    validMoves.add(new ChessMove(myPosition, newPositionUp, null));
                } else if (board.getPiece(newPositionUp).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, newPositionUp, null));
                }
            }

            if (colAdjusted < 9) {
                if (board.getPiece(newPositionCen) == null) {
                    validMoves.add(new ChessMove(myPosition, newPositionCen, null));
                } else if (board.getPiece(newPositionCen).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, newPositionCen, null));
                }
            }

            if (colAdjusted - 1 > 0) {
                if (board.getPiece(newPositionDown) == null) {
                    validMoves.add(new ChessMove(myPosition, newPositionDown, null));
                } else if (board.getPiece(newPositionDown).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, newPositionDown, null));
                }
            }

        }
        return validMoves;
    }

    public Collection<ChessMove> knightMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        var validMoves = new HashSet<ChessMove>();

        validMoves.addAll(generateKnightMove(2, 1, board, myPosition));
        validMoves.addAll(generateKnightMove(2, -1, board, myPosition));
        validMoves.addAll(generateKnightMove(1, 2,  board, myPosition));
        validMoves.addAll(generateKnightMove(-1, 2,  board, myPosition));
        validMoves.addAll(generateKnightMove(-2, 1,  board, myPosition));
        validMoves.addAll(generateKnightMove(-2, -1, board, myPosition));
        validMoves.addAll(generateKnightMove(-1, -2,  board, myPosition));
        validMoves.addAll(generateKnightMove(1, -2,  board, myPosition));


        return validMoves;
    }

    private Collection<ChessMove> generateKnightMove(int x, int y, ChessBoard board, ChessPosition myPosition) {
        var validMoves = new HashSet<ChessMove>();
        int rowAdjusted = myPosition.getRow() + 1;
        int colAdjusted = myPosition.getColumn() + 1;
        if (!(rowAdjusted + x > 8 || rowAdjusted + x < 1 || colAdjusted + y > 8 || colAdjusted + y < 1)) {
            ChessPosition newPosition = new ChessPosition(rowAdjusted + x, colAdjusted + y);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        return validMoves;
    }

    public Collection pawnMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        var validMoves = new HashSet<ChessMove>();
        int i = 1;
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
            i = -1;
        }
        validMoves.addAll(generatePawnMove(i, 0, myPosition, board));
        validMoves.addAll(generatePawnMove(i, -1, myPosition, board));
        validMoves.addAll(generatePawnMove(i, 1, myPosition, board));
        return validMoves;

    }

    private Collection<ChessMove> generatePawnMove(int i, int f, ChessPosition myPosition, ChessBoard board) {
        var validMoves = new HashSet<ChessMove>();
        int rowAdjusted = myPosition.getRow() + 1;
        int colAdjusted = myPosition.getColumn() + 1;
        ChessGame.TeamColor myColor = board.getPiece(myPosition).getTeamColor();

        if (rowAdjusted + i != 9 && rowAdjusted + i != 0 && colAdjusted + f != 9 && colAdjusted + f != 0) { //checks space in front
            ChessPosition newPosition = new ChessPosition(rowAdjusted + i, colAdjusted + f);
            if ((f != 0 && board.getPiece(newPosition) == null) || (f == 0 && board.getPiece(newPosition) != null)) {
                return validMoves;
            }
            if (board.getPiece(newPosition) != null){
                if (board.getPiece(newPosition).getTeamColor() == myColor){
                    return validMoves;
                }
            }
            if ((i == 1 && rowAdjusted == 2) || (i == -1 && rowAdjusted == 7)) {
                ChessPosition newPosition2 = new ChessPosition(rowAdjusted + (2 * i), colAdjusted);
                validMoves.add(new ChessMove(myPosition, newPosition, null));
                if (board.getPiece(newPosition2) == null) {
                    validMoves.add(new ChessMove(myPosition, newPosition2, null));
                }
            } else if (rowAdjusted + i == 8 || rowAdjusted + i == 1) {
                validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN));
                validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP));
                validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT));
                validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK));

            } else {
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            }


        }


        return validMoves;
    }


    public Collection rookMovesCalculator(ChessBoard board, ChessPosition myPosition){
        var validMoves = new HashSet<ChessMove>();

        validMoves.addAll(iterateRow(1, myPosition, board));
        validMoves.addAll(iterateColumn(1, myPosition, board));
        validMoves.addAll(iterateRow(-1, myPosition, board));
        validMoves.addAll((iterateColumn(-1, myPosition, board)));
        return validMoves;

    }

    public Collection<ChessMove> queenMovesCalculator(ChessBoard board, ChessPosition myPosition){
        var validMoves = new HashSet<ChessMove>();

        validMoves.addAll(rookMovesCalculator(board, myPosition));
        validMoves.addAll(bishopMovesCalculator(board, myPosition));


        return validMoves;

    }

    private Collection<ChessMove> iterateRow(int f, ChessPosition myPosition, ChessBoard board){
        var validMoves = new HashSet<ChessMove>();
        //row increasing,

        int rowAdjusted = myPosition.getRow() +1;
        int colAdjusted = myPosition.getColumn() +1;
        for (int i = f; i < 8 ; i = i + f){
            if(myPosition.getRow()+i == 8 || myPosition.getRow()+i == -1){
                break;}
            ChessPosition checkPosition = new ChessPosition(rowAdjusted + i ,colAdjusted);
            if (board.getPiece(checkPosition) != null) {  //causes problems with index out of range
                if (board.getPiece(checkPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    validMoves.add(new ChessMove(myPosition, checkPosition, null));
                }
                break;
            }
            else{


                validMoves.add(new ChessMove(myPosition, checkPosition, null));

            }

        }


        return validMoves;
    }

    private Collection<ChessMove> iterateColumn(int f, ChessPosition myPosition, ChessBoard board){
        var validMoves = new HashSet<ChessMove>();

        int rowAdjusted = myPosition.getRow() +1;
        int colAdjusted = myPosition.getColumn() +1;
        for (int i = f; i < 8; i = i + f){
            if(myPosition.getColumn()+i == 8 || myPosition.getColumn()+i == -1){
                break;}
            ChessPosition checkPosition = new ChessPosition(rowAdjusted,colAdjusted + i);
            if (board.getPiece(checkPosition) != null) {
                if (board.getPiece(checkPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    validMoves.add(new ChessMove(myPosition, checkPosition, null));
                }
                break;
            }
            else{
                validMoves.add(new ChessMove(myPosition, checkPosition, null));

            }

        }
        return validMoves;
    }

    private Collection<ChessMove> iterateSameDiag(int f, ChessPosition myPosition, ChessBoard board) {
        var validMoves = new HashSet<ChessMove>();

        int rowAdjusted = myPosition.getRow() + 1;
        int colAdjusted = myPosition.getColumn() + 1;

        for (int i = f; i < 8; i = i + f) {
            if (rowAdjusted + i == 9 || colAdjusted + i == 9 || rowAdjusted + i == 0 || colAdjusted + i == 0) {
                break;
            }
            ChessPosition checkPosition = new ChessPosition(rowAdjusted + i, colAdjusted + i);
            if (board.getPiece(checkPosition) != null) {
                if (board.getPiece(checkPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, checkPosition, null));
                }
                break;
            } else {


                validMoves.add(new ChessMove(myPosition, checkPosition, null));

            }
        }

        return validMoves;
    }

    private Collection<ChessMove> iterateDiffDiag(int f, ChessPosition myPosition, ChessBoard board) {
        var validMoves = new HashSet<ChessMove>();

        int rowAdjusted = myPosition.getRow() + 1;
        int colAdjusted = myPosition.getColumn() + 1;

        for (int i = f; i < 8; i = i + f) {
            if (rowAdjusted - i == 9 || colAdjusted + i == 9 || rowAdjusted - i == 0 || colAdjusted + i == 0) {
                break;
            }
            ChessPosition checkPosition = new ChessPosition(rowAdjusted - i, colAdjusted + i);
            if (board.getPiece(checkPosition) != null) {
                if (board.getPiece(checkPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, checkPosition, null));
                }
                break;
            } else {


                validMoves.add(new ChessMove(myPosition, checkPosition, null));

            }
        }

        return validMoves;
    }


}


