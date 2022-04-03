package com.roguelike.roguelike.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.roguelike.roguelike.control.MobController;

public class Mob extends GameObject{

    public static float verticalSpeed = 3f;
    public static float horizontalSpeed = 3f;
    private MobController mobController;
    public Mob(Texture texture, float x, float y, float width, float height, Polygon heroBounds) {
        super(texture, x, y, width, height);
        mobController = new MobController(bounds, heroBounds);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        mobController.handle();
    }
}
