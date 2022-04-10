package com.roguelike.roguelike.model.map;

import com.badlogic.gdx.math.Vector2;

public class MapConfig {
    private final MapName mapName;
    private final String loadPath;
    private final Vector2 playerStartPosition;

    public MapConfig(MapName mapName, String loadPath, Vector2 playerStartPosition) {
        this.mapName = mapName;
        this.loadPath = loadPath;
        this.playerStartPosition = playerStartPosition;
    }

    public MapName getMapName() {
        return mapName;
    }

    public String getLoadPath() {
        return loadPath;
    }

    public Vector2 getPlayerStartPosition() {
        return playerStartPosition;
    }

    public static final MapConfig START = new MapConfig(
            MapName.START,
            "/Users/elevankoff/Desktop/IdeaProjects/roguelike/map.tmx",
            Vector2.Zero
    );
}
