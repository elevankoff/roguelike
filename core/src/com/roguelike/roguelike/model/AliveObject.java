package com.roguelike.roguelike.model;

import com.badlogic.gdx.graphics.Texture;

public class AliveObject extends GameObject {
    protected int health;
    protected int strength;
    protected float verticalSpeed;
    protected float horizontalSpeed;

    public AliveObject(
            Texture texture,
            float x, float y,
            float width, float height,
            int health, int strength,
            float verticalSpeed,
            float horizontalSpeed)
    {
        super(texture, x, y, width, height);
        this.health = health;
        this.strength = strength;
        this.verticalSpeed = verticalSpeed;
        this.horizontalSpeed = horizontalSpeed;
    }

    public float getVerticalSpeed() {
        return verticalSpeed;
    }

    public float getHorizontalSpeed() {
        return horizontalSpeed;
    }


}
