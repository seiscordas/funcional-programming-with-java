package com.kl.application;

import com.kl.chess.ChessMatch;
import com.kl.chess.ChessPiece;
import com.kl.chess.ChessPosition;
import com.kl.chess.Player;

import java.util.*;

public class UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner scanner){
        try {
            String input = scanner.nextLine();
            char column = input.charAt(0);
            int row = Integer.parseInt(input.substring(1));
            return new ChessPosition(column, row);
        }
        catch (RuntimeException e){
            throw new InputMismatchException("Error: to instantiate ChessPosition value must be from a1 to h8.");
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> capturedChessPieceList){
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(capturedChessPieceList);
        System.out.println();
        System.out.println("Turn: " + chessMatch.getTurn());
        System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
        if(chessMatch.isCheck()){
            System.out.println("CHECK!");
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        printBoard(pieces, null);
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        System.out.println("  a b c d e f g h");
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces[i].length; j++) {
                printPiece(pieces[i][j], possibleMoves != null && possibleMoves[i][j]);
            }
            System.out.print((8 - i) + " ");
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void printPiece(ChessPiece piece, boolean background) {
        printBackgroundIfTrue(background);
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            String colorCode = (piece.getPlayer() == Player.WHITE) ? ANSI_WHITE : ANSI_BLUE;
            System.out.print(colorCode + piece + ANSI_RESET);
        }
        System.out.print(" ");
    }

    private static void printBackgroundIfTrue(boolean background) {
        if (background) {
            System.out.print(ANSI_GREEN_BACKGROUND);
        }
    }

    private static void printCapturedPieces(List<ChessPiece> capturedChessPieceList){
        List<ChessPiece> whitePieces = capturedChessPieceList.stream()
                .filter(x -> x.getPlayer() == Player.WHITE).toList();
        List<ChessPiece> blackPieces = capturedChessPieceList.stream()
                .filter(x -> x.getPlayer() == Player.BLACK).toList();
        System.out.println("Captured pieces:");
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(whitePieces.toArray()));
        System.out.print(ANSI_RESET);
        System.out.print("Black: ");
        System.out.print(ANSI_BLUE);
        System.out.println(Arrays.toString(blackPieces.toArray()));
        System.out.print(ANSI_RESET);
    }
}
