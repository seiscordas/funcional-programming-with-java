package com.kl.chess;

import com.kl.boardgame.Board;
import com.kl.boardgame.Piece;
import com.kl.boardgame.Position;
import lombok.Getter;

public abstract class ChessPiece extends Piece {

    @Getter
    private Player player;

    public ChessPiece(Board board, Player player) {
        super(board);
        this.player = player;
    }

    protected boolean isThereOpponentPiece(Position position){
        ChessPiece piecePosition = (ChessPiece) getBoard().piece(position);
        return piecePosition != null && piecePosition.getPlayer() != player;
    }
}
