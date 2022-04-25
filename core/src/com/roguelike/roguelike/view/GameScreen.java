package com.roguelike.roguelike.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.roguelike.roguelike.control.HeroController;
import com.roguelike.roguelike.model.AliveObject;
import com.roguelike.roguelike.model.GameContext;
import com.roguelike.roguelike.model.GameObjectType;
import com.roguelike.roguelike.model.HeroFactory;
import com.roguelike.roguelike.model.MobFactory;
import com.roguelike.roguelike.model.map.MapConfig;
import com.roguelike.roguelike.model.map.MapManager;

import java.util.Map;

public class GameScreen implements Screen {
    private final Map<GameObjectType, Texture> textures;
    private final HealthSpriteFactory healthSpriteFactory;
    private OrthographicCamera camera;
    private AliveObject hero;
    private AliveObject mob;
    private HeroController heroController;
    private MapManager mapManager;
    private OrthogonalTiledMapRenderer mapRenderer;

    private final int GAME_WORLD_HEIGHT = 20 * 32;
    private final int GAME_WORLD_WIDTH = 30 * 32;

    public GameScreen() {
        textures = TexturesFactory.create();
        healthSpriteFactory = new HealthSpriteFactory(textures.get(GameObjectType.HEALTH_LINE));
    }

    //Вызывается когда в игре мы переключаемся на этот экран
    @Override
    public void show() {
        camera = new OrthographicCamera(GAME_WORLD_WIDTH / 4, GAME_WORLD_HEIGHT / 4);
        camera.translate(camera.viewportWidth, camera.viewportHeight);

        mapManager = new MapManager();
        mapRenderer = new OrthogonalTiledMapRenderer(mapManager.getCurrentMap(), 1f);
        mapRenderer.setView(camera);

        MapConfig mapConfig = mapManager.getCurrentMapConfig();
        Vector2 heroStartPosition = mapConfig.getPlayerStartPosition();
        HeroFactory heroFactory = new HeroFactory();
        hero = heroFactory.create(textures.get(GameObjectType.HERO), heroStartPosition);
        MobFactory mobFactory = new MobFactory();
        Vector2 bossStartPosition = mapConfig.getBossStartPosition();
        mob = mobFactory.create(textures.get(GameObjectType.MOB), bossStartPosition);

        GameContext gameContext = createGameContext(mob, hero);
        heroController = new HeroController(hero, gameContext);
        Gdx.input.setInputProcessor(heroController);
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
        System.out.println(camera.viewportWidth + " " + camera.viewportHeight);
        update(delta);
        renderInner();
    }

    private void update(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateHero(delta);
        mob.update();

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

    private boolean isCollisionWithMapLayer(Rectangle objectRectangle) {
        MapLayer mapCollisionLayer = mapManager.getCollisionLayer();
        if (mapCollisionLayer == null) {
            return false;
        }
        System.out.println("Rectangle: " + objectRectangle.getX() + " " + objectRectangle.getY());

        for (RectangleMapObject object : mapCollisionLayer.getObjects().getByType(RectangleMapObject.class)) {
            System.out.println("Object: " + object.getRectangle().getY() + " " + object.getRectangle().getX());
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
