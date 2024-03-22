package ui;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import static ui.EscapeSequences.*;

public class PrintBoard {

    private ChessBoard board;
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private String visitorColor;

    public PrintBoard(ChessBoard board, String visitorColor){
        this.board = board;
        this.visitorColor = visitorColor;
    }

    public void drawBoard(){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        //draw headers
        drawHeaders(out);

        //
        drawChessBoard(out);

        //draw bottom headers
        drawHeaders(out);




    }

    private void drawHeaders(PrintStream out){
        out.print(EMPTY.repeat(4));
        //out.print(" ");


        setTextWhite(out);
        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};

        if (visitorColor == "WHITE" || visitorColor == null){
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                drawHeader(out, headers[boardCol]);

                if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                }
            }

            out.println();
        }



    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;
        //"\u2001"\u2005\u200A"

        //out.print(EMPTY.repeat(prefixLength));
        //out.print(" ");
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
        //out.print(" ");
    }


    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setBlack(out);
    }

    private  void drawChessBoard(PrintStream out) {
        int sideHead = 1;
        int i = 1;
        if (visitorColor == "WHITE"){
             sideHead = 8;
             i = -1;
        }

        for (int boardRow = 1; boardRow <= BOARD_SIZE_IN_SQUARES; ++boardRow) {
            setTextWhite(out);
            drawRowOfSquares(out, boardRow, sideHead);
            out.println();
            sideHead = sideHead + i;

            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                setTextWhite(out);
            }
        }
    }

    private void drawRowOfSquares(PrintStream out, int boardRow, int sideHead) {

        out.print(" " + sideHead + " ");
        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
        for (int boardCol = 1; boardCol <= BOARD_SIZE_IN_SQUARES; ++boardCol){
            drawSquare(out, boardRow, boardCol);
            out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));


        }
        out.print("|  ");
        out.print(" " + sideHead + " ");


    }

    private void drawSquare(PrintStream out, int boardRow, int boardCol){
        var currentPiece = board.getPiece(new ChessPosition(boardRow, boardCol));
        if (currentPiece != null){
            //determine pieceType
            String p = determinePieceType(currentPiece);
            out.print("| " + p + " ");
        } else {
            out.print("|" + EMPTY);
        }
    }

    private String determinePieceType(ChessPiece piece){
        ChessPiece.PieceType type = piece.getPieceType();
        String returnPiece = "";
        switch (type) {
            case ChessPiece.PieceType.KNIGHT -> returnPiece = "k";
            case ChessPiece.PieceType.KING -> returnPiece = "K";
            case ChessPiece.PieceType.PAWN -> returnPiece = "P";
            case ChessPiece.PieceType.QUEEN -> returnPiece = "Q";
            case ChessPiece.PieceType.BISHOP -> returnPiece = "B";
            case ChessPiece.PieceType.ROOK -> returnPiece = "R";

        }

        return returnPiece;
    }

//    private static void drawVerticalLine(PrintStream out) {
//
//        int boardSizeInSpaces = BOARD_SIZE_IN_SQUARES * SQUARE_SIZE_IN_CHARS +
//                (BOARD_SIZE_IN_SQUARES - 1) * LINE_WIDTH_IN_CHARS;
//
//        for (int lineRow = 0; lineRow < LINE_WIDTH_IN_CHARS; ++lineRow) {
//            setRed(out);
//            out.print(EMPTY.repeat(boardSizeInSpaces));
//
//            setBlack(out);
//            out.println();
//        }
//    }




    private static void setTextWhite(PrintStream out) {
        //out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }
}
