package com.roguelike.roguelike.control;

import com.badlogic.gdx.math.Polygon;
import com.roguelike.roguelike.model.Hero;
import com.roguelike.roguelike.model.Mob;
import com.roguelike.roguelike.view.GameScreen;

public class MobController {

    private Polygon mobBounds;
    private Polygon heroBounds;
    public MobController(Polygon mobBounds, Polygon heroBounds) {
        this.mobBounds = mobBounds;
        this.heroBounds = heroBounds;
    }

    float verticalSpeed = Mob.verticalSpeed;
    float horizontalSpeed = Mob.horizontalSpeed;
    public void handle(){
        float curVerticalSpeed = heroBounds.getY() - mobBounds.getY();
        float curHorizontalSpeed = heroBounds.getX() - mobBounds.getX();

        mobBounds.setPosition(mobBounds.getX() + curHorizontalSpeed * GameScreen.deltaCff,
                mobBounds.getY() + curVerticalSpeed * GameScreen.deltaCff);
    }
}
