package com.roguelike.roguelike.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.roguelike.roguelike.ScreenManager;
import com.roguelike.roguelike.control.HeroController;
import com.roguelike.roguelike.control.MobController;
import com.roguelike.roguelike.model.AliveObject;
import com.roguelike.roguelike.model.GameContext;
import com.roguelike.roguelike.model.GameObjectType;
import com.roguelike.roguelike.model.HeroFactory;
import com.roguelike.roguelike.model.MobFactory;
import com.roguelike.roguelike.model.map.MapConfig;
import com.roguelike.roguelike.model.map.MapManager;

import java.util.Map;

public class GameScreen extends AbstractScreen{
    private final Map<GameObjectType, Texture> textures;
    private final HealthSpriteFactory healthSpriteFactory;
    private OrthographicCamera camera;
    private AliveObject hero;
    private AliveObject mob;
    private HeroController heroController;
    private MobController mobController;
    private MapManager mapManager;
    private OrthogonalTiledMapRenderer mapRenderer;
    private AssetManager assetManager;
    private Skin skin;

    private Stage stage;

    private Table mainTable;

    private final int GAME_WORLD_HEIGHT = 20 * 32;
    private final int GAME_WORLD_WIDTH = 30 * 32;

    public GameScreen() {
        Assets assets = new Assets();
        assets.loadAll();
        assetManager = assets.getAssetManager();
        skin = assetManager.get(Assets.SKIN);
        textures = TexturesFactory.create();
        healthSpriteFactory = new HealthSpriteFactory(textures.get(GameObjectType.HEALTH_LINE));

        mainTable = new Table();
        stage = new Stage();
        stage.addActor(mainTable);
    }

    //Вызывается когда в игре мы переключаемся на этот экран
    @Override
    public void show() {
        camera = new OrthographicCamera(GAME_WORLD_WIDTH / 4, GAME_WORLD_HEIGHT / 4);
        camera.translate(camera.viewportWidth, camera.viewportHeight);

        mapManager = new MapManager();
        mapRenderer = new OrthogonalTiledMapRenderer(mapManager.getCurrentMap(), 1f);
        mapRenderer.setView(camera);

        mainTable.setPosition(camera.viewportWidth * 2.5f, camera.viewportHeight * 2.8f);
        addButton("x").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
            }
        });
        MapConfig mapConfig = mapManager.getCurrentMapConfig();
        Vector2 heroStartPosition = mapConfig.getPlayerStartPosition();
        HeroFactory heroFactory = new HeroFactory();
        hero = heroFactory.create(textures.get(GameObjectType.HERO), heroStartPosition);
        MobFactory mobFactory = new MobFactory();
        Vector2 bossStartPosition = mapConfig.getBossStartPosition();
        mob = mobFactory.create(textures.get(GameObjectType.MOB), bossStartPosition);

        GameContext gameContext = createGameContext(mob, hero);
        heroController = new HeroController(hero, gameContext);
        mobController = new MobController(mob, hero);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(heroController);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private GameContext createGameContext(AliveObject mob, AliveObject hero) {
        GameContext gameContext = new GameContext(hero);
        gameContext.addMob(mob);
        return gameContext;
    }

    //Итеративный метод, вызывается итеративно с промежутком в delta секунд
    //private float positionY = 0f;
    @Override
    public void render(float delta) {
        update(delta);
        renderInner();
        stage.act(delta);
        stage.draw();
    }

    private TextButton addButton(String name) {
        TextButton button = new TextButton(name, skin);
        mainTable.add(button).width(60).height(60);
        mainTable.row();
        return button;
    }

    private void update(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateHero(delta);
        updateMob();

        camera.position.set(hero.getX() + hero.getSprite().getWidth() / 2, hero.getY() + hero.getSprite().getHeight() / 2, 100);
        camera.update();

        mapRenderer.setView(camera);
    }

    private void updateHero(float delta) {
        Vector2 heroNextPosition = heroController.getNextPosition(delta);
        Rectangle heroRectangle = new Rectangle(hero.getSprite().getBoundingRectangle());
        heroRectangle.setPosition(heroNextPosition);
        if (!isCollisionWithMapLayer(heroRectangle)) {
            heroController.update(delta);
            hero.update();
        }
    }

    private void updateMob() {
        Vector2 mobNextPosition = mobController.getNextPosition();
        Rectangle mobRectangle = new Rectangle(mob.getSprite().getBoundingRectangle());
        mobRectangle.setPosition(mobNextPosition);
        if (!isCollisionWithMapLayer(mobRectangle)) {
            mobController.update();
            mob.update();
        }
        mobController.process();
    }

    private boolean isCollisionWithMapLayer(Rectangle objectRectangle) {
        MapLayer mapCollisionLayer = mapManager.getCollisionLayer();
        if (mapCollisionLayer == null) {
            return false;
        }

        for (RectangleMapObject object : mapCollisionLayer.getObjects().getByType(RectangleMapObject.class)) {
            if (object.getRectangle().overlaps(objectRectangle)) {
                return true;
            }
        }
        return false;
    }

    private void renderInner() {
        mapRenderer.setView(camera);
        mapRenderer.render();

        Batch mapBatch = mapRenderer.getBatch(); // todo: make MapRendererManager
        mapBatch.begin();
        drawAliveObject(mapBatch, hero);
        drawAliveObject(mapBatch, mob);
        mapBatch.end();
    }

    private void drawAliveObject(Batch mapBatch, AliveObject aliveObject) {
        Sprite sprite = aliveObject.getSprite();
        mapBatch.draw(sprite.getTexture(), aliveObject.getX(), aliveObject.getY(), sprite.getWidth(), sprite.getHeight());
        Sprite healthSprite = healthSpriteFactory.create(aliveObject);
        System.out.println(healthSprite.getX() + " " + healthSprite.getY() + " " + healthSprite.getWidth() + " " + healthSprite.getHeight());
        mapBatch.draw(healthSprite.getTexture(), healthSprite.getX(), healthSprite.getY(), healthSprite.getWidth(), healthSprite.getHeight());
    }

    //Вызывается при изменении размеров экрана
    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) height / width;
        camera = new OrthographicCamera(GAME_WORLD_WIDTH / 4, GAME_WORLD_WIDTH * aspectRatio / 4);
        camera.translate(camera.viewportWidth, camera.viewportHeight);
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
        Gdx.input.setInputProcessor(null);
        mapRenderer.dispose();
    }
}
