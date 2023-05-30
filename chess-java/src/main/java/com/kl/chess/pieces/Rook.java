package com.kl.chess.pieces;

import com.kl.boardgame.Board;
import com.kl.chess.ChessPiece;
import com.kl.chess.Color;

public class Rook extends ChessPiece {
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "R";
    }
}
