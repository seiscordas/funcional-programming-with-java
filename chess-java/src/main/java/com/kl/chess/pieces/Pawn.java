package com.kl.chess.pieces;

import com.kl.boardgame.Board;
import com.kl.boardgame.Position;
import com.kl.chess.ChessPiece;
import com.kl.chess.Player;

import java.awt.*;

public class Pawn extends ChessPiece {
    public Pawn(Board board, Player player) {
        super(board, player);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        int direction = (getPlayer() == Player.WHITE) ? -1 : 1;

        Position currentPosition = new Position(position.getRow(), position.getColumn());

        Position singleMove = new Position(currentPosition.getRow() + direction, currentPosition.getColumn());
        if (getBoard().positionExists(singleMove) && !getBoard().thereIsAPiece(singleMove)) {
            possibleMoves[singleMove.getRow()][singleMove.getColumn()] = true;
        }

        Position doubleMove = new Position(currentPosition.getRow() + (2 * direction), currentPosition.getColumn());
        if (getBoard().positionExists(doubleMove) && !getBoard().thereIsAPiece(doubleMove)
                && getMoveCount() == 0) {
            possibleMoves[doubleMove.getRow()][doubleMove.getColumn()] = true;
        }

        int[] colOffsets = {-1, 1};
        for (int colOffset : colOffsets) {
            Position capture = new Position(currentPosition.getRow() + direction, currentPosition.getColumn() + colOffset);
            if (getBoard().positionExists(capture) && getBoard().thereIsAPiece(capture)
                    && isThereOpponentPiece(capture)) {
                possibleMoves[capture.getRow()][capture.getColumn()] = true;
            }
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "P";
    }
}
