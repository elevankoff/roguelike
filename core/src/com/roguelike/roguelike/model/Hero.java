package com.roguelike.roguelike.model;

import com.badlogic.gdx.graphics.Texture;

public class Hero extends AliveObject {
    public static final float VERTICAL_SPEED = 3f;
    public static final float HORIZONTAL_SPEED = 3f;

    public Hero(Texture texture, float x, float y, float width, float height, int health, int strength) {
        super(texture, x, y, width, height, health, strength);
    }
}
