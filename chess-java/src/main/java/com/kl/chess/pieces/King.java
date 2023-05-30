package com.kl.chess.pieces;

import com.kl.boardgame.Board;
import com.kl.chess.ChessPiece;
import com.kl.chess.Color;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "K";
    }
}
