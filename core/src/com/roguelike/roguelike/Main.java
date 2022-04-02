package com.roguelike.roguelike;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.roguelike.roguelike.view.GameScreen;

public class Main extends Game {

    private Screen gameScreen;
    @Override
    public void create() {
        gameScreen = new GameScreen();
        setScreen(gameScreen);
    }
}