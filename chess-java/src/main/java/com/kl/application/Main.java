package com.kl.application;

import com.kl.chess.ChessMatch;
import com.kl.chess.ChessPiece;
import com.kl.chess.ChessPosition;
import com.kl.chess.exceptions.ChessException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> capturedChessPieceList = new ArrayList<>();

        while (!chessMatch.isCheckMate()){
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, capturedChessPieceList);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(scanner);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(scanner);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                if(capturedPiece != null){
                    capturedChessPieceList.add(capturedPiece);
                }
            }
            catch (ChessException | InputMismatchException e){
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(chessMatch, capturedChessPieceList);
    }
}