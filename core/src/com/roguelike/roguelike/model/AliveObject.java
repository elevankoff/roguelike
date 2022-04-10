package com.roguelike.roguelike.model;

import com.badlogic.gdx.graphics.Texture;

public abstract class AliveObject extends GameObject {
    protected int health;
    protected int strength;

    public AliveObject(Texture texture, float x, float y, float width, float height, int health, int strength) {
        super(texture, x, y, width, height);
        this.health = health;
        this.strength = strength;
    }
}
