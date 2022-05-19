package com.roguelike.roguelike.control;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.roguelike.roguelike.model.AliveObject;

import java.time.Instant;

public class MobController {
    private static final int attackPeriodMillis = 2000;
    private static final int ATTACK_RADIUS = 10; // todo: make configurable

    private final AliveObject mob;
    private final AliveObject hero;
    private Instant lastAttackTimestamp;

    @SuppressWarnings("NewApi")
    public MobController(AliveObject mob, AliveObject hero) {
        this.mob = mob;
        this.hero = hero;
        this.lastAttackTimestamp = Instant.now();
    }

    public Vector2 getNextPosition() {
        Vector2 nextDirection = getNextDirection();
        Vector2 currentPosition = mob.getPosition();
        return currentPosition.cpy().add(nextDirection);
    }

    private Vector2 getNextDirection() {
        float xDir = hero.getX() - mob.getX();
        float yDir = hero.getY() - mob.getY();
        return new Vector2(xDir, yDir).nor();
    }

    public void update() {
        move(getNextDirection());
    }

    public void move(Vector2 direction) {
        mob.setPosition(mob.getPosition().cpy().add(direction));
    }

    @SuppressWarnings("NewApi")
    public void process() {
        if (mob.isDead()) {
            return;
        }
        if (isAttacking()) {
            Circle attackCircle = new Circle(mob.getPosition(), ATTACK_RADIUS);
            if (Intersector.overlaps(attackCircle, hero.getSprite().getBoundingRectangle())) {
                hero.hit(mob.getStrength());
            }
            lastAttackTimestamp = Instant.now();
        }
    }

    @SuppressWarnings("NewApi")
    private boolean isAttacking() {
        return lastAttackTimestamp.isBefore(Instant.now().minusMillis(attackPeriodMillis));
    }
}
