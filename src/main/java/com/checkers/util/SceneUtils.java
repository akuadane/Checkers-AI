package com.checkers.util;

import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * Class SceneUtils contains helper methods performed on stage and scenes
 */
public class SceneUtils {
    public static void switchScene(Scene currentScene, Scene nextScene) {
        Stage stage = (Stage) (currentScene.getWindow());
        stage.setScene(nextScene);
        stage.show();
    }
    public static void fadeInTransition(Parent root) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(root);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public static void fakeOutTransition(Parent parent, Callback<Void, Void> function) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(parent);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(actionEvent -> {
            try {
                function.call(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        fadeTransition.play();
    }
}

