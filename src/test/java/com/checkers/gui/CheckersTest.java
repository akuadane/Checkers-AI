package com.checkers.gui;

import com.checkers.controller.FxBaseTest;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.util.WaitForAsyncUtils;

public class CheckersTest extends FxBaseTest {
    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @Before
    public void goto_board() {
        clickOn(WITH_PLAYER_ID);
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void test_board() {
        GridPane gridPane = find(MAIN_GRID_PANE_ID);
        Assert.assertNotNull(gridPane);
    }

    @Test
    public void player_score_displayed() {
        HBox scoreHBox = find(SCORE_HBOX_ID);
        Assert.assertNotNull(scoreHBox);
    }

//    @Test
//    public void home_button_displayed() {
//        Button scoreHBox = find(HOME_BUTTON_ID);
//        Assert.assertNotNull(scoreHBox);
//    }

    @Test
    public void undo_info_displayed() {
        Button scoreHBox = find(UNDO_ID);
        Assert.assertNotNull(scoreHBox);
    }

    @Test
    public void info_button_displayed() {
        Button scoreHBox = find(INFO_ID);
        Assert.assertNotNull(scoreHBox);
    }

}
