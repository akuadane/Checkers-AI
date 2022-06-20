package com.checkers.controller;

import com.checkers.gui.ChoosePlayer;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class FxBaseTest extends ApplicationTest {
    public final String EASY_ID = "#easyButton";
    public final String MEDIUM_ID = "#mediumButton";
    public final String HARD_ID = "#hardButton";
    public final String EXPERT_ID = "#expertButton";
    public final String WITH_COMPUTER_ID = "#playWithComputerButton";
    public final String WITH_PLAYER_ID = "#playWithPlayerButton";
    public final String WITH_REMOTE_ID = "#playRemoteButton";
    public final String MAIN_GRID_PANE_ID = "#mainGridPane";
    public final String SCORE_HBOX_ID = "#scoreHBox";
    public final String HOME_BUTTON_ID = "#homeButton";
    public final String UNDO_ID = "#undoButton";
    public final String INFO_ID = "#infoButton";

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(ChoosePlayer.class);
    }

    @After
    public void afterEachTest() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    public <T extends Node> T find(final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }
}
