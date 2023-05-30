package com.kl.application;

import com.kl.chess.ChessMatch;
import com.kl.chess.ChessPiece;
import com.kl.chess.ChessPosition;
import com.kl.chess.exceptions.ChessException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true){
            try {
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces());
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(scanner);

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(scanner);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
            }
            catch (ChessException | InputMismatchException e){
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }
}