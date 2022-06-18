package com.checkers.gui;


import com.checkers.models.move.Position;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


public class BoardSquare extends Button {
    private final Color squareColor;
    public static final int SIZE = 70;
    private final double PIECE_SIZE = SIZE - 10.0;
    private final Background redBackround = new Background(new BackgroundFill(Color.valueOf("#A60300"), CornerRadii.EMPTY, Insets.EMPTY));
    private final int r;
    private final int c;

    public BoardSquare(int r, int c) {
        boolean light = (r + c) % 2 == 0;
        this.r = r;
        this.c = c;
        Color GRAY = Color.valueOf("#282922FF");
        Color WHITE = Color.valueOf("#FFF0DCFF");
        this.squareColor = (light) ? WHITE : GRAY;
        setDisable(light);
        setPrefSize(SIZE, SIZE);
        setMinSize(SIZE, SIZE);
    }

    public Position getCoordinate() {
        return new Position(this.r, this.c);
    }

    public void setPiece(String piece) {
        String[] pieces = piece.split(" ");
        String type = pieces[0];
        String owner = pieces[1];
        ImageView player1King = new ImageView(String.valueOf(getClass().getResource("/black-king.png")));
        ImageView player1 = new ImageView(String.valueOf(getClass().getResource("/black.png")));
        ImageView player2King = new ImageView(String.valueOf(getClass().getResource("/red-king.png")));
        ImageView player2 = new ImageView(String.valueOf(getClass().getResource("/red.png")));
        player1.setFitWidth(PIECE_SIZE);
        player1.setFitHeight(PIECE_SIZE);
        player2.setFitWidth(PIECE_SIZE);
        player2.setFitHeight(PIECE_SIZE);
        player1King.setFitWidth(PIECE_SIZE);
        player1King.setFitHeight(PIECE_SIZE);
        player2King.setFitWidth(PIECE_SIZE);
        player2King.setFitHeight(PIECE_SIZE);
        if (type.equalsIgnoreCase("King")) if (owner.equalsIgnoreCase("player1")) {
            Platform.runLater(() -> setGraphic(player1King));
        } else {
            Platform.runLater(() -> setGraphic(player2King));
        }

        else if (owner.equalsIgnoreCase("player1")) {
            Platform.runLater(() -> setGraphic(player1));
        } else {
            Platform.runLater(() -> setGraphic(player2));
        }

    }

    public void setPiece() {
        Platform.runLater(() -> setGraphic(null));
    }

    public void setHighlight(boolean highlight) {
        if (highlight) {

            Platform.runLater(() -> setBackground(redBackround));
        } else {
            Platform.runLater(() -> setBackground(new Background(new BackgroundFill(this.squareColor, CornerRadii.EMPTY, Insets.EMPTY))));
        }
    }
}
