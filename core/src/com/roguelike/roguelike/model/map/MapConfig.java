package com.roguelike.roguelike.model.map;

import com.badlogic.gdx.math.Vector2;

public class MapConfig {
    private final MapName mapName;
    private final String loadPath;
    private final Vector2 playerStartPosition;
    private final Vector2 bossStartPosition;

    public MapConfig(MapName mapName, String loadPath, Vector2 playerStartPosition, Vector2 bossStartPosition) {
        this.mapName = mapName;
        this.loadPath = loadPath;
        this.playerStartPosition = playerStartPosition;
        this.bossStartPosition = bossStartPosition;
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

    public Vector2 getBossStartPosition() {
        return bossStartPosition;
    }

    public static final MapConfig START = new MapConfig(
            MapName.START,
            "map.tmx",
            new Vector2(1, 140),
            new Vector2(640, 380)
    );
}
