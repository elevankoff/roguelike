package com.roguelike.roguelike.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.roguelike.roguelike.GdxTestRunner;
import com.roguelike.roguelike.model.bonus.BonusKit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
 * new GdxTestRunner(); is needed in the beginning of every test for setting libgdx configuration
 */
public class AliveObjectTest {
    private static final String DEFAULT_TEXTURE_PATH = "../assets/hero.png";
    private static final float OBJECT_X = 5;
    private static final float OBJECT_Y = 10;
    private static final float WIDTH = 100;
    private static final float HEIGHT = 200;
    private static final int MAX_HEALTH = 40;
    private static final int STRENGTH = 4;
    private static final float VERTICAL_SPEED = 10;
    private static final float HORIZONTAL_SPEED = 9;

    private static final float DELTA = 0.0001f;

    @Test
    public void testDefault() {
        new GdxTestRunner();

        Texture texture = new Texture(DEFAULT_TEXTURE_PATH);
        AliveObject aliveObject = new AliveObject(
                texture,
                OBJECT_X, OBJECT_Y,
                WIDTH, HEIGHT,
                MAX_HEALTH, STRENGTH,
                VERTICAL_SPEED, HORIZONTAL_SPEED);

        Assertions.assertFalse(aliveObject.isDead());
        Assertions.assertEquals(OBJECT_X, aliveObject.getX(), DELTA);
        Assertions.assertEquals(OBJECT_Y, aliveObject.getY(), DELTA);
        Assertions.assertEquals(MAX_HEALTH, aliveObject.getHealth());
        Assertions.assertEquals(1f, aliveObject.getHealthPercent(), DELTA);
        Assertions.assertEquals(STRENGTH, aliveObject.getStrength());
        Assertions.assertEquals(VERTICAL_SPEED, aliveObject.getVerticalSpeed(), DELTA);
        Assertions.assertEquals(HORIZONTAL_SPEED, aliveObject.getHorizontalSpeed(), DELTA);
    }

    @Test
    public void testPositions() {
        new GdxTestRunner();

        Texture texture = new Texture(DEFAULT_TEXTURE_PATH);
        AliveObject aliveObject = new AliveObject(
                texture,
                OBJECT_X, OBJECT_Y,
                WIDTH, HEIGHT,
                MAX_HEALTH, STRENGTH,
                VERTICAL_SPEED, HORIZONTAL_SPEED);

        aliveObject.setPosition(5, 7);
        aliveObject.update();
        Assertions.assertEquals(aliveObject.getX(), 5);
        Assertions.assertEquals(aliveObject.getY(), 7);
        Assertions.assertEquals(aliveObject.getPosition(), new Vector2(5, 7));
        aliveObject.setPosition(new Vector2(20, 30));
        aliveObject.update();
        Assertions.assertEquals(aliveObject.getX(), 20);
        Assertions.assertEquals(aliveObject.getY(), 30);
        Assertions.assertEquals(aliveObject.getPosition(), new Vector2(20, 30));
    }

    @Test
    public void testHitAndBonusKit() {
        new GdxTestRunner();

        Texture texture = new Texture(DEFAULT_TEXTURE_PATH);
        AliveObject aliveObject = new AliveObject(
                texture,
                OBJECT_X, OBJECT_Y,
                WIDTH, HEIGHT,
                MAX_HEALTH, STRENGTH,
                VERTICAL_SPEED, HORIZONTAL_SPEED);

        aliveObject.hit(5);
        Assertions.assertEquals(MAX_HEALTH - 5, aliveObject.getHealth());
        Assertions.assertEquals((float)(MAX_HEALTH - 5)/MAX_HEALTH, aliveObject.getHealthPercent(), DELTA);
        BonusKit bonusKit = BonusKit.newBuilder().setHealthBonus(5).build();
        aliveObject.useBonusKit(bonusKit);
        Assertions.assertEquals(MAX_HEALTH, aliveObject.getHealth());
        Assertions.assertEquals(1f, aliveObject.getHealthPercent(), DELTA);
        aliveObject.useBonusKit(bonusKit);
        Assertions.assertEquals(MAX_HEALTH, aliveObject.getHealth());
        Assertions.assertEquals(1f, aliveObject.getHealthPercent(), DELTA);
        aliveObject.hit(MAX_HEALTH * 2);
        Assertions.assertEquals(0, aliveObject.getHealth());
        Assertions.assertEquals(0f, aliveObject.getHealthPercent(), DELTA);
        Assertions.assertTrue(aliveObject.isDead());
        aliveObject.useBonusKit(bonusKit);
        Assertions.assertEquals(0, aliveObject.getHealth());
        Assertions.assertEquals(0f, aliveObject.getHealthPercent(), DELTA);
        Assertions.assertTrue(aliveObject.isDead());
    }

    @Test
    public void testFactory() {
        new GdxTestRunner();

        MobFactory factory = new MobFactory();
        Texture texture = new Texture(DEFAULT_TEXTURE_PATH);
        AliveObject aliveObject = factory.create(texture, new Vector2(13, 37));
        Assertions.assertEquals(aliveObject.getHealth(), MobFactory.START_HEALTH);
        Assertions.assertEquals(aliveObject.maxHealth, MobFactory.START_HEALTH);
        Assertions.assertEquals(aliveObject.getStrength(), MobFactory.START_STRENGTH);
        Assertions.assertEquals(aliveObject.getVerticalSpeed(), MobFactory.VERTICAL_SPEED);
        Assertions.assertEquals(aliveObject.getHorizontalSpeed(), MobFactory.HORIZONTAL_SPEED);
        Assertions.assertEquals(aliveObject.getX(), 13);
        Assertions.assertEquals(aliveObject.getY(), 37);
    }
}
