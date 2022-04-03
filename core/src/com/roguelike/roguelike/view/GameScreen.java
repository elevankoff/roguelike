package com.roguelike.roguelike.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.roguelike.roguelike.model.Hero;
import com.roguelike.roguelike.model.Mob;
import java.util.Random;

public class GameScreen implements Screen {

    private Texture texture;
    private Texture mobTexture;
    private SpriteBatch batch;
    private Hero hero;
    private Mob mob;
    private OrthographicCamera camera;

    //framerate
    public static float deltaCff;

    //Вызывается когда в игре мы переключаемся на этот экран
    @Override
    public void show() {
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("hero.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        mobTexture = new Texture(Gdx.files.internal("monster.png"));
        final Random random = new Random();
        hero = new Hero(texture, 0, 0, 2f, 2f);
        mob = new Mob(mobTexture, random.nextFloat(), random.nextFloat(), 2f, 2f, hero.bounds);
    }

    //Итеративный метод, вызывается итеративно с промежутком в delta секунд
    //private float positionY = 0f;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        deltaCff = delta;

        //positionY++;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        hero.draw(batch);
        mob.draw(batch);
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
    @Override
    public void dispose() {
        texture.dispose();
        batch.dispose();
    }
}
