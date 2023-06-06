package com.kl.chess;

import com.kl.boardgame.Board;
import com.kl.boardgame.Piece;
import com.kl.boardgame.Position;
import lombok.Getter;

public abstract class ChessPiece extends Piece {

    @Getter
    private Player player;
    @Getter
    private int moveCount;

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    public ChessPiece(Board board, Player player) {
        super(board);
        this.player = player;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece piecePosition = (ChessPiece) getBoard().piece(position);
        return piecePosition != null && piecePosition.getPlayer() != player;
    }

    private boolean canMoveTo(Position position) {
        ChessPiece chessPiece = (ChessPiece) getBoard().piece(position);
        return chessPiece == null || chessPiece.getPlayer() != getPlayer();
    }

    protected boolean[][] calculatePossibleMovesForQueenBishopAndRook(int[][] directions) {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        for (int[] direction : directions) {
            int newRow = getCurrentPosition().getRow() + direction[0];
            int newCol = getCurrentPosition().getColumn() + direction[1];

            Position newPosition = new Position(newRow, newCol);

            while (getBoard().positionExists(newPosition) && !getBoard().thereIsAPiece(newPosition)) {
                possibleMoves[newRow][newCol] = true;
                newRow += direction[0];
                newCol += direction[1];
                newPosition = new Position(newRow, newCol);
            }

            if (getBoard().positionExists(newPosition) && isThereOpponentPiece(newPosition)) {
                possibleMoves[newRow][newCol] = true;
            }
        }

        return possibleMoves;
    }

    protected boolean[][] calculatePossibleMovesForKingAndKnight(int[][] directions){
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        for (int[] direction : directions) {
            int newRow = getCurrentPosition().getRow() + direction[0];
            int newCol = getCurrentPosition().getColumn() + direction[1];

            Position newPosition = new Position(newRow, newCol);

            if (getBoard().positionExists(newPosition) && canMoveTo(newPosition)) {
                possibleMoves[newRow][newCol] = true;
            }
        }
        return possibleMoves;
    }

    protected Position getCurrentPosition() {
        return new Position(position.getRow(), position.getColumn());
    }
}
