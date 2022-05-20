package com.roguelike.roguelike.view;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.roguelike.roguelike.control.HeroController;
import com.roguelike.roguelike.model.AliveObject;
import com.roguelike.roguelike.model.bonus.BonusKitObject;

import java.util.List;

public class Hero {
    private final HeroController controller;
    private final AliveObject object;

    public Hero(HeroController controller, AliveObject object) {
        this.controller = controller;
        this.object = object;
    }

    public AliveObject getObject() {
        return object;
    }

    public void update(float delta, MapCollisionResolver mapCollisionResolver) {
        Vector2 heroNextPosition = controller.getNextPosition(delta);
        Rectangle heroRectangle = new Rectangle(object.getSprite().getBoundingRectangle());
        heroRectangle.setPosition(heroNextPosition);
        if (!mapCollisionResolver.isCollisionWithMapLayer(heroRectangle)) {
            controller.update(delta);
            object.update();
        }
    }

    @SuppressWarnings("NewApi")
    public void tryToUseKits(List<BonusKitObject> bonusKits) {
        bonusKits.forEach(bonusKit -> bonusKit.tryToUse(object));
    }
}
