package com.roguelike.roguelike.view;

import com.badlogic.gdx.graphics.Texture;
import com.roguelike.roguelike.model.Hero;

public class HeroFactory {
    private static final float SPAWN_X = 0;
    private static final float SPAWN_Y = 0;

    private static final float WIDTH = 2f;
    private static final float HEIGHT = 2f;

    private static final int START_HEALTH = 100;
    private static final int START_STRENGTH = 2;

    public static Hero create(Texture heroTexture) {
        return new Hero(heroTexture, SPAWN_X, SPAWN_Y, WIDTH, HEIGHT, START_HEALTH, START_STRENGTH);
    }
}
