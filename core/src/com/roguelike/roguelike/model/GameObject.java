package com.roguelike.roguelike.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;

public abstract class GameObject {
    protected final Polygon bounds;
    protected final Sprite object;

    public GameObject(Texture texture, float x, float y, float width, float height) {
        bounds = new Polygon(new float[]{0f, 0f, width, 0f, width, height, 0f, height});
        bounds.setPosition(x, y);
        bounds.setOrigin(width / 2f, height / 2f);

        object = new Sprite(texture);
        object.setSize(width, height);
        object.setOrigin(width / 2f, height / 2f);
    }

    public void update() {
        //object.setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        object.setPosition(bounds.getX(), bounds.getY());
        object.setRotation(bounds.getRotation());
    }

    public void draw(SpriteBatch batch) {
        object.draw(batch);
    }

    public void setPosition(float x, float y) {
        bounds.setPosition(x, y);
    }

    public float getX() {
        return bounds.getX();
    }

    public float getY() {
        return bounds.getY();
    }

    public static abstract class Builder<T extends GameObject> {
        protected Texture texture;
        protected float x = 0f;
        protected float y = 0f;
        protected float width = 1f;
        protected float height = 1f;

        public abstract T build();

        public Builder<T> setTexture(Texture texture) {
            this.texture = texture;
            return this;
        }

        public Builder<T> setX(float x) {
            this.x = x;
            return this;
        }

        public Builder<T> setY(float y) {
            this.y = y;
            return this;
        }

        public Builder<T> setWidth(float width) {
            this.width = width;
            return this;
        }

        public Builder<T> setHeight(float height) {
            this.height = height;
            return this;
        }
    }
}
