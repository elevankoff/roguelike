package com.roguelike.roguelike.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Polygon;
import com.roguelike.roguelike.model.Hero;
import com.roguelike.roguelike.view.GameScreen;

public class HeroController {

    private Polygon heroBounds;
    public HeroController(Polygon heroBounds) {
        this.heroBounds = heroBounds;
    }


    float verticalSpeed = Hero.verticalSpeed;
    float horizontalSpeed = Hero.horizontalSpeed;
    public void handle(){
        float curVerticalSpeed = 0f;
        float curHorizontalSpeed = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            curVerticalSpeed += verticalSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            curVerticalSpeed -= verticalSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            curHorizontalSpeed += horizontalSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            curHorizontalSpeed -= horizontalSpeed;
        }

        heroBounds.setPosition(heroBounds.getX() + curHorizontalSpeed * GameScreen.deltaCff,
                heroBounds.getY() + curVerticalSpeed * GameScreen.deltaCff);
    }
}
