package com.roguelike.roguelike.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.roguelike.roguelike.model.AliveObject;

public class HealthSpriteFactory {
    private static final int HEALTH_SPRITE_HEIGHT = 4;

    private final Texture healthTexture;

    public HealthSpriteFactory(Texture healthTexture) {
        this.healthTexture = healthTexture;
    }

    public Sprite create(AliveObject aliveObject) {
        Vector2 position = aliveObject.getPosition();
        Sprite sprite = new Sprite(healthTexture);
        Sprite aliveObjectSprite = aliveObject.getSprite();
        float width = aliveObjectSprite.getWidth() * aliveObject.getHealthPercent();
        sprite.setBounds(position.x, position.y + aliveObjectSprite.getHeight(), width, HEALTH_SPRITE_HEIGHT);
        return sprite;
    }
}
