package com.roguelike.roguelike.view;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.roguelike.roguelike.control.MobFollowingController;
import com.roguelike.roguelike.model.AliveObject;
import com.roguelike.roguelike.model.bonus.BonusKitObject;

import java.util.List;

public class Mob {
    private final MobFollowingController controller;
    private final AliveObject object;

    public Mob(MobFollowingController controller, AliveObject object) {
        this.controller = controller;
        this.object = object;
    }

    public MobFollowingController getController() {
        return controller;
    }

    public AliveObject getObject() {
        return object;
    }

    public void update(MapCollisionResolver mapCollisionResolver) {
        Vector2 mobNextPosition = controller.getNextPosition();
        Rectangle mobRectangle = new Rectangle(object.getSprite().getBoundingRectangle());
        mobRectangle.setPosition(mobNextPosition);
        if (!mapCollisionResolver.isCollisionWithMapLayer(mobRectangle)) {
            controller.move(mobNextPosition);
            object.update();
        }
        controller.process();
    }

    @SuppressWarnings("NewApi")
    public void tryToUseKits(List<BonusKitObject> bonusKits) {
        bonusKits.forEach(bonusKit -> bonusKit.tryToUse(object));
    }
}
