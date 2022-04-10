package com.roguelike.roguelike.model;

import com.badlogic.gdx.graphics.Texture;

public class HeroFactory {
    private static final float WIDTH = 2f;
    private static final float HEIGHT = 2f;

    private static final int START_HEALTH = 100;
    private static final int START_STRENGTH = 2;

    private static final float VERTICAL_SPEED = 3f;
    private static final float HORIZONTAL_SPEED = 3f;

    public static AliveObject create(Texture heroTexture, float spawnX, float spawnY) {
        return new AliveObject(heroTexture, spawnX, spawnY, WIDTH, HEIGHT, START_HEALTH, START_STRENGTH, VERTICAL_SPEED, HORIZONTAL_SPEED);
    }
}
