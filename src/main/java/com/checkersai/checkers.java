package com.checkersai;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class checkers extends Application  {

    //Setting text to the button


    public static final int TILE_SIZE = 70;// the size of each tile on the board
    public static  int WIDTH = 8;// the number of tiles on the width of the board
    public static  int HEIGHT = 8; // the number of tiles on the height of the board
    private Tile[][] board = new Tile[WIDTH][HEIGHT];
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    private Parent createContent(){

        Pane root = new Pane();
       // Button button = new Button();
        //Setting text to the button
        //button.setText("Sample Button");
        //Setting the location of the button
        //button.setTranslateX(10);
        //button.setTranslateY(10);
        //button.setMinSize(10,10);
        root.setPrefSize(WIDTH * TILE_SIZE,HEIGHT * TILE_SIZE);
       // root.setPrefSize(600,6);
        root.layoutXProperty().bind(root.widthProperty().subtract(root.prefWidth(-1)).divide(2));
        root.layoutYProperty().bind(root.heightProperty().subtract(root.prefWidth(-1)).divide(1.2));
        root.getChildren().addAll(tileGroup, pieceGroup);

        //populating the tiles
        for (int y= 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x+y) % 2 == 0, x, y);
                board[x][y] = tile;
                tileGroup.getChildren().add(tile);
                Piece piece = null;
                if (y <= 2 && (x + y) % 2 !=0){
                    piece = makePiece(PieceType.RED,x,y);
                }
                if (y >= 5 && (x + y) % 2 !=0){
                    piece = makePiece(PieceType.Black,x,y);
                }
                if (piece != null){
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }


            }
        }

        return root;

    }
    private MoveResult tryMove(Piece piece, int newX, int newY){
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0){
            return new MoveResult(MoveType.NONE);
        }
        int x0= toBoard(piece.getOldX());
        int y0= toBoard(piece.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir){
            return new MoveResult(MoveType.NORMAL);
        } else if (Math.abs(newX - x0) == 2 && newY -y0 == piece.getType().moveDir * 2){
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;
            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()){
                return new MoveResult(MoveType.KILL,board[x1][y1].getPiece());

            }

        };

        return new MoveResult(MoveType.NONE);
    }
    private int toBoard(double pixel){
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }
    @Override
    public void start(Stage primarystage) throws Exception {

        Scene scene = new Scene(createContent(),1400,700);
        scene.setFill(Color.web("A47444"));
        //Button button = new Button();
        //button.setTranslateX(10);
        //button.setTranslateY(10);
        //Group root = new Group(button);
        primarystage.initStyle(StageStyle.UNDECORATED);
        primarystage.setScene(scene);
        primarystage.setResizable(false);
        primarystage.show();
    }

    private Piece makePiece(PieceType type, int x,int y){
        Piece piece = new Piece(type, x, y);
        piece.setOnMouseReleased(e ->{
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());
            MoveResult result = tryMove(piece, newX, newY);

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());
            switch (result.getTYpe()){
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    break;
                case KILL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece otherPiece =result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    break;
            }

        });

        return piece;
    }
    public static void main(String[] args) {
        launch(args);

    }

   /* @Override
    public void handle(ActionEvent actionEvent) {

    }*/
}