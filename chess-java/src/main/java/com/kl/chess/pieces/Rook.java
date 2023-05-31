package com.kl.chess.pieces;

import com.kl.boardgame.Board;
import com.kl.boardgame.Position;
import com.kl.chess.ChessPiece;
import com.kl.chess.Player;

public class Rook extends ChessPiece {
    public Rook(Board board, Player player) {
        super(board, player);
    }

    @Override
    public String toString(){
        return "R";
    }

    private boolean canMove(Position position) {
        ChessPiece chessPiece = (ChessPiece) getBoard().piece(position);
        return chessPiece == null || chessPiece.getPlayer() != getPlayer();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        Position currentPosition = new Position(position.getRow(), position.getColumn());

        for (int[] direction : directions) {
            int newRow = currentPosition.getRow() + direction[0];
            int newCol = currentPosition.getColumn() + direction[1];

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
}
