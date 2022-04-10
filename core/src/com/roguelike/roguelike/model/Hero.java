package com.roguelike.roguelike.model;

import com.badlogic.gdx.graphics.Texture;

public class Hero extends AliveObject {
    public static final float VERTICAL_SPEED = 3f;
    public static final float HORIZONTAL_SPEED = 3f;

    public Hero(Texture texture, float x, float y, float width, float height, int health, int strength) {
        super(texture, x, y, width, height, health, strength);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AliveObject.Builder<Hero> {
        @Override
        public Hero build() {
            if (texture == null) {
                throw new IllegalArgumentException("Texture should be set");
            }
            return new Hero(texture, x, y, width, height, health, strength);
        }
    }
}
