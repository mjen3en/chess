package chess;


import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

//Generates valid moves based on pieceType and position
public class PieceMovesCalculator {

    /*private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;*/

    private final ChessBoard myBoard;

    private final ChessPosition thisPosition;



    /*public Collection pieceMoves(ChessBoard board, ChessPosition myPosition){

    }*/

    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition){
        /*pieceColor = board.getPiece(myPosition).getTeamColor();
        type = board.getPiece(myPosition).getPieceType();*/
        myBoard = board;
        thisPosition = myPosition;


    }


    public Collection BishopMovesCalculator(ChessBoard board, ChessPosition myPosition){
        var validMoves = new HashSet<ChessMove>();
        //row increasing, column increasing
        for (int i = 1; i <= 7; i++){
            if(myPosition.getRow()+i <= 0 || myPosition.getRow()+i > 8 || myPosition.getColumn()+i <= 0|| myPosition.getColumn()+i > 8){
                break;}
            ChessPosition checkPosition = new ChessPosition(myPosition.getRow()+i-1,myPosition.getColumn()+i-1);
            if (i != 1 && board.getPiece(checkPosition) != null) {  //causes problems with index out of range
                    validMoves.add(new ChessMove(myPosition, checkPosition, null));
                    break;
                }
            else{
                ChessPosition nextposition = new ChessPosition(myPosition.getRow()+i,myPosition.getColumn()+i);
                validMoves.add(new ChessMove(myPosition, nextposition, null));

            }

        }
        //row decreasing, column increasing
        for (int i = 1; i <= 7; i++){
            if(myPosition.getRow()-i <= 0 || myPosition.getRow()-i > 8 || myPosition.getColumn()+i <= 0|| myPosition.getColumn()+i > 8){
                break;}
            ChessPosition checkPosition = new ChessPosition(myPosition.getRow()-i+1,myPosition.getColumn()+i-1);
            if (i != 1 && board.getPiece(checkPosition) != null) {  //causes problems with index out of range
                validMoves.add(new ChessMove(myPosition, checkPosition, null));
                break;
            }
            else{
                ChessPosition nextposition = new ChessPosition(myPosition.getRow()-i,myPosition.getColumn()+i);
                validMoves.add(new ChessMove(myPosition, nextposition, null));

            }

        }
        // row increasing, column decreasing
        for (int i = 1; i <= 7; i++){
            if(myPosition.getRow()+i <= 0 || myPosition.getRow()+i > 8 || myPosition.getColumn()-i <= 0|| myPosition.getColumn()-i > 8){
                break;}
            ChessPosition checkPosition = new ChessPosition(myPosition.getRow()+i-1,myPosition.getColumn()-i+1);
            if (i != 1 && board.getPiece(checkPosition) != null) {  //causes problems with index out of range
                validMoves.add(new ChessMove(myPosition, checkPosition, null));
                break;
            }
            else{
                ChessPosition nextposition = new ChessPosition(myPosition.getRow()+i,myPosition.getColumn()-i);
                validMoves.add(new ChessMove(myPosition, nextposition, null));

            }

        }
        // row decreasing, column decreasing
        for (int i = 1; i <= 7; i++){
            if(myPosition.getRow()-i <= 0 || myPosition.getRow()-i > 8 || myPosition.getColumn()-i <= 0|| myPosition.getColumn()-i > 8){
                break;}
            ChessPosition checkPosition = new ChessPosition(myPosition.getRow()-i+1,myPosition.getColumn()-i+1);
            if (i != 1 && board.getPiece(checkPosition) != null) {  //causes problems with index out of range
                validMoves.add(new ChessMove(myPosition, checkPosition, null));
                break;
            }
            else{
                ChessPosition nextposition = new ChessPosition(myPosition.getRow()-i,myPosition.getColumn()-i);
                validMoves.add(new ChessMove(myPosition, nextposition, null));

            }

        }


        return validMoves;

    }

   /* public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.getTeamColor() && type == that.getPieceType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }*/

   /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PieceMovesCalculator that = (PieceMovesCalculator) o;
        return Objects.equals(myBoard, that.myBoard) && Objects.equals(thisPosition, that.thisPosition);
    }*/

    /*@Override
    public int hashCode(ChessMove added) {
        return Objects.hash(added.getStartPosition(), added.getEndPosition(), added.getPromotionPiece());
    }*/
}


