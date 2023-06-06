package com.kl.chess.pieces;

import com.kl.boardgame.Board;
import com.kl.boardgame.Position;
import com.kl.chess.ChessMatch;
import com.kl.chess.ChessPiece;
import com.kl.chess.Player;

public class King extends ChessPiece {

    private final ChessMatch chessMatch;

    public King(Board board, Player player, ChessMatch chessMatch) {
        super(board, player);
        this.chessMatch = chessMatch;
    }

    private boolean isRookCastling(Position position){
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece instanceof Rook && piece.getPlayer() == getPlayer() && piece.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };
        boolean[][] possibleMoves = calculatePossibleMovesForKingAndKnight(directions);

        if (!hasMoved() && chessMatch.isCheck()) {
            Position[][] rookPositions = {
                    {new Position(position.getRow(), position.getColumn() - 4), new Position(position.getRow(), position.getColumn() - 2)},
                    {new Position(position.getRow(), position.getColumn() + 3), new Position(position.getRow(), position.getColumn() + 2)}
            };

            for (Position[] positions : rookPositions) {
                if (isRookCastling(positions[0])) {
                    if (canCastle(positions[1])) {
                        chessMatch.setCastle(true);
                        possibleMoves[positions[1].getRow()][positions[1].getColumn()] = true;
                    }
                }
            }
        }

        return possibleMoves;
    }

    private boolean hasMoved() {
        return getMoveCount() > 0;
    }

    private boolean canCastle(Position targetPosition) {
        Position currentPosition = new Position(position.getRow(), position.getColumn());
        Position rookPosition = new Position(targetPosition.getRow(), targetPosition.getColumn());

        int startCol = Math.min(currentPosition.getColumn(), rookPosition.getColumn());
        int endCol = Math.max(currentPosition.getColumn(), rookPosition.getColumn());

        for (int col = startCol + 1; col < endCol; col++) {
            Position intermediatePosition = new Position(currentPosition.getRow(), col);
            if (getBoard().thereIsAPiece(intermediatePosition)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "K";
    }
}
