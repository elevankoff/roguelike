package com.roguelike.roguelike.model.bonus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.roguelike.roguelike.model.AliveObject;
import com.roguelike.roguelike.model.GameObject;

public class BonusKitObject extends GameObject {
    private static final int AVAILABLE_RADIUS = 8;

    private final BonusKit bonusKit;
    private boolean isUsed;

    public BonusKitObject(
            Texture texture, float x, float y, float width, float height,
            BonusKit bonusKit)
    {
        super(texture, x, y, width, height);
        this.bonusKit = bonusKit;
        this.isUsed = false;
    }

    public boolean isNotUsed() {
        return !isUsed;
    }

    public boolean tryToUse(AliveObject aliveObject) {
        Circle usageCircle = new Circle(super.getPosition(), AVAILABLE_RADIUS);
        if (Intersector.overlaps(usageCircle, aliveObject.getSprite().getBoundingRectangle())) {
            aliveObject.useBonusKit(use());
            return true;
        }
        return false;
    }

    private BonusKit use() {
        isUsed = true;
        return bonusKit;
    }
}
