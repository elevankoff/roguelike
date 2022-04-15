package com.roguelike.roguelike.model;

import com.badlogic.gdx.graphics.Texture;

public class AliveObject extends GameObject {
    protected final int maxHealth;
    protected int health;
    protected int strength;
    protected float verticalSpeed;
    protected float horizontalSpeed;

    public AliveObject(
            Texture texture,
            float x, float y,
            float width, float height,
            int maxHealth, int strength,
            float verticalSpeed,
            float horizontalSpeed)
    {
        super(texture, x, y, width, height);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.strength = strength;
        this.verticalSpeed = verticalSpeed;
        this.horizontalSpeed = horizontalSpeed;
    }

    public float getHealthPercent() {
        return (float) health / maxHealth;
    }

    public int getStrength() {
        return strength;
    }

    public float getVerticalSpeed() {
        return verticalSpeed;
    }

    public float getHorizontalSpeed() {
        return horizontalSpeed;
    }

    public void hit(int attackStrength) {
        this.health = Math.max(0, health - attackStrength);
    }
}
