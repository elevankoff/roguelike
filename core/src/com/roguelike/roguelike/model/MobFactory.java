package com.roguelike.roguelike.model;

public class MobFactory extends AliveObjectFactory {
    // not private for tests
    static final float WIDTH = 15f;
    static final float HEIGHT = 15f;

    static final int START_HEALTH = 100;
    static final int START_STRENGTH = 10;

    static final float VERTICAL_SPEED = 1f;
    static final float HORIZONTAL_SPEED = 1f;

    public MobFactory() {
        super(WIDTH, HEIGHT, START_HEALTH, START_STRENGTH, VERTICAL_SPEED, HORIZONTAL_SPEED);
    }
}
