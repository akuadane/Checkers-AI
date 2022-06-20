package com.checkers.controller;

import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

public class ChooseLevelTest extends FxBaseTest {

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }
    @Before
    public void go_to_levels_screen() {
        clickOn("#playWithComputerButton");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void should_contain_easy_button() {
        moveTo(EASY_ID);
        FxAssert.verifyThat(EASY_ID, LabeledMatchers.hasText("Easy"));
    }

    @Test
    public void should_contain_medium_button() {
        moveTo(MEDIUM_ID);
        FxAssert.verifyThat(MEDIUM_ID, LabeledMatchers.hasText("Medium"));
    }

    @Test
    public void should_contain_hard_button() {
        moveTo(HARD_ID);
        FxAssert.verifyThat(HARD_ID, LabeledMatchers.hasText("Hard"));
    }

    @Test
    public void should_contain_expert_button() {
        moveTo(EXPERT_ID);
        FxAssert.verifyThat(EXPERT_ID, LabeledMatchers.hasText("Expert"));
    }

}

