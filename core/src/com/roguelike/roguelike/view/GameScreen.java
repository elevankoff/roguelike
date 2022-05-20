package com.roguelike.roguelike.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.roguelike.roguelike.ScreenManager;
import com.roguelike.roguelike.control.HeroController;
import com.roguelike.roguelike.control.MobFollowingController;
import com.roguelike.roguelike.model.AliveObject;
import com.roguelike.roguelike.model.GameContext;
import com.roguelike.roguelike.model.GameObject;
import com.roguelike.roguelike.model.GameObjectType;
import com.roguelike.roguelike.model.HeroFactory;
import com.roguelike.roguelike.model.MobFactory;
import com.roguelike.roguelike.model.bonus.BonusKitObject;
import com.roguelike.roguelike.model.bonus.BonusKitSpawner;
import com.roguelike.roguelike.model.map.MapConfig;
import com.roguelike.roguelike.model.map.MapManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameScreen extends AbstractScreen{
    private final Map<GameObjectType, Texture> textures;
    private final HealthSpriteFactory healthSpriteFactory;
    private final MobFactory mobFactory;
    private List<BonusKitObject> bonusKits;
    private BonusKitSpawner bonusKitSpawner;
    private MapCollisionResolver mapCollisionResolver;
    private OrthographicCamera camera;
    private Hero hero;
    private final List<Mob> mobs;
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
        bonusKits = new ArrayList<>();
        mobs = new ArrayList<>();
        mobFactory = new MobFactory();
    }

    //Вызывается когда в игре мы переключаемся на этот экран
    @Override
    public void show() {
        camera = new OrthographicCamera(GAME_WORLD_WIDTH / 4, GAME_WORLD_HEIGHT / 4);
        camera.translate(camera.viewportWidth, camera.viewportHeight);

        mapManager = new MapManager();
        mapCollisionResolver = new MapCollisionResolver(mapManager);
        bonusKitSpawner = new BonusKitSpawner(
                textures.get(GameObjectType.FIRST_AID_KIT),
                mapCollisionResolver,
                100,
                GAME_WORLD_WIDTH,
                GAME_WORLD_HEIGHT);
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
        Vector2 bossStartPosition = mapConfig.getBossStartPosition();

        AliveObject heroObject = heroFactory.create(textures.get(GameObjectType.HERO), heroStartPosition);
        AliveObject mobObject = mobFactory.create(textures.get(GameObjectType.MOB), bossStartPosition);
        MobFollowingController mobFollowingController = new MobFollowingController(mobObject, heroObject);
        Mob mob = new Mob(mobFollowingController, mobObject);
        mobs.add(mob);

        GameContext gameContext = new GameContext(mobs);
        HeroController heroController = new HeroController(heroObject, gameContext);
        hero = new Hero(heroController, heroObject);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(heroController);
        Gdx.input.setInputProcessor(multiplexer);
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

        updateBonusKits();
        updateHero(delta);
        updateMobs();

        AliveObject heroObject = hero.getObject();
        camera.position.set(
                heroObject.getX() + heroObject.getSprite().getWidth() / 2,
                heroObject.getY() + heroObject.getSprite().getHeight() / 2,
                100
        );
        camera.update();

        mapRenderer.setView(camera);
    }

    @SuppressWarnings("NewApi")
    private void updateBonusKits() {
        bonusKits = bonusKits.stream().filter(BonusKitObject::isNotUsed).collect(Collectors.toList());
        Optional<BonusKitObject> bonusKitObjectO = bonusKitSpawner.spawn();
        bonusKitObjectO.ifPresent(bonusKits::add);
    }

    @SuppressWarnings("NewApi")
    private void updateHero(float delta) {
        hero.tryToUseKits(bonusKits);
        hero.update(delta, mapCollisionResolver);
    }

    @SuppressWarnings("NewApi")
    private void updateMobs() {
        long aliveMobs = mobs.stream().filter(mob -> !mob.getObject().isDead()).count();
        if (aliveMobs == 0) {
            MapConfig mapConfig = mapManager.getCurrentMapConfig();
            Vector2 bossStartPosition = mapConfig.getBossStartPosition();
            Vector2 heroStartPosition = mapConfig.getPlayerStartPosition();
            int prevMobsCount = mobs.size();
            mobs.clear();
            for (int i = 0; i < prevMobsCount + 1; i++) {
                if (i%2 == 1) {
                    mobs.add(createMob(hero, heroStartPosition));
                } else {
                    mobs.add(createMob(hero, bossStartPosition));
                }
            }
        }
        mobs.forEach(mob -> {
            mob.tryToUseKits(bonusKits);
            mob.update(mapCollisionResolver);
        });
    }

    private Mob createMob(Hero hero, Vector2 startPosition) {
        AliveObject mobObject = mobFactory.create(textures.get(GameObjectType.MOB), startPosition);
        MobFollowingController mobFollowingController = new MobFollowingController(mobObject, hero.getObject());
        return new Mob(mobFollowingController, mobObject);
    }

    @SuppressWarnings("NewApi")
    private void renderInner() {
        mapRenderer.setView(camera);
        mapRenderer.render();

        Batch mapBatch = mapRenderer.getBatch(); // todo: make MapRendererManager
        mapBatch.begin();
        drawAliveObject(mapBatch, hero.getObject());
        mobs.forEach(mob -> drawMob(mapBatch, mob));
        drawBonusKits(mapBatch);
        mapBatch.end();
    }

    @SuppressWarnings("NewApi")
    private void drawBonusKits(Batch mapBatch) {
        bonusKits.forEach(kit -> drawGameObject(mapBatch, kit));
    }

    private void drawMob(Batch mapBatch, Mob mob) {
        AliveObject object = mob.getObject();
        if (object.isDead()) {
            return;
        }
        Circle attackCircle = mob.getController().getAttackCircle();
        drawCircle(mapBatch, attackCircle);
        drawAliveObject(mapBatch, object);
    }

    private void drawCircle(Batch mapBatch, Circle circle) {
        for (float x = -circle.radius; x <= circle.radius; x += 0.1) {
            float y = (float) Math.sqrt(circle.radius*circle.radius - x*x);
            System.out.println(x + " " + y);
            mapBatch.draw(textures.get(GameObjectType.FIRST_AID_KIT), circle.x + x, circle.y + y, 2, 2);
            mapBatch.draw(textures.get(GameObjectType.FIRST_AID_KIT), circle.x + x, circle.y - y, 2, 2);
        }
    }

    private void drawAliveObject(Batch mapBatch, AliveObject aliveObject) {
        if (aliveObject.isDead()) {
            return;
        }
        drawGameObject(mapBatch, aliveObject);
        Sprite sprite = aliveObject.getSprite();
        mapBatch.draw(sprite.getTexture(), aliveObject.getX(), aliveObject.getY(), sprite.getWidth(), sprite.getHeight());
        Sprite healthSprite = healthSpriteFactory.create(aliveObject);
        mapBatch.draw(healthSprite.getTexture(), healthSprite.getX(), healthSprite.getY(), healthSprite.getWidth(), healthSprite.getHeight());
    }

    private void drawGameObject(Batch mapBatch, GameObject gameObject) {
        Sprite sprite = gameObject.getSprite();
        mapBatch.draw(sprite.getTexture(), gameObject.getX(), gameObject.getY(), sprite.getWidth(), sprite.getHeight());
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
