package com.roguelike.roguelike.model;

import java.util.ArrayList;
import java.util.List;

public class GameContext {
    private final List<AliveObject> mobs;
    private final AliveObject hero;

    public GameContext(List<AliveObject> mobs, AliveObject hero) {
        this.mobs = mobs;
        this.hero = hero;
    }

    public GameContext(AliveObject hero) {
        this.mobs = new ArrayList<>();
        this.hero = hero;
    }

    public void addMob(AliveObject mob) {
        mobs.add(mob);
    }

    public List<AliveObject> getMobs() {
        return mobs;
    }
}
