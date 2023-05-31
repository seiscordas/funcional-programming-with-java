package com.kl.chess;

import com.kl.boardgame.Board;
import com.kl.boardgame.Piece;
import com.kl.boardgame.Position;
import com.kl.chess.exceptions.ChessException;
import com.kl.chess.pieces.King;
import com.kl.chess.pieces.Rook;
import lombok.Getter;

public class ChessMatch {

    @Getter
    private int turn;
    @Getter
    private Player currentPlayer;
    private final Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Player.WHITE;
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        int rows = board.getRows();
        int columns = board.getColumns();
        ChessPiece[][] chessPieces = new ChessPiece[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                chessPieces[i][j] = (ChessPiece) board.piece(i, j);
            }
        }

        return chessPieces;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        switchTurn();
        return (ChessPiece) capturedPiece;
    }

    private void validateTargetPosition(Position source, Position target) {
        if(!board.piece(source).possibleMove(target)){
            throw new ChessException("Chosen piece cannot be moved to destination position!");
        }
    }

    private Piece makeMove(Position source, Position target) {
        Piece piece = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(piece, target);
        return capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if(!board.thereIsAPiece(position)){
            throw new ChessException("There is no piece on source position.");
        }

        validateTurnOfPlayer(position);

        if(!board.piece(position).isThereAnyPossibleMove()){
            throw new ChessException("There is no possible moves for the chosen piece!");
        }
    }

    private void validateTurnOfPlayer(Position position){
        if(currentPlayer != ((ChessPiece) board.piece(position)).getPlayer()){
            throw new ChessException("The chosen piece is not yours");
        }
    }

    public void switchTurn() {
        turn++;
        currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
    }

    private void placeNewPiece(char column, int row, ChessPiece chessPiece){
        board.placePiece(chessPiece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup(){
        placeNewPiece('c', 1, new Rook(board, Player.WHITE));
        placeNewPiece('c', 2, new Rook(board, Player.WHITE));
        placeNewPiece('d', 2, new Rook(board, Player.WHITE));
        placeNewPiece('e', 2, new Rook(board, Player.WHITE));
        placeNewPiece('e', 1, new Rook(board, Player.WHITE));
        placeNewPiece('d', 1, new King(board, Player.WHITE));

        placeNewPiece('c', 7, new Rook(board, Player.BLACK));
        placeNewPiece('c', 8, new Rook(board, Player.BLACK));
        placeNewPiece('d', 7, new Rook(board, Player.BLACK));
        placeNewPiece('e', 7, new Rook(board, Player.BLACK));
        placeNewPiece('e', 8, new Rook(board, Player.BLACK));
        placeNewPiece('d', 8, new King(board, Player.BLACK));
    }
}