package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    ChessBoard board = new ChessBoard();
    TeamColor turn;

    public static HashMap<ChessPosition, ChessPiece> blackPieces = new HashMap<ChessPosition, ChessPiece>();
    public static HashMap<ChessPosition, ChessPiece> whitePieces = new HashMap<ChessPosition, ChessPiece>();



    public ChessGame() {
        //board.resetBoard();
    }





    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {

        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece currentPiece = board.getPiece(startPosition);
        HashSet<ChessMove> moves = new HashSet<ChessMove>();
        if (currentPiece == null) {
            return null;
        } else {
            moves.addAll(currentPiece.pieceMoves(board, startPosition));

        }
        return moves;
    }



    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
            throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        HashMap<ChessPosition, ChessPiece> teamHashMap;
        ChessPosition kingPosition;

        if (teamColor == TeamColor.BLACK){
            teamHashMap = whitePieces;
            kingPosition = getKingPosition(blackPieces);
        } else {
            teamHashMap = blackPieces;
            kingPosition = getKingPosition(whitePieces);
        }
        HashSet<ChessPosition> allTeamMoves = new HashSet<ChessPosition>();
        for (Map.Entry<ChessPosition, ChessPiece> set: teamHashMap.entrySet()){
            Set<ChessPosition> allPieceMoves = new HashSet<ChessPosition>();
            allPieceMoves.addAll(getAllEndPositions(set.getValue().pieceMoves(board, set.getKey())));
            allTeamMoves.addAll(allPieceMoves);
        }



        return allTeamMoves.contains(kingPosition);
    }


    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
            throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
            throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return new ChessBoard();
    }

    public static void addPieceMap(ChessPosition position, ChessPiece piece){
        TeamColor color = piece.getTeamColor();
        if (color == TeamColor.BLACK){
            blackPieces.put(position, piece);
        } else{
            whitePieces.put(position, piece);
        }

    }

    public static ChessPosition getKingPosition(HashMap<ChessPosition, ChessPiece> pieceMap) {
        for (Map.Entry<ChessPosition, ChessPiece> entry : pieceMap.entrySet()) {
            if (entry.getValue().getPieceType() == ChessPiece.PieceType.KING) {
                return entry.getKey();
            }
        }
        return null;
    }


    private Collection<ChessPosition> getAllEndPositions(Collection<ChessMove> pieceMoves){
        Iterator<ChessMove> iterator = pieceMoves.iterator();
        Collection<ChessPosition> allEndPositions = new HashSet<ChessPosition>();
        while(iterator.hasNext()){
            allEndPositions.add(iterator.next().getEndPosition());

        }
        return allEndPositions;
    }

//    private static boolean checkPositionSet(HashSet<ChessPosition> set, ChessPosition kingPosition){
//
//
//
//    }
}
