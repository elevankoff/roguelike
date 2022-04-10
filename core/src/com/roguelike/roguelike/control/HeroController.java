package com.roguelike.roguelike.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.roguelike.roguelike.model.Hero;

public class HeroController implements InputProcessor {
    private static final float VERTICAL_SPEED = Hero.VERTICAL_SPEED;;
    private static final float HORIZONTAL_SPEED = Hero.HORIZONTAL_SPEED;

    private final Hero hero;
    private Vector2 direction;

    public HeroController(Hero hero) {
        this.hero = hero;
        this.direction = Vector2.Zero;
    }

    public void update(float delta) {
        move(direction.x * delta, direction.y * delta);
    }

    @Override
    public boolean keyDown(int keycode) {
        processInput();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        processInput();
        return true;
    }

    private void processInput() {
        direction = new Vector2(getCurHorizontalSpeed(), getCurVerticalSpeed());
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    private float getCurHorizontalSpeed() {
        float curHorizontalSpeed = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            curHorizontalSpeed += HORIZONTAL_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            curHorizontalSpeed -= HORIZONTAL_SPEED;
        }
        return curHorizontalSpeed;
    }

    private float getCurVerticalSpeed() {
        float curVerticalSpeed = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            curVerticalSpeed += VERTICAL_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            curVerticalSpeed -= VERTICAL_SPEED;
        }
        return curVerticalSpeed;
    }

    public void move(float difX, float difY) {
        hero.setPosition(hero.getX() + difX, hero.getY() + difY);
    }
}
