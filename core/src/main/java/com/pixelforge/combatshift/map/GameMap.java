package com.pixelforge.combatshift.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;
import com.pixelforge.combatshift.entity.Mob;
import com.pixelforge.combatshift.entity.Player;

public class GameMap implements GameMapInterface {

    private final AssetManagerHelper assets;
    private final Array<Rectangle> obstacles = new Array<>();
    private final Array<String> obstacleTypes = new Array<>();
    private final Array<Mob> mobs = new Array<>();       // ← Мобы (кабаны)

    private com.badlogic.gdx.audio.Music music;

    // Раунды
    private int currentRound = 1;
    private final int maxRounds = 3;
    private boolean inBreak = false;
    private float breakTimer = 0f;

    public GameMap(AssetManagerHelper assets) {
        this.assets = assets;
        generateObstacles();
        this.music = assets.forestMusic;
        spawnMobs();                    // ← Спавним кабанов
    }

    private void generateObstacles() {
        obstacles.clear();
        obstacleTypes.clear();

        float minDistance = 55f;

        // Деревья
        for (int i = 0; i < 35; i++) {
            float x = MathUtils.random(100, Constants.WORLD_WIDTH - 120);
            float y = MathUtils.random(100, Constants.WORLD_HEIGHT - 120);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "tree", 20, 14, 28);
            }
        }

        // Камни
        for (int i = 0; i < 28; i++) {
            float x = MathUtils.random(80, Constants.WORLD_WIDTH - 100);
            float y = MathUtils.random(80, Constants.WORLD_HEIGHT - 100);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "rock", 20, 16, 22);
            }
        }

        // Кусты Medium
        for (int i = 0; i < 32; i++) {
            float x = MathUtils.random(70, Constants.WORLD_WIDTH - 90);
            float y = MathUtils.random(70, Constants.WORLD_HEIGHT - 90);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "bushMedium", 22, 14, 20);
            }
        }

        // Кусты Large
        for (int i = 0; i < 22; i++) {
            float x = MathUtils.random(80, Constants.WORLD_WIDTH - 100);
            float y = MathUtils.random(80, Constants.WORLD_HEIGHT - 100);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "bushLarge", 35, 16, 24);
            }
        }

        // Пни
        for (int i = 0; i < 18; i++) {
            float x = MathUtils.random(60, Constants.WORLD_WIDTH - 80);
            float y = MathUtils.random(60, Constants.WORLD_HEIGHT - 80);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "stumpShort", 16, 12, 18);
            }
        }
        for (int i = 0; i < 12; i++) {
            float x = MathUtils.random(70, Constants.WORLD_WIDTH - 90);
            float y = MathUtils.random(70, Constants.WORLD_HEIGHT - 90);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "stumpTall", 14, 12, 20);
            }
        }
    }

    private void spawnMobs() {
        mobs.clear();
        int count = 5 + currentRound * 2;
        for (int i = 0; i < count; i++) {
            float x = MathUtils.random(150, Constants.WORLD_WIDTH - 150);
            float y = MathUtils.random(150, Constants.WORLD_HEIGHT - 150);
            mobs.add(new Mob(x, y, assets));
        }
    }
    public void updateBreak(float delta, Player player) {
        if (!inBreak) return;
        breakTimer -= delta;
        if (breakTimer <= 0) {
            inBreak = false;
            spawnMobs();
            player.fullHeal();
        }
    }

    public String getLocationName() { return "FOREST"; }
    public int getCurrentRound() { return currentRound; }
    public boolean isInBreak() { return inBreak; }
    public float getBreakTimeLeft() { return breakTimer; }

    public void checkRoundEnd() {
        if (inBreak) return;
        boolean allDead = true;
        for (Mob m : mobs) if (!m.isDead()) allDead = false;

        if (allDead) {
            if (currentRound < maxRounds) {
                currentRound++;
                inBreak = true;
                breakTimer = 15f;
            }
        }
    }

    private boolean isFarEnough(float x, float y, float minDist) {
        for (Rectangle obs : obstacles) {
            float dx = obs.x - x;
            float dy = obs.y - y;
            if (dx * dx + dy * dy < minDist * minDist) {
                return false;
            }
        }
        return true;
    }

    private void addObstacle(float x, float y, String type, float collisionW, float collisionH, float offsetY) {
        obstacles.add(new Rectangle(x, y + offsetY, collisionW, collisionH));
        obstacleTypes.add(type);
    }

    // ==================== МУЗЫКА ====================
    public void playMusic() {
        if (music != null && !music.isPlaying()) music.play();
    }

    public void stopMusic() {
        if (music != null && music.isPlaying()) music.stop();
    }

    // ==================== МОБЫ ====================
    public void updateMobs(float delta, Player player) {
        for (Mob mob : mobs) {
            // Простое движение в сторону игрока (для теста)
            float dx = player.getX() - mob.getX(); // используем getX/getY из Mob
            float dy = player.getY() - mob.getY();
            float len = (float) Math.sqrt(dx * dx + dy * dy);
            if (len > 0) {
                dx /= len;
                dy /= len;
            }
            mob.update(delta, dx, dy);
        }
    }

    // ==================== РЕНДЕР ====================
    public void drawGround(SpriteBatch batch) {
        float tileSize = 160f;
        for (int x = 0; x < Constants.WORLD_WIDTH; x += tileSize) {
            for (int y = 0; y < Constants.WORLD_HEIGHT; y += tileSize) {
                batch.draw(assets.groundTexture, x, y, tileSize, tileSize);
            }
        }
    }

    public void draw(SpriteBatch batch) {
        drawGround(batch);

        // Объекты окружения
        for (int i = 0; i < obstacles.size; i++) {
            Rectangle rect = obstacles.get(i);
            String type = obstacleTypes.get(i);

            switch (type) {
                case "tree":    batch.draw(assets.treeMedium, rect.x - 50, rect.y - 15, 128, 128); break;
                case "rock":    batch.draw(assets.rock, rect.x - 12, rect.y - 10, 40, 40); break;
                case "bushMedium": batch.draw(assets.bushMedium, rect.x - 18, rect.y - 12, 45, 45); break;
                case "bushLarge":  batch.draw(assets.bushLarge, rect.x - 14, rect.y - 16, 60, 55); break;
                case "stumpShort": batch.draw(assets.stumpShort, rect.x - 14, rect.y - 8, 35, 35); break;
                case "stumpTall":  batch.draw(assets.stumpTall, rect.x - 12, rect.y - 6, 38, 42); break;
            }
        }

        // Рисуем мобов (кабанов)
        for (Mob mob : mobs) {
            mob.render(batch);
        }
    }
    // Добавь в конец класса GameMap
    public Array<Mob> getMobs() {
        return mobs;
    }

    public boolean collides(Rectangle bounds) {
        for (Rectangle obstacle : obstacles) {
            if (obstacle.overlaps(bounds)) return true;
        }
        if (bounds.x < 0 || bounds.y < 0 ||
            bounds.x + bounds.width > Constants.WORLD_WIDTH ||
            bounds.y + bounds.height > Constants.WORLD_HEIGHT) {
            return true;
        }
        return false;
    }

    public void dispose() {
        stopMusic();
        for (Mob mob : mobs) {
            mob.dispose();
        }
    }

    public Array<Rectangle> getObstacles() { return obstacles; }
    public Array<String> getObstacleTypes() { return obstacleTypes; }
}
