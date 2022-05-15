package com.roguelike.roguelike.view;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    private AssetManager assetManager = new AssetManager();
    public static final AssetDescriptor<Skin> SKIN = new AssetDescriptor<Skin>("comic/skin/comic-ui.json", Skin.class, new SkinLoader.SkinParameter("comic/skin/comic-ui.atlas"));

    public void loadAll(){
        assetManager.load(SKIN);
        assetManager.finishLoading();
    }

    public AssetManager getAssetManager(){
        return assetManager;
    }
}
