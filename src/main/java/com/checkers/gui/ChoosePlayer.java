package com.checkers.gui;

import com.checkers.models.prefs.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChoosePlayer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChoosePlayer.class.getResource("/choose-player.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Config config = new Config();
        config.setChoosePlayerScene(scene);
        stage.setUserData(config);
        stage.setTitle("Choose Player");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}