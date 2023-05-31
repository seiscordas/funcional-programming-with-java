package com.kl.boardgame;

import lombok.AccessLevel;
import lombok.Getter;

public abstract class Piece {
    protected Position position;

    @Getter(AccessLevel.PROTECTED)
    private Board board;

    public Piece(Board board) {
        this.board = board;
    }

    public abstract boolean[][] possibleMoves();

    public boolean possibleMove(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {
        boolean[][] possibleMoves = possibleMoves();
        for (boolean[] row : possibleMoves) {
            for (boolean value : row) {
                if (value) {
                    return true;
                }
            }
        }
        return false;
    }
}
