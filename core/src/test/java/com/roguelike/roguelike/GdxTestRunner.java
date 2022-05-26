package com.roguelike.roguelike;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import static org.mockito.Mockito.mock;

import com.badlogic.gdx.ApplicationListener;

public class GdxTestRunner implements ApplicationListener {
    public GdxTestRunner() {
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        HeadlessApplication ha = new HeadlessApplication(this, conf);
        Gdx.gl = mock(GL20.class);
    }

    @Override
    public void create() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void render() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }
}