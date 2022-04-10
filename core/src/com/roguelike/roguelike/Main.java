package com.roguelike.roguelike;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.roguelike.roguelike.view.GameScreen;

public class Main extends Game {
    @Override
    public void create() {
        Screen gameScreen = new GameScreen();
        setScreen(gameScreen);
    }
}