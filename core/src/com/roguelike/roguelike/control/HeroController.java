package com.roguelike.roguelike.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.roguelike.roguelike.model.AliveObject;
import com.roguelike.roguelike.model.GameContext;
import com.roguelike.roguelike.view.Mob;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class HeroController implements InputProcessor {
    private static final int attackPeriodMillis = 400;
    private static final int ATTACK_RADIUS = 20; // todo: make configurable

    private final AliveObject hero;
    private Vector2 currentDirection;
    private GameContext gameContext;
    private Instant lastAttackTimestamp;

    @SuppressWarnings("NewApi")
    public HeroController(AliveObject hero, GameContext gameContext) {
        this.hero = hero;
        this.currentDirection = Vector2.Zero;
        this.gameContext = gameContext;
        this.lastAttackTimestamp = Instant.now();
    }

    public Vector2 getNextPosition(float delta) {
        processInput();
        Vector2 nextDirection = getNextDirection(delta);
        Vector2 currentPosition = hero.getPosition();
        return currentPosition.cpy().add(nextDirection);
    }

    private Vector2 getNextDirection(float delta) {
        processInput();
        return new Vector2(currentDirection.x * delta, currentDirection.y * delta);
    }

    public void update(float delta) {
        if (hero.isDead()) {
            return;
        }
        move(getNextDirection(delta));
    }

    public void move(Vector2 direction) {
        hero.setPosition(hero.getPosition().cpy().add(direction));
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

    @SuppressWarnings("NewApi")
    private void processInput() {
        currentDirection = new Vector2(getCurHorizontalSpeed(), getCurVerticalSpeed());
        if (isAttacking()) {
            List<AliveObject> mobs = gameContext.getMobs().stream().map(Mob::getObject).collect(Collectors.toList());
            Circle attackCircle = new Circle(hero.getPosition(), ATTACK_RADIUS);
            mobs.forEach(mob -> {
                if (Intersector.overlaps(attackCircle, mob.getSprite().getBoundingRectangle())) {
                    mob.hit(hero.getStrength());
                }
            });
            lastAttackTimestamp = Instant.now();
        }
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
            currentHorizontalSpeed += hero.getHorizontalSpeed();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            currentHorizontalSpeed -= hero.getHorizontalSpeed();
        }
        return currentHorizontalSpeed;
    }

    private float getCurVerticalSpeed() {
        float currentVerticalSpeed = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            currentVerticalSpeed += hero.getVerticalSpeed();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            currentVerticalSpeed -= hero.getVerticalSpeed();
        }
        return currentVerticalSpeed;
    }

    @SuppressWarnings("NewApi")
    private boolean isAttacking() {
        return lastAttackTimestamp.isBefore(Instant.now().minusMillis(attackPeriodMillis))
                && Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }
}
