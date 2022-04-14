package com.company.main.demo3;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.example.demo3.checkers.TILE_SIZE;

public class Piece extends StackPane {
    private PieceType type;
    private double mouseX, mouseY;
    private double oldX ,oldY;
    public PieceType getType(){
        return type;
    }
    public double getOldX(){
        return oldX;
    }
    public double getOldY(){
        return oldY;
    }

    public Piece(PieceType type, int x, int y){
        this.type = type;
        move(x , y );

        Circle bg = new Circle(TILE_SIZE * 0.3125);
        bg.setFill(Color.BLACK);
        //bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE * 0);
        bg.setTranslateX((TILE_SIZE -TILE_SIZE * 0.3125 * 2)/2);
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) /2 + TILE_SIZE *0.07);
        Circle ellipse = new Circle(TILE_SIZE * 0.3125);
        ellipse.setFill(type == PieceType.RED
                ? Color.valueOf("000000") : Color.valueOf("#c40003"));
        //ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE * 0);
        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) /2);
        getChildren().addAll(bg,ellipse);
        setOnMousePressed(e ->{
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMousePressed(e -> {
             mouseX = e.getSceneX();
             mouseY = e.getSceneY();
        });
        setOnMouseDragged(e ->{
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });

    }
    public void move(int x ,int y){
        oldX= x * TILE_SIZE;
        oldY= y * TILE_SIZE;
        relocate(oldX,oldY);
    }
    public void abortMove(){
        relocate(oldX ,oldY);
    }
}
