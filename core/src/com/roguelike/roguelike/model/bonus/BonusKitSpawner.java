package com.roguelike.roguelike.model.bonus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.roguelike.roguelike.view.MapCollisionResolver;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

public class BonusKitSpawner {
    private static final int MAX_RETRY_COUNT = 20;
    private static final Random random = new Random();

    private final Texture firstAidTexture;
    private final MapCollisionResolver mapCollisionResolver;
    private final int spawnPeriodMillis;
    private final int maxX;
    private final int maxY;

    private Instant lastSpawn;

    @SuppressWarnings("NewApi")
    public BonusKitSpawner(
            Texture firstAidTexture,
            MapCollisionResolver mapCollisionResolver,
            int spawnPeriodMillis,
            int maxX, int maxY)
    {
        this.firstAidTexture = firstAidTexture;
        this.mapCollisionResolver = mapCollisionResolver;
        this.spawnPeriodMillis = spawnPeriodMillis;
        this.maxX = maxX;
        this.maxY = maxY;

        this.lastSpawn = Instant.now();
    }

    @SuppressWarnings("NewApi")
    public Optional<BonusKitObject> spawn() {
        Instant now = Instant.now();
        if (lastSpawn.isAfter(now.minusMillis(spawnPeriodMillis))) {
            return Optional.empty();
        }
        BonusKitObject firstAidKit = spawnFirstAidKit();
        int retry = 0;
        while (isCollision(firstAidKit) && retry < MAX_RETRY_COUNT) {
            firstAidKit = spawnFirstAidKit();
            retry++;
        }
        if (isCollision(firstAidKit)) {
            return Optional.empty();
        }
        lastSpawn = now;
        return Optional.of(firstAidKit);
    }

    private boolean isCollision(BonusKitObject object) {
        return mapCollisionResolver.isCollisionWithMapLayer(object.getSprite().getBoundingRectangle());
    }

    private BonusKitObject spawnFirstAidKit() {
        int spawnX = random.nextInt(maxX);
        int spawnY = random.nextInt(maxY);
        return BonusKitObjectFactory.createFirstAidKit(firstAidTexture, new Vector2(spawnX, spawnY));
    }
}
