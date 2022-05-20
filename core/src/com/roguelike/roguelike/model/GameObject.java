package com.roguelike.roguelike.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
    protected final Polygon bounds;
    protected final Sprite object;

    public GameObject(Texture texture, float x, float y, float width, float height) {
        bounds = new Polygon(new float[]{0f, 0f, width, 0f, width, height, 0f, height});
        bounds.setPosition(x, y);
        bounds.setOrigin(width / 2f, height / 2f);

        object = new Sprite(texture);
        object.setSize(width, height);
        object.setPosition(x, y);
        object.setOrigin(width / 2f, height / 2f);
    }

    public void update() {
        object.setPosition(bounds.getX(), bounds.getY());
        object.setRotation(bounds.getRotation());
    }

    public Sprite getSprite() {
        return object;
    }

    public void draw(SpriteBatch batch) {
        object.draw(batch);
    }

    public void setPosition(float x, float y) {
        bounds.setPosition(x, y);
    }

    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    public float getX() {
        return bounds.getX();
    }

    public float getY() {
        return bounds.getY();
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }
}
