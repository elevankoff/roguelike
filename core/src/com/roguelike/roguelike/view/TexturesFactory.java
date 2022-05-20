package com.roguelike.roguelike.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.roguelike.roguelike.model.GameObjectType;

import java.util.HashMap;
import java.util.Map;

public class TexturesFactory {
    private static final String HERO_TEXTURE_PATH = "hero.png";
    private static final String MOB_TEXTURE_PATH = "monster.png"; // todo: make separate texture
    private static final String HEALTH_TEXTURE_PATH = "health.jpeg";
    private static final String FIRST_AID_KIT_TEXTURE_PATH = "first-aid-kit.png";

    public static Map<GameObjectType, Texture> create() {
        Map<GameObjectType, Texture> textures = new HashMap<>();
        textures.put(GameObjectType.HERO, getHeroTexture());
        textures.put(GameObjectType.MOB, getMobTexture());
        textures.put(GameObjectType.HEALTH_LINE, getHealthTexture());
        textures.put(GameObjectType.FIRST_AID_KIT, getFirstAidKitTexture());
        return textures;
    }

    private static Texture getHeroTexture() {
        Texture heroTexture = new Texture(Gdx.files.internal(HERO_TEXTURE_PATH));
        heroTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return heroTexture;
    }

    private static Texture getMobTexture() {
        Texture mobTexture = new Texture(Gdx.files.internal(MOB_TEXTURE_PATH));
        mobTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return mobTexture;
    }

    private static Texture getHealthTexture() {
        Texture mobTexture = new Texture(Gdx.files.internal(HEALTH_TEXTURE_PATH));
        mobTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return mobTexture;
    }

    private static Texture getFirstAidKitTexture() {
        Texture mobTexture = new Texture(Gdx.files.internal(FIRST_AID_KIT_TEXTURE_PATH));
        mobTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return mobTexture;
    }
}
