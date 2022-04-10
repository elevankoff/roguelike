package com.roguelike.roguelike.model;

import com.badlogic.gdx.graphics.Texture;

public abstract class AliveObject extends GameObject {
    private int health;
    private int strength;

    public AliveObject(Texture texture, float x, float y, float width, float height, int health, int strength) {
        super(texture, x, y, width, height);
    }

    public abstract static class Builder<T extends AliveObject> extends GameObject.Builder<T> {
        protected int health = 100;
        protected int strength = 0;

        public Builder<T> setHealth(int health) {
            this.health = health;
            return this;
        }

        public Builder<T> setStrength(int strength) {
            this.strength = strength;
            return this;
        }
    }
}
