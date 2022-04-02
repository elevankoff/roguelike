package com.roguelike.roguelike.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.roguelike.roguelike.model.Hero;

public class GameScreen implements Screen {

    private Texture texture;
    private SpriteBatch batch;
    private Hero hero;

    //Вызывается когда в игре мы переключаемся на этот экран
    @Override
    public void show() {
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("hero.png"));
        hero = new Hero(texture, 0, 0, 80, 80);
    }

    //Итеративный метод, вызывается итеративно с промежутком в delta секунд
    //private float positionY = 0f;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //positionY++;
        batch.begin();
        hero.draw(batch);
        batch.end();
    }

    //Вызывается при изменении размеров экрана
    @Override
    public void resize(int width, int height) {

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
