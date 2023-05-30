package com.kl.boardgame;

import lombok.Getter;
import lombok.Setter;

public class Board {
    @Getter
    @Setter
    private Integer rows, columns;

    private Piece[][] pieces;

    public Board(Integer rows, Integer columns) {
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public Piece piece(Integer row, Integer column){
        return pieces[row][column];
    }

    public Piece piece(Position position){
        return pieces[position.getRow()][position.getColumn()];
    }
}
