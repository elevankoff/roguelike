package com.roguelike.roguelike;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.roguelike.roguelike.model.map.MapConfig;

public class Utility {
    private static final GdxLogger logger = new GdxLogger(Utility.class);

    private static final AssetManager assetManager = new AssetManager();
    private static final LocalFileHandleResolver filePathResolver = new LocalFileHandleResolver();

    static {
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(filePathResolver));
    }

    public static void loadMapAsset(MapConfig mapConfig) {
        logger.log("Loading map asset " + mapConfig.getMapName().name());
        String loadPath = mapConfig.getLoadPath();
        if (loadPath == null) {
            throw new IllegalArgumentException("Load path should be non null");
        }

        if (filePathResolver.resolve(loadPath).exists()) {
            assetManager.load(loadPath, TiledMap.class);
            assetManager.finishLoadingAsset(loadPath);
            logger.log("Map " + mapConfig.getMapName().name() + " loaded!");
        } else {
            throw new IllegalArgumentException("There's no path " + loadPath + "!");
        }
    }

    public static TiledMap getMapAsset(MapConfig mapConfig) {
        String loadPath = mapConfig.getLoadPath();
        if (isAssetLoaded(loadPath)) {
            return assetManager.get(loadPath, TiledMap.class);
        } else {
            throw new IllegalArgumentException("Map " + mapConfig.getMapName().name() + " is not loaded!");
        }
    }

    public static boolean isAssetLoaded(String loadPath) {
        return assetManager.isLoaded(loadPath);
    }
}
