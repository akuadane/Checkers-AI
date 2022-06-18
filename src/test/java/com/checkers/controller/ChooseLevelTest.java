package com.checkers.controller;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;

public class ChooseLevelTest extends FxBaseTest {
    final String EASY_ID = "#easyButton";
    final String MEDIUM_ID = "#mediumButton";
    final String HARD_ID = "#hardButton";
    final String EXPERT_ID = "#expertButton";

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
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

