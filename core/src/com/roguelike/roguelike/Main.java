package com.roguelike.roguelike;

import com.badlogic.gdx.Game;
import com.roguelike.roguelike.view.Assets;
import com.roguelike.roguelike.view.ScreenEnum;

public class Main extends Game {
    public static ScreenManager screenManager;

    @Override
    public void create() {
        Assets assets = new Assets();
        assets.loadAll();
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
    }

}