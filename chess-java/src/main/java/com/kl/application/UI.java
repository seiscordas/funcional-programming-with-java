package com.kl.application;

import com.kl.chess.ChessPiece;

public class UI {

    public static void printBoard(ChessPiece[][] pieces) {
        System.out.println("  a b c d e f g h");
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces[i].length; j++) {
                printPiece(pieces[i][j]);
            }
            System.out.println();
        }
    }

    private static void printPiece(ChessPiece chessPiece) {
        String pieceString = (chessPiece == null) ? "-" : chessPiece.toString();
        System.out.print(pieceString + " ");
    }
}
