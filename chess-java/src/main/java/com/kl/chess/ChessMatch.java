package com.kl.chess;

import com.kl.boardgame.Board;
import com.kl.boardgame.Piece;
import com.kl.boardgame.Position;
import com.kl.chess.exceptions.ChessException;
import com.kl.chess.pieces.*;
import lombok.Getter;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessMatch {

    @Getter
    private int turn;
    @Getter
    private Player currentPlayer;
    private final Board board;
    @Getter
    private boolean check;
    @Getter
    private boolean checkMate;
    @Getter
    private ChessPiece enPassantVulnerable;
    @Getter
    private ChessPiece promoted;

    private final List<Piece> piecesOnTheBoard = new ArrayList<>();
    private final List<Piece> capturedChessPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Player.WHITE;
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        int rows = board.getRows();
        int columns = board.getColumns();
        ChessPiece[][] chessPieces = new ChessPiece[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                chessPieces[i][j] = (ChessPiece) board.piece(i, j);
            }
        }

        return chessPieces;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        if (isKingInCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You cannot put yourself in check");
        }

        ChessPiece movedPiece = (ChessPiece) board.piece(target);

        promoted = null;
        if (movedPiece instanceof Pawn) {
            if (target.getRow() == 0 || target.getRow() == 7) {
                promoted = (ChessPiece) board.piece(target);
                promoted = replacePromotedPiece("Q");
            }
        }

        check = isKingInCheck((opponent(currentPlayer)));

        if (isKingInCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            switchTurn();
        }

        checkEnPassant(source, target, movedPiece);

        return (ChessPiece) capturedPiece;
    }

    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) {
            throw new IllegalStateException("There is no piece to be promoted");
        }
        String[] pieceToPromote = {"Q", "N", "B", "R"};
        if (!Arrays.asList(pieceToPromote).contains(type)) {
            return promoted;
        }
        Position position = promoted.getChessPosition().toPosition();
        Piece piece = board.removePiece(position);
        piecesOnTheBoard.remove(piece);

        ChessPiece newPiece = newPiece(type, promoted.getPlayer());
        board.placePiece(newPiece, position);
        piecesOnTheBoard.add(newPiece);

        return newPiece;
    }

    private ChessPiece newPiece(String type, Player player) {
        return switch (type) {
            case "B" -> new Bishop(board, player);
            case "N" -> new Knight(board, player);
            case "R" -> new Rook(board, player);
            default -> new Queen(board, player);
        };

    }

    private void checkEnPassant(Position source, Position target, ChessPiece movedPiece) {
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2) || target.getRow() == source.getRow() + 2) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("Chosen piece cannot be moved to destination position!");
        }
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece piece = (ChessPiece) board.removePiece(source);
        piece.increaseMoveCount();

        Piece capturedPiece = board.removePiece(target);
        board.placePiece(piece, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedChessPieces.add(capturedPiece);
        }

        if (piece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position rookSource = new Position(source.getRow(), source.getColumn() + 3);
            Position rookTarget = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookSource);
            board.placePiece(rook, rookTarget);
            rook.increaseMoveCount();
        }
        if (piece instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position rookSource = new Position(source.getRow(), source.getColumn() - 4);
            Position rookTarget = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookSource);
            board.placePiece(rook, rookTarget);
            rook.increaseMoveCount();
        }

        if (piece instanceof Pawn) {
            int direction = piece.getPlayer() == Player.WHITE ? 1 : -1;
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition = new Position(target.getRow() + direction, target.getColumn());
                capturedPiece = board.removePiece(pawnPosition);
                capturedChessPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece piece = (ChessPiece) board.removePiece(target);
        piece.decreaseMoveCount();

        board.placePiece(piece, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedChessPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        if (piece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position rookSource = new Position(source.getRow(), source.getColumn() + 3);
            Position rookTarget = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookTarget);
            board.placePiece(rook, rookSource);
            rook.decreaseMoveCount();
        }
        if (piece instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position rookSource = new Position(source.getRow(), source.getColumn() - 4);
            Position rookTarget = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookTarget);
            board.placePiece(rook, rookSource);
            rook.decreaseMoveCount();
        }

        if (piece instanceof Pawn) {
            int direction = piece.getPlayer() == Player.WHITE ? 3 : 4;
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece) board.removePiece(target);
                Position pawnPosition = new Position(direction, target.getColumn());
                board.placePiece(pawn, pawnPosition);
            }
        }
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position.");
        }

        validateTurnOfPlayer(position);

        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece!");
        }
    }

    private void validateTurnOfPlayer(Position position) {
        if (currentPlayer != ((ChessPiece) board.piece(position)).getPlayer()) {
            throw new ChessException("The chosen piece is not yours");
        }
    }

    public void switchTurn() {
        turn++;
        currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
    }

    private Player opponent(Player player) {
        return (player == Player.WHITE) ? Player.BLACK : Player.WHITE;
    }

    private ChessPiece king(Player player) {
        List<Piece> peaceList = piecesOnTheBoard.stream()
                .filter(x -> ((ChessPiece) x).getPlayer() == player).toList();
        for (Piece piece : peaceList) {
            if (piece instanceof King) {
                return (ChessPiece) piece;
            }
        }
        throw new IllegalStateException("There is no " + player + "king on the board");
    }

    private boolean isKingInCheck(Player player) {
        Position kingPosition = king(player).getChessPosition().toPosition();
        return piecesOnTheBoard.stream()
                .filter(piece -> ((ChessPiece) piece).getPlayer() == opponent(player))
                .anyMatch(piece -> piece.possibleMoves()[kingPosition.getRow()][kingPosition.getColumn()]);
    }

    private boolean isKingInCheckMate(Player player) {
        if (!isKingInCheck(player)) {
            return false;
        }
        List<Piece> pieceList = piecesOnTheBoard.stream()
                .filter(x -> ((ChessPiece) x).getPlayer() == player).toList();
        for (Piece p : pieceList) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean isCheque = isKingInCheck(player);
                        undoMove(source, target, capturedPiece);
                        if (!isCheque) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void placeNewPiece(char column, int row, ChessPiece chessPiece) {
        board.placePiece(chessPiece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(chessPiece);
    }

    private void initialSetup() {

        placeNewPiece('a', 1, new Rook(board, Player.WHITE));
        placeNewPiece('b', 1, new Knight(board, Player.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Player.WHITE));
        placeNewPiece('d', 1, new Queen(board, Player.WHITE));
        placeNewPiece('e', 1, new King(board, Player.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Player.WHITE));
        placeNewPiece('g', 1, new Knight(board, Player.WHITE));
        placeNewPiece('h', 1, new Rook(board, Player.WHITE));

        placeNewPiece('a', 2, new Pawn(board, Player.BLACK, this));
        placeNewPiece('b', 2, new Pawn(board, Player.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Player.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Player.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Player.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Player.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Player.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Player.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Player.BLACK));
        placeNewPiece('b', 8, new Knight(board, Player.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Player.BLACK));
        placeNewPiece('d', 8, new Queen(board, Player.BLACK));
        placeNewPiece('e', 8, new King(board, Player.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Player.BLACK));
        placeNewPiece('g', 8, new Knight(board, Player.BLACK));
        placeNewPiece('h', 8, new Rook(board, Player.BLACK));

        placeNewPiece('a', 7, new Pawn(board, Player.WHITE, this));
        placeNewPiece('b', 7, new Pawn(board, Player.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Player.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Player.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Player.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Player.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Player.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Player.BLACK, this));
    }
}