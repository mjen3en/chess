package chess;


import java.util.Collection;
import java.util.HashSet;

//Generates valid moves based on pieceType and position
public class PieceMovesCalculator{


    /*public Collection pieceMoves(ChessBoard board, ChessPosition myPosition){

    }*/


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



}


