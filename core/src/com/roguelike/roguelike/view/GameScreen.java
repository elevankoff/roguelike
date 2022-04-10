package com.roguelike.roguelike.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.roguelike.roguelike.control.HeroController;
import com.roguelike.roguelike.model.GameObjectType;
import com.roguelike.roguelike.model.Hero;

import java.util.Map;

public class GameScreen implements Screen {
    private final Map<GameObjectType, Texture> textures;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Hero hero;
    private HeroController heroController;

    public GameScreen() {
        textures = TexturesFactory.create();
    }

    //Вызывается когда в игре мы переключаемся на этот экран
    @Override
    public void show() {
        batch = new SpriteBatch();
        hero = HeroFactory.create(textures.get(GameObjectType.HERO));
        heroController = new HeroController(hero);
        Gdx.input.setInputProcessor(heroController);
    }

    //Итеративный метод, вызывается итеративно с промежутком в delta секунд
    //private float positionY = 0f;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        hero.update();
        heroController.update(delta);

        //positionY++;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        hero.draw(batch);

        batch.end();
    }

    //Вызывается при изменении размеров экрана
    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) height / width;
        camera = new OrthographicCamera(20f, 20f * aspectRatio);
    }

    //Вызывается когда сворачиваем окошко с игрой
    @Override
    public void pause() {

    }

    //Вызывается если разворачиваем окошко с игрой
    @Override
    public void resume() {

    }

    //Вызывается при переключении на другой экран в игре
    @Override
    public void hide() {

    }

    //Вызывается когда закрываем игру (уничтожение всех ресурсов)
    @SuppressWarnings("NewApi")
    @Override
    public void dispose() {
        textures.forEach((type, texture) -> texture.dispose());
        batch.dispose();
    }
}
