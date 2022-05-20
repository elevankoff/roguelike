package com.roguelike.roguelike.model;

public class HeroFactory extends AliveObjectFactory {
    // todo: make with config
    private static final float WIDTH = 20f;
    private static final float HEIGHT = 20f;

    private static final int START_HEALTH = 100;
    private static final int START_STRENGTH = 20;

    private static final float VERTICAL_SPEED = 180f;
    private static final float HORIZONTAL_SPEED = 180f;

    public HeroFactory() {
        super(WIDTH, HEIGHT, START_HEALTH, START_STRENGTH, VERTICAL_SPEED, HORIZONTAL_SPEED);
    }
}
