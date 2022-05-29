package com.checkers.util;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneUtils {
    public static void switchScene(Scene currentScene, Scene nextScene) {
        Stage stage = (Stage) (currentScene.getWindow());
        stage.setScene(nextScene);
        stage.show();
    }
}

