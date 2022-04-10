package com.roguelike.roguelike.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.roguelike.roguelike.model.GameObjectType;

import java.util.HashMap;
import java.util.Map;

public class TexturesFactory {
    private static final String HERO_TEXTURE_PATH = "hero.png";

    public static Map<GameObjectType, Texture> create() {
        Map<GameObjectType, Texture> textures = new HashMap<>();
        textures.put(GameObjectType.HERO, getHeroTexture());
        return textures;
    }

    private static Texture getHeroTexture() {
        Texture heroTexture = new Texture(Gdx.files.internal(HERO_TEXTURE_PATH));
        heroTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return heroTexture;
    }
}
