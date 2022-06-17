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
    private final Color GRAY = Color.GRAY;
    private final Color WHITE = Color.WHITE;
    private final Color RED = Color.RED;
    private final String BASE_PATH = "/resources";
    private final Color squareColor;
    public static final int SIZE = 70;
    private final double PIECE_SIZE = SIZE - 10.0;
    private final int r;
    private final int c;

    public BoardSquare(int r, int c) {
        boolean light = (r + c) % 2 == 0;
        this.r = r;
        this.c = c;
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
        ImageView player1King = new ImageView(String.valueOf(getClass().getResource("/player1king.png")));
        ImageView player1 = new ImageView(String.valueOf(getClass().getResource("/player1.png")));
        ImageView player2King = new ImageView(String.valueOf(getClass().getResource("/player2king.png")));
        ImageView player2 = new ImageView(String.valueOf(getClass().getResource("/player2.png")));
        player1.setFitWidth(PIECE_SIZE);
        player1.setFitHeight(PIECE_SIZE);
        player2.setFitWidth(PIECE_SIZE);
        player2.setFitHeight(PIECE_SIZE);
        if (type.equalsIgnoreCase("King"))
            if (owner.equalsIgnoreCase("player1")) {
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

            Platform.runLater(() -> setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY))));
        } else {
            Platform.runLater(() -> setBackground(new Background(new BackgroundFill(this.squareColor, CornerRadii.EMPTY, Insets.EMPTY))));
        }
    }
}
