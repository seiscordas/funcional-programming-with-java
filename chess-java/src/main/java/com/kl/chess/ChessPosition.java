package com.kl.chess;

import com.kl.boardgame.Position;
import com.kl.chess.exceptions.ChessException;
import lombok.Getter;

public class ChessPosition {

    @Getter
    private final char column;
    @Getter
    private final int row;

    public ChessPosition(char column, int row) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8){
            throw new ChessException("Error: to instantiate ChessPosition value must be from a1 to h8.");
        }
        this.column = column;
        this.row = row;
    }

    protected Position toPosition(){
        return new Position(8 - row, column - 'a');
    }

    protected static ChessPosition fromPosition(Position position){
        return new ChessPosition((char) ('a' - position.getColumn()), 8 - position.getRow());
    }

    @Override
    public String toString(){
        return String.valueOf(column) + row;
    }
}
