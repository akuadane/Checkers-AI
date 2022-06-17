package com.checkers.models.prefs;

import com.checkers.models.players.Player;
import javafx.scene.Scene;

public class Config {
    private GameType gameType;
    private Level level;
    private Player player;
    private Scene choosePlayerScene;


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Config{" +
                "gameType=" + gameType +
                ", level=" + level +
                '}';
    }

    public Scene getChoosePlayerScene() {
        return choosePlayerScene;
    }

    public void setChoosePlayerScene(Scene choosePlayerScene) {
        this.choosePlayerScene = choosePlayerScene;
    }
}
