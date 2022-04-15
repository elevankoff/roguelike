package com.roguelike.roguelike.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class AliveObjectFactory {
    private float modelWidth;
    private float modelHeight;

    private int startHealth;
    private int startStrength;

    private float verticalSpeed;
    protected float horizontalSpeed;

    public AliveObjectFactory(
            float modelWidth,
            float modelHeight,
            int startHealth,
            int startStrength,
            float verticalSpeed,
            float horizontalSpeed)
    {
        this.modelWidth = modelWidth;
        this.modelHeight = modelHeight;
        this.startHealth = startHealth;
        this.startStrength = startStrength;
        this.verticalSpeed = verticalSpeed;
        this.horizontalSpeed = horizontalSpeed;
    }

    public AliveObject create(Texture texture, Vector2 spawnPosition) {
        return new AliveObject(
                texture,
                spawnPosition.x,
                spawnPosition.y,
                modelWidth,
                modelHeight,
                startHealth,
                startStrength,
                verticalSpeed,
                horizontalSpeed
        );
    }
}
