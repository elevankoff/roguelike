package com.roguelike.roguelike.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.roguelike.roguelike.model.Hero;

public class HeroController implements InputProcessor {
    private final Hero hero;
    private Vector2 currentDirection;

    public HeroController(Hero hero) {
        this.hero = hero;
        this.currentDirection = Vector2.Zero;
    }

    public void update(float delta) {
        move(currentDirection.x * delta, currentDirection.y * delta);
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
        currentDirection = new Vector2(getCurHorizontalSpeed(), getCurVerticalSpeed());
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
        float currentHorizontalSpeed = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            currentHorizontalSpeed += Hero.HORIZONTAL_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            currentHorizontalSpeed -= Hero.HORIZONTAL_SPEED;
        }
        return currentHorizontalSpeed;
    }

    private float getCurVerticalSpeed() {
        float currentVerticalSpeed = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            currentVerticalSpeed += Hero.VERTICAL_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            currentVerticalSpeed -= Hero.VERTICAL_SPEED;
        }
        return currentVerticalSpeed;
    }

    public void move(float difX, float difY) {
        hero.setPosition(hero.getX() + difX, hero.getY() + difY);
    }
}
