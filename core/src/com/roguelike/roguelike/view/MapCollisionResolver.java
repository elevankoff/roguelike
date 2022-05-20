package com.roguelike.roguelike.view;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.roguelike.roguelike.model.map.MapManager;

public class MapCollisionResolver {
    private final MapManager mapManager;

    public MapCollisionResolver(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public boolean isCollisionWithMapLayer(Rectangle objectRectangle) {
        MapLayer mapCollisionLayer = mapManager.getCollisionLayer();
        if (mapCollisionLayer == null) {
            return false;
        }

        for (RectangleMapObject object : mapCollisionLayer.getObjects().getByType(RectangleMapObject.class)) {
            if (object.getRectangle().overlaps(objectRectangle)) {
                return true;
            }
        }
        return false;
    }
}
