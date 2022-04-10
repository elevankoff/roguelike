package com.roguelike.roguelike.model.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.roguelike.roguelike.GdxLogger;
import com.roguelike.roguelike.Utility;

public class MapManager {
    private static final GdxLogger logger = new GdxLogger(MapManager.class);

    public static final float UNIT_SCALE = 1/16f;

    private TiledMap currentMap;
    private MapConfig currentMapConfig;

    public TiledMap getCurrentMap() {
        if (currentMap == null) {
            currentMapConfig = MapConfig.START;
            loadNewCurrentMap(currentMapConfig);
        }
        return currentMap;
    }

    private void loadNewCurrentMap(MapConfig mapConfig) {
        if (currentMap != null) {
            logger.log("Dispose old map");
            currentMap.dispose();
        }
        Utility.loadMapAsset(mapConfig);
        currentMap = Utility.getMapAsset(mapConfig);
    }
}
