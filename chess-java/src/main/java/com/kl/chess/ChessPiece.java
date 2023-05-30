package com.kl.chess;

import com.kl.boardgame.Board;
import com.kl.boardgame.Piece;
import lombok.Getter;

public class ChessPiece extends Piece {

    @Getter
    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }
}
