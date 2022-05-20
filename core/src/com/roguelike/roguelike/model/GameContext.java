package com.roguelike.roguelike.model;

import com.roguelike.roguelike.view.Mob;

import java.util.List;

public class GameContext {
    private final List<Mob> mobs;

    public GameContext(List<Mob> mobs) {
        this.mobs = mobs;
    }

    public List<Mob> getMobs() {
        return mobs;
    }
}
