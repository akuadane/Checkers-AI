package com.checkers.controller;

import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobotException;
import org.testfx.matcher.control.LabeledMatchers;

public class ChoosePlayerTest extends FxBaseTest {
    final String WITH_COMPUTER_ID = "#playWithComputerButton";
    final String WITH_PLAYER_ID = "#playWithPlayerButton";
    final String WITH_REMOTE_ID = "#playRemoteButton";

    @Test(expected = FxRobotException.class)
    public void clickOnBogusElement() {
        clickOn("#selector");
    }

    @Test
    public void should_contain_play_with_computer_button() {
        FxAssert.verifyThat(WITH_COMPUTER_ID, LabeledMatchers.hasText("Play with Computer"));
    }

    @Test
    public void should_contain_play_with_player_button() {
        FxAssert.verifyThat(WITH_PLAYER_ID, LabeledMatchers.hasText("Play with Player"));
    }

    @Test
    public void should_contain_play_remote_button() {
        FxAssert.verifyThat(WITH_REMOTE_ID, LabeledMatchers.hasText("Play Remote"));
    }
}
