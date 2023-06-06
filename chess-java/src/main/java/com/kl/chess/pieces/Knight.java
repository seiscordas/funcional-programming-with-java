package com.kl.chess.pieces;

import com.kl.boardgame.Board;
import com.kl.chess.ChessPiece;
import com.kl.chess.Player;

public class Knight extends ChessPiece {
    public Knight(Board board, Player player) {
        super(board, player);
    }

    @Override
    public boolean[][] possibleMoves() {
        int[][] directions = {
                {-1, -2}, {-2, -1},
                {1, -2}, {2, -1},
                {-1, 2}, {-2, 1},
                {1, 2}, {2, 1},
        };
        return calculatePossibleMovesForKingAndKnight(directions);
    }

    @Override
    public String toString() {
        return "N";
    }
}
