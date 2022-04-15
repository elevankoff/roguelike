package com.roguelike.roguelike.model.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.roguelike.roguelike.GdxLogger;
import com.roguelike.roguelike.Utility;

public class MapManager {
    private static final GdxLogger logger = new GdxLogger(MapManager.class);

    private static final String MAP_COLLISION_LAYER = "MAP_COLLISION_LAYER";

    public static final float UNIT_SCALE = 1/16f;

    private TiledMap currentMap;
    private MapConfig currentMapConfig;

    public TiledMap getCurrentMap() {
        loadNewCurrentMap(getCurrentMapConfig());
        return currentMap;
    }

    public MapConfig getCurrentMapConfig() {
        if (currentMap == null) {
            currentMapConfig = MapConfig.START;
        }
        return currentMapConfig;
    }

    private void loadNewCurrentMap(MapConfig mapConfig) {
        if (currentMap != null) {
            logger.log("Dispose old map");
            currentMap.dispose();
        }
        Utility.loadMapAsset(mapConfig);
        currentMap = Utility.getMapAsset(mapConfig);
    }

    public MapLayer getCollisionLayer() {
        return currentMap.getLayers().get(MAP_COLLISION_LAYER);
    }
}
