package com.kl.chess.pieces;

import com.kl.boardgame.Board;
import com.kl.boardgame.Position;
import com.kl.chess.ChessPiece;
import com.kl.chess.Player;

public class King extends ChessPiece {
    public King(Board board, Player player) {
        super(board, player);
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position) {
        ChessPiece chessPiece = (ChessPiece) getBoard().piece(position);
        return chessPiece == null || chessPiece.getPlayer() != getPlayer();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        int[][] offsets = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        Position currentPosition = new Position(position.getRow(), position.getColumn());

        for (int[] offset : offsets) {
            int newRow = currentPosition.getRow() + offset[0];
            int newCol = currentPosition.getColumn() + offset[1];

            Position newPosition = new Position(newRow, newCol);

            if (getBoard().positionExists(newPosition) && canMove(newPosition)) {
                possibleMoves[newRow][newCol] = true;
            }
        }
        return possibleMoves;
    }
}
