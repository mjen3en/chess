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

        int rowAdjusted = thisPosition.getRow() + 1;
        int colAdjusted = thisPosition.getColumn() + 1;

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

        if (!(myPosition.getRow() + 1 == 8)){
            ChessPosition newPositionUp = new ChessPosition(myPosition.getRow() +2, myPosition.getColumn()+2);
            ChessPosition newPositionCen = new ChessPosition(myPosition.getRow() +2, myPosition.getColumn()+1);
            ChessPosition newPositionDown = new ChessPosition(myPosition.getRow() +2, myPosition.getColumn() );
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

        if (!(myPosition.getRow() - 1 <= -1 || myPosition.getRow() + 1 == 8)){
            ChessPosition newPositionUp = new ChessPosition(myPosition.getRow() , myPosition.getColumn()+2);
            ChessPosition newPositionCen = new ChessPosition(myPosition.getRow() , myPosition.getColumn() +1);
            ChessPosition newPositionDown = new ChessPosition(myPosition.getRow() , myPosition.getColumn() );
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

        if (!(myPosition.getColumn() - 1 == -1)){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()+ 1, myPosition.getColumn());
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        if (!(myPosition.getColumn() + 1 == 8)){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 2);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }


        return validMoves;
    }

    public Collection KnightMovesCalculator(ChessBoard board, ChessPosition myPosition){
        var validMoves = new HashSet<ChessMove>();
        int rowAdjusted = myPosition.getRow() + 1;
        int colAdjusted = myPosition.getColumn() + 1;

        ChessMove move1 = generateKnightMove(2, 1, rowAdjusted, colAdjusted, board, myPosition);
        if (move1 != null){
            validMoves.add(move1);
        }
        ChessMove move2 = generateKnightMove(2, -1, rowAdjusted, colAdjusted, board, myPosition);
        if (move2 != null){
            validMoves.add(move2);
        }
        ChessMove move3 = generateKnightMove(1, 2, rowAdjusted, colAdjusted, board, myPosition);
        if (move3 != null){
            validMoves.add(move3);
        }
        ChessMove move4 = generateKnightMove(-1, 2, rowAdjusted, colAdjusted, board, myPosition);
        if (move4 != null){
            validMoves.add(move4);
        }
        ChessMove move5 = generateKnightMove(-2, 1, rowAdjusted, colAdjusted, board, myPosition);
        if (move5 != null){
            validMoves.add(move5);
        }

        ChessMove move6 = generateKnightMove(-2, -1, rowAdjusted, colAdjusted, board, myPosition);
        if (move6 != null){
            validMoves.add(move6);
        }

        ChessMove move7 = generateKnightMove(-1, -2, rowAdjusted, colAdjusted, board, myPosition);
        if (move7 != null){
            validMoves.add(move7);
        }

        ChessMove move8 =generateKnightMove(1, -2, rowAdjusted, colAdjusted, board, myPosition);
        if (move8 != null){
            validMoves.add(move8);
        }


        /*if (board.getPiece(topRight) == null || board.getPiece(topRight).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
            validMoves.add(new ChessMove(myPosition, topRight, null));
        }
        ChessPosition topLeft = new ChessPosition(rowAdjusted + 2, colAdjusted - 1);
        if (board.getPiece(topLeft) == null || board.getPiece(topLeft).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
            validMoves.add(new ChessMove(myPosition, topLeft, null));
        }
        ChessPosition rightTop = new ChessPosition(rowAdjusted + 1, colAdjusted + 2);
        if (board.getPiece(rightTop) == null || board.getPiece(rightTop).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
            validMoves.add(new ChessMove(myPosition, topLeft, null));
        }*/



        return validMoves;
    }

    private ChessMove generateKnightMove(int x, int y, int rowAdjusted, int colAdjusted, ChessBoard board, ChessPosition myPosition){
        if (!(rowAdjusted + x > 8 || rowAdjusted + x < 1 || colAdjusted + y > 8 || colAdjusted + y < 1)){
            ChessPosition newPosition = new ChessPosition(rowAdjusted + x, colAdjusted + y);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                return new ChessMove(myPosition, newPosition, null);
            }
        }
        return null;
    }

    public Collection PawnMovesCalculator(ChessBoard board, ChessPosition myPosition){
        var validMoves = new HashSet<ChessMove>();
        int i = 1;
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
            i = -1;
        }
        int rowAdjusted = myPosition.getRow() + 1;
        int colAdjusted = myPosition.getColumn() + 1;

        if (rowAdjusted+i != 9 && rowAdjusted + i != 0){ //checks space in front
            ChessPosition newPosition = new ChessPosition(rowAdjusted + i , colAdjusted);
            if (board.getPiece(newPosition) == null){
                validMoves.add(new ChessMove(myPosition, newPosition, null));


            }
            ChessPosition rightDiag = new ChessPosition(rowAdjusted + i, colAdjusted + 1);
            if (colAdjusted +1 != 9 && board.getPiece(rightDiag) != null && board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            }
            ChessPosition leftDiag = new ChessPosition(rowAdjusted + i, colAdjusted - 1);
            if (colAdjusted -1 != 9 && board.getPiece(leftDiag) != null && board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }



        return validMoves;

    }

}


