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

        ChessPosition checkMyPosition = new ChessPosition(myPosition.getRow() -1, myPosition.getColumn()-1);


    }


    public Collection BishopMovesCalculator(ChessBoard board, ChessPosition myPosition){
        var validMoves = new HashSet<ChessMove>();
        //ChessPosition checkMyPosition = new ChessPosition(myPosition.getRow() -1, myPosition.getColumn()-1);
        //row increasing, column increasing
        for (int i = 1; i < 8; i++){
            if(myPosition.getRow()+i == 8 || myPosition.getColumn()+i == 8){
                break;}
            ChessPosition checkPosition = new ChessPosition(myPosition.getRow()+i+1,myPosition.getColumn()+i+1);
            //ChessPosition nextPosition = new ChessPosition(myPosition.getRow()+i,myPosition.getColumn()+i);
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
        //row decreasing, column increasing
        for (int i = 1; i < 8; i++){
            if(myPosition.getRow()-i == -1 ||  myPosition.getColumn()+i == 8){
                break;}
            ChessPosition checkPosition = new ChessPosition(myPosition.getRow()-i+1,myPosition.getColumn()+i+1);
            //ChessPosition nextPosition = new ChessPosition(myPosition.getRow()-i,myPosition.getColumn()+i);
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
        // row increasing, column decreasing
        for (int i = 1; i < 8; i++){
            if(myPosition.getRow()+i == 8 || myPosition.getColumn()-i  == -1){
                break;}
            ChessPosition checkPosition = new ChessPosition(myPosition.getRow()+i+1,myPosition.getColumn()-i+1);
            //ChessPosition nextPosition = new ChessPosition(myPosition.getRow()+i,myPosition.getColumn()-i);
            if (board.getPiece(checkPosition) != null) {  //causes problems with index out of range
                if (board.getPiece(checkPosition).getTeamColor() != board.getPiece(checkPosition).getTeamColor()){
                    validMoves.add(new ChessMove(myPosition, checkPosition, null));
                }
                break;
            }
            else{

                validMoves.add(new ChessMove(myPosition, checkPosition, null));

            }

        }
        // row decreasing, column decreasing
        for (int i = 1; i < 8; i++){
            if(myPosition.getRow()-i == -1 ||myPosition.getColumn()-i == -1){
                break;}
            ChessPosition checkPosition = new ChessPosition(myPosition.getRow()-i+1,myPosition.getColumn()-i+1);
            //ChessPosition nextPosition = new ChessPosition(myPosition.getRow()-i,myPosition.getColumn()-i);
            if (board.getPiece(checkPosition) != null) {  //checks if space has a piece
                if (board.getPiece(checkPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){ //checks if piece is enemy
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

    public Collection KingMovesCalculator(ChessBoard board, ChessPosition myPosition){
        var validMoves = new HashSet<ChessMove>();

        if (!(myPosition.getRow() + 1 <= 0 || myPosition.getRow() + 1 >= 8)){
            ChessPosition newPositionUp = new ChessPosition(myPosition.getRow() +1, myPosition.getColumn()+1);
            ChessPosition newPositionCen = new ChessPosition(myPosition.getRow() +1, myPosition.getColumn());
            ChessPosition newPositionDown = new ChessPosition(myPosition.getRow() +1, myPosition.getColumn() - 1);
            if (board.getPiece(newPositionUp) == null || board.getPiece(newPositionUp).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(new ChessMove(myPosition, newPositionUp, null));
            }
            if (board.getPiece(newPositionCen) == null || board.getPiece(newPositionCen).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(new ChessMove(myPosition, newPositionCen, null));
            }
            if (board.getPiece(newPositionDown) == null || board.getPiece(newPositionCen).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(new ChessMove(myPosition, newPositionDown, null));
            }

        }

        if (!(myPosition.getRow() - 1 <= 0 || myPosition.getRow() - 1 >= 8)){
            ChessPosition newPositionUp = new ChessPosition(myPosition.getRow() -1, myPosition.getColumn()+1);
            ChessPosition newPositionCen = new ChessPosition(myPosition.getRow() -1, myPosition.getColumn());
            ChessPosition newPositionDown = new ChessPosition(myPosition.getRow() -1, myPosition.getColumn() - 1);
            if (board.getPiece(newPositionUp) == null || board.getPiece(newPositionUp).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(new ChessMove(myPosition, newPositionUp, null));
            }
            if (board.getPiece(newPositionCen) == null || board.getPiece(newPositionCen).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(new ChessMove(myPosition, newPositionCen, null));
            }
            if (board.getPiece(newPositionDown ) == null || board.getPiece(newPositionDown).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(new ChessMove(myPosition, newPositionDown, null));
            }

        }

        if (!(myPosition.getColumn() - 1 <= 0 || myPosition.getColumn() - 1 >= 8)){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow(), myPosition.getColumn() - 1);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        if (!(myPosition.getColumn() + 1 <= 0 || myPosition.getColumn() + 1 >= 8)){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 1);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }


        return validMoves;
    }


}


