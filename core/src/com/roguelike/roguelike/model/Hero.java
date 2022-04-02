package com.roguelike.roguelike.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.roguelike.roguelike.control.HeroController;

public class Hero extends GameObject{

    public static float verticalSpeed = 3f;
    public static float horizontalSpeed = 3f;
    private HeroController heroController;
    public Hero(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        heroController = new HeroController(bounds);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        heroController.handle();
    }

}
