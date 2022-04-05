package com.checkersai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CheckersAI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CheckersAI.class.getResource("view/choose-player.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Choose Player!");
        stage.setScene(scene);
        stage.show();
    }
}
