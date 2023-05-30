package com.kl.chess.exceptions;

import com.kl.boardgame.exceptions.BoardException;

public class ChessException extends BoardException {
    public ChessException(String msg){
        super(msg);
    }
}
