package com.kl.boardgame;

import lombok.AccessLevel;
import lombok.Getter;

public class Piece {
    protected Position position;

    @Getter(AccessLevel.PROTECTED)
    private Board board;

    public Piece(Board board) {
        this.board = board;
    }
}
