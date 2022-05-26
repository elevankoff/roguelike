package com.roguelike.roguelike.model.bonus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.roguelike.roguelike.GdxTestRunner;
import com.roguelike.roguelike.model.AliveObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BonusKitTest {
    private static final String DEFAULT_TEXTURE_PATH = "../assets/hero.png";
    private static final float X = 5;
    private static final float Y = 11;
    private static final float WIDTH = 15;
    private static final float HEIGHT = 20;
    private static final int HEALTH_BONUS = 10;

    @Test
    public void testDefault() {
        new GdxTestRunner();

        Texture texture = new Texture(DEFAULT_TEXTURE_PATH);
        BonusKit.Builder builder = BonusKit.newBuilder().setHealthBonus(HEALTH_BONUS);
        BonusKitObject object = new BonusKitObject(texture, X, Y, WIDTH, HEIGHT, builder.build());
        Assertions.assertTrue(object.isNotUsed());
        AliveObject aliveObject1 = new AliveObject(texture, X + 100, Y + 100, 0, 0, 0, 0, 0f, 0f);
        Assertions.assertFalse(object.tryToUse(aliveObject1));
        Assertions.assertTrue(object.isNotUsed());
        AliveObject aliveObject2 = new AliveObject(texture, X, Y, 0, 0, 0, 0, 0f, 0f);
        Assertions.assertTrue(object.tryToUse(aliveObject2));
        Assertions.assertFalse(object.isNotUsed());
    }

    @Test
    public void testFactory() {
        new GdxTestRunner();

        Texture texture = new Texture(DEFAULT_TEXTURE_PATH);
        BonusKitObject object = BonusKitObjectFactory.createFirstAidKit(texture, new Vector2(2, 1));
        Assertions.assertEquals(object.getX(), 2);
        Assertions.assertEquals(object.getY(), 1);
        Assertions.assertTrue(object.isNotUsed());
        Assertions.assertEquals(object.getSprite().getWidth(), BonusKitObjectFactory.FIRST_AID_KIT_WIDTH);
        Assertions.assertEquals(object.getSprite().getHeight(), BonusKitObjectFactory.FIRST_AID_KIT_HEIGHT);
    }
}
