package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

import static ui.EscapeSequences.*;

public class PrintBoard {

    private ChessBoard board;
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private String visitorColor;

    ArrayList<ChessMove> legalMoves;

    ChessPosition startPosition;



    public PrintBoard(ChessBoard board, String visitorColor, ChessPosition position){
        this.board = board;
        this.visitorColor = visitorColor;
        startPosition = position;
        legalMoves = populateLegalMoves(position);
        //board.resetBoard();
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
        out.print(" ");


        setTextWhite(out);
        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};

        if (Objects.equals(visitorColor, "white") || visitorColor == null){
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                drawHeader(out, headers[boardCol]);

                if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                }
            }

            out.println();
        }

        if (Objects.equals(visitorColor, "black")){
            for (int boardCol = 7; boardCol > -1; --boardCol) {
                drawHeader(out, headers[boardCol]);

                if (boardCol > 0) {
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
        //out.print(EMPTY.repeat(suffixLength));
        out.print("   ");
    }


    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setBackgroundBlack(out);
    }

    private  void drawChessBoard(PrintStream out) {
        int sideHead = 1;
        int i = 1;

        boolean black = false;
        if (Objects.equals(visitorColor, "white")){
             sideHead = 8;
             i = -1;
             black = true;
        }

        if (Objects.equals(visitorColor, "black")) {
            for (int boardRow = 1; boardRow <= BOARD_SIZE_IN_SQUARES; ++boardRow) {
                setTextWhite(out);
                drawRowOfSquares(out, boardRow, sideHead, black);
                out.println();
                sideHead = sideHead + i;

                if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                    setTextWhite(out);
                }
            }
        } else {
            for (int boardRow = 8; boardRow > 0; --boardRow) {
                setTextWhite(out);
                drawRowOfSquares(out, boardRow, sideHead, black);
                out.println();
                sideHead = sideHead + i;

                if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                    setTextWhite(out);
                }
            }

        }
    }

    private void drawRowOfSquares(PrintStream out, int boardRow, int sideHead, boolean white) {

        out.print(" " + sideHead + " ");
        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
        if (white) {
            for (int boardCol = 1; boardCol <= BOARD_SIZE_IN_SQUARES; ++boardCol) {
                drawSquare(out, boardRow, boardCol);
                out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                setBackgroundBlack(out);

            }
            out.print("|  ");
            out.print(" " + sideHead + " ");
            setBackgroundBlack(out);
        } else {
            for (int boardCol = 8; boardCol >= 1; --boardCol) {
                drawSquare(out, boardRow, boardCol);
                out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                setBackgroundBlack(out);

            }
            out.print("|  ");
            out.print(" " + sideHead + " ");
            setBackgroundBlack(out);

        }

    }

    private void drawSquare(PrintStream out, int boardRow, int boardCol){
        ChessPosition position = new ChessPosition(boardRow, boardCol);
        var currentPiece = board.getPiece(position);
        out.print("|");
        if (isLegalMove(new ChessMove(startPosition, position, null))){
            setBackgroundMagenta(out);
        }
        if (currentPiece != null){
            //determine pieceType
            String p = determinePieceType(currentPiece);
            out.print("  ");
            switch (currentPiece.getTeamColor()){
                case BLACK -> setTextRed(out);
                case WHITE -> setTextBlue(out);
            }
            out.print(p);
            setTextWhite(out);
        } else {
            out.print("   ");


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


    private ArrayList<ChessMove> populateLegalMoves(ChessPosition position){
        if (position == null){
            return null;
        }
        ChessPiece piece = board.getPiece(position);
        if (piece == null){
            return null;
        }

        ArrayList<ChessMove> positions = new ArrayList<>();
        positions.addAll(piece.pieceMoves(board, position));
        return positions;
    }

    private boolean isLegalMove(ChessMove move){
        if (legalMoves == null){
            return false;
        }
        return legalMoves.contains(move);
    }




    private static void setTextWhite(PrintStream out) {
        //out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setTextGreen(PrintStream out) {
        //out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_GREEN);
    }

    private static void setTextRed(PrintStream out) {
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setTextBlue(PrintStream out) {
        out.print(SET_TEXT_COLOR_BLUE);
    }

    private static void setBackgroundBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
    }


    private static void setBackgroundMagenta(PrintStream out){
        out.print(SET_BG_COLOR_MAGENTA);
    }
}
