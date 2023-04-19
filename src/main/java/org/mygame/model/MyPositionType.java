package org.mygame.model;

import javafx.scene.Node;

public class MyPositionType extends Node {
    private Integer column;
    private Integer row;

    public MyPositionType(Integer column, Integer row) {
        this.column = column;
        this.row = row;
    }

    public void setColumn(Integer column) {
        this.column=column;
    }

    public void setRow(Integer row) {
        this.row=row;
    }
    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }

    @Override
    public String toString() {
        return column+" "+row;
    }
}
