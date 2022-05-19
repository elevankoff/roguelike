package com.roguelike.roguelike.model.bonus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class BonusKitObjectFactory {
    private static final int FIRST_AID_KIT_WIDTH = 10;
    private static final int FIRST_AID_KIT_HEIGHT = 10;

    public static BonusKitObject createFirstAidKit(Texture texture, Vector2 position) {
        BonusKit bonusKit = BonusKit.newBuilder()
                .setHealthBonus(100)
                .build();
        return new BonusKitObject(
                texture,
                position.x,
                position.y,
                FIRST_AID_KIT_WIDTH,
                FIRST_AID_KIT_HEIGHT,
                bonusKit
        );
    }
}
