package chess;


import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Stream;

//Generates valid moves based on pieceType and position
public class PieceMovesCalculator {

    /*private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;*/

    private final ChessBoard myBoard;

    private final ChessPosition thisPosition;





    

    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition){
        myBoard = board;
        thisPosition = myPosition;

        int rowAdjusted = thisPosition.getRow() + 1;
        int colAdjusted = thisPosition.getColumn() + 1;

        ChessPosition checkMyPosition = new ChessPosition(myPosition.getRow() -1, myPosition.getColumn()-1);


    }


    public Collection bishopMovesCalculator(ChessBoard board, ChessPosition myPosition){
        var validMoves = new HashSet<ChessMove>();
        //row increasing, column increasing
        for (int i = 1; i < 8; i++){
            if(myPosition.getRow()+i == 8 || myPosition.getColumn()+i == 8){
                break;}
            ChessPosition checkPosition = new ChessPosition(myPosition.getRow()+i+1,myPosition.getColumn()+i+1);
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
                if (board.getPiece(checkPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
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

    public Collection kingMovesCalculator(ChessBoard board, ChessPosition myPosition){
        var validMoves = new HashSet<ChessMove>();

        if (!(myPosition.getRow() + 1 == 8)){
            ChessPosition newPositionUp = new ChessPosition(myPosition.getRow() +2, myPosition.getColumn()+2);
            ChessPosition newPositionCen = new ChessPosition(myPosition.getRow() +2, myPosition.getColumn()+1);
            ChessPosition newPositionDown = new ChessPosition(myPosition.getRow() +2, myPosition.getColumn() );
            if (myPosition.getColumn()+ 2 < 9) {
                if (board.getPiece(newPositionUp) == null) {
                        validMoves.add(new ChessMove(myPosition, newPositionUp, null));
                    } else if (board.getPiece(newPositionUp).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, newPositionUp, null));
                }
            }

            if (myPosition.getColumn()+ 1 < 9) {
                if (board.getPiece(newPositionCen) == null) {
                    validMoves.add(new ChessMove(myPosition, newPositionCen, null));
                } else if (board.getPiece(newPositionCen).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, newPositionCen, null));
                }
            }

            if (myPosition.getColumn() < 9) {
                if (board.getPiece(newPositionDown) == null) {
                    validMoves.add(new ChessMove(myPosition, newPositionDown, null));
                } else if (board.getPiece(newPositionDown).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, newPositionDown, null));
                }
            }

        }

        if (!(myPosition.getRow() - 1 <= -1 || myPosition.getRow() + 1 == 8)) {
            ChessPosition newPositionUp = new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 2);
            ChessPosition newPositionCen = new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 1);
            ChessPosition newPositionDown = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
            if (myPosition.getColumn()+ 2 < 9) {
                if (board.getPiece(newPositionUp) == null) {
                    validMoves.add(new ChessMove(myPosition, newPositionUp, null));
                } else if (board.getPiece(newPositionUp).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, newPositionUp, null));
                }
            }

            if (myPosition.getColumn()+ 1 < 9) {
                if (board.getPiece(newPositionCen) == null) {
                    validMoves.add(new ChessMove(myPosition, newPositionCen, null));
                } else if (board.getPiece(newPositionCen).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, newPositionCen, null));
                }
            }

            if (myPosition.getColumn() < 9) {
                if (board.getPiece(newPositionDown) == null) {
                    validMoves.add(new ChessMove(myPosition, newPositionDown, null));
                } else if (board.getPiece(newPositionDown).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, newPositionDown, null));
                }
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

    public Collection knightMovesCalculator(ChessBoard board, ChessPosition myPosition){
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

    public Collection pawnMovesCalculator(ChessBoard board, ChessPosition myPosition){
        var validMoves = new HashSet<ChessMove>();
        int i = 1;
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
            i = -1;
        }
        int rowAdjusted = myPosition.getRow() + 1;
        int colAdjusted = myPosition.getColumn() + 1;

        ChessPiece promoPiece = null;
        if (rowAdjusted+i != 9 && rowAdjusted + i != 0) { //checks space in front
            ChessPosition newPosition = new ChessPosition(rowAdjusted + i, colAdjusted);
            if (board.getPiece(newPosition) == null) {
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
            ChessPosition rightDiag = new ChessPosition(rowAdjusted + i, colAdjusted + 1);
            if (colAdjusted + 1 != 9 && board.getPiece(rightDiag) != null) {
                if (board.getPiece(rightDiag).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    if (rowAdjusted + i == 8 || rowAdjusted + i == 1) {
                        validMoves.add(new ChessMove(myPosition, rightDiag, ChessPiece.PieceType.QUEEN));
                        validMoves.add(new ChessMove(myPosition, rightDiag, ChessPiece.PieceType.BISHOP));
                        validMoves.add(new ChessMove(myPosition, rightDiag, ChessPiece.PieceType.KNIGHT));
                        validMoves.add(new ChessMove(myPosition, rightDiag, ChessPiece.PieceType.ROOK));

                    } else {
                        validMoves.add(new ChessMove(myPosition, rightDiag, null));
                    }
                }
            }
            ChessPosition leftDiag = new ChessPosition(rowAdjusted + i, colAdjusted - 1);
            if (colAdjusted - 1 != 0) {
                if (board.getPiece(leftDiag) != null) {
                    if (board.getPiece(leftDiag).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        if (rowAdjusted + i == 8 || rowAdjusted + i == 1) {
                            validMoves.add(new ChessMove(myPosition, leftDiag, ChessPiece.PieceType.QUEEN));
                            validMoves.add(new ChessMove(myPosition, leftDiag, ChessPiece.PieceType.BISHOP));
                            validMoves.add(new ChessMove(myPosition, leftDiag, ChessPiece.PieceType.KNIGHT));
                            validMoves.add(new ChessMove(myPosition, leftDiag, ChessPiece.PieceType.ROOK));

                        } else {
                            validMoves.add(new ChessMove(myPosition, leftDiag, null));
                        }
                    }

                }

            }
        }




        return validMoves;

    }

    public Collection rookMovesCalculator(ChessBoard board, ChessPosition myPosition){
        var validMoves = new HashSet<ChessMove>();
        //row increasing,

        int rowAdjusted = myPosition.getRow() +1;
        int colAdjusted = myPosition.getColumn() +1;

        for (int i = 1; i < 8; i++){
            if(myPosition.getRow()+i == 8){
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
        //column increasing
        for (int i = 1; i < 8; i++){
            if(myPosition.getColumn()+i == 8){
                break;}
            ChessPosition checkPosition = new ChessPosition(rowAdjusted,colAdjusted + i);
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
        // column decreasing
        for (int i = 1; i < 8; i++){
            if(myPosition.getColumn()-i  == -1){
                break;}
            ChessPosition checkPosition = new ChessPosition(rowAdjusted,colAdjusted - i);
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
        // row decreasing
        for (int i = 1; i < 8; i++){
            if(myPosition.getRow()-i == -1){
                break;}
            ChessPosition checkPosition = new ChessPosition(rowAdjusted - i, colAdjusted);
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

    public Collection<ChessMove> queenMovesCalculator(ChessBoard board, ChessPosition myPosition){
        var validMoves = new HashSet<ChessMove>();

        validMoves.addAll(RookMovesCalculator(board, myPosition));
        validMoves.addAll(BishopMovesCalculator(board, myPosition));


        return validMoves;

    }

}


