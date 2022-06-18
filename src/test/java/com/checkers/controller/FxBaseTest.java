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
import org.testfx.util.WaitForAsyncUtils;

public class FxBaseTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(ChoosePlayer.class);
        clickOn("#playWithComputerButton");
        Thread.sleep(1000);
        WaitForAsyncUtils.waitForFxEvents();
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
