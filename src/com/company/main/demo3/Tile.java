package com.company.main.demo3;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    private Piece piece;
    public boolean hasPiece(){
        return piece != null;
    }
    public Piece getPiece(){
        return piece;
    }
    public void setPiece(Piece piece){
        this.piece = piece;
    }
    public Tile(boolean light, int x ,int y){
        setWidth(checkers.TILE_SIZE);//To set the size of the tile
        setHeight(checkers.TILE_SIZE);
        relocate(x * checkers.TILE_SIZE, y  * checkers.TILE_SIZE);
        setFill(light ? Color.valueOf("#A0A0A0") : Color.valueOf("#404040")); //To set the color if the tiles
    }
}
