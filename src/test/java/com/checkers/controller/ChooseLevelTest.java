package com.checkers.controller;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

public class ChooseLevelTest extends FxBaseTest {
    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @Test
    public void should_contain_easy_button() {
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("EASY"));
    }

    @Test
    public void should_contain_medium_button() {
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("MEDIUM"));
    }

    @Test
    public void should_contain_hard_button() {
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("HARD"));
    }

    @Test
    public void should_contain_expert_button() {
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("EXPERT"));
    }

}

