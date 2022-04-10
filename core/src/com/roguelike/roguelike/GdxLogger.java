package com.roguelike.roguelike;

import com.badlogic.gdx.Gdx;

public class GdxLogger {
    private final String className;

    public <T> GdxLogger(Class<T> clazz) {
        this(clazz.getName());
    }

    public GdxLogger(String className) {
        this.className = className;
    }

    public void log(String message) {
        Gdx.app.log(className, message);
    }

    public void log(String message, Throwable exception) {
        Gdx.app.log(className, message, exception);
    }

    public void error(String message) {
        Gdx.app.error(className, message);
    }

    public void error(String message, Throwable exception) {
        Gdx.app.error(className, message, exception);
    }

    public void debug(String message) {
        Gdx.app.debug(className, message);
    }

    public void debug(String message, Throwable exception) {
        Gdx.app.debug(className, message, exception);
    }
}
