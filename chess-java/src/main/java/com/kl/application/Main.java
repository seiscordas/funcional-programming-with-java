package com.kl.application;

import com.kl.chess.ChessMatch;

public class Main {
    public static void main(String[] args) {
        //Board board = new Board(8,8);
        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());
    }
}