package com.roguelike.roguelike.model;

public class MobFactory extends AliveObjectFactory {
    // todo: make with config
    private static final float WIDTH = 60f;
    private static final float HEIGHT = 60f;

    private static final int START_HEALTH = 100;
    private static final int START_STRENGTH = 10;

    private static final float VERTICAL_SPEED = 1f;
    private static final float HORIZONTAL_SPEED = 1f;

    public MobFactory() {
        super(WIDTH, HEIGHT, START_HEALTH, START_STRENGTH, VERTICAL_SPEED, HORIZONTAL_SPEED);
    }
}
