package com.kl.chess.pieces;

import com.kl.boardgame.Board;
import com.kl.chess.ChessPiece;
import com.kl.chess.Player;

public class Bishop extends ChessPiece {
    public Bishop(Board board, Player player) {
        super(board, player);
    }

    @Override
    public boolean[][] possibleMoves() {

        int[][] directions = {
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        return calculatePossibleMovesForQueenBishopAndRook(directions);
    }

    @Override
    public String toString() {
        return "B";
    }
}
