package com.pixelforge.combatshift.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;

public class WinterMap implements GameMapInterface {

    private final AssetManagerHelper assets;
    private final Array<Rectangle> obstacles = new Array<>();
    private final Array<String> obstacleTypes = new Array<>();

    public WinterMap(AssetManagerHelper assets) {
        this.assets = assets;
        generateObstacles();
    }

    private void generateObstacles() {
        obstacles.clear();
        obstacleTypes.clear();

        float minDistance = 60f;   // Чуть больше расстояние для зимы

        // === Деревья ===
        for (int i = 0; i < 35; i++) {
            float x = MathUtils.random(100, Constants.WORLD_WIDTH - 120);
            float y = MathUtils.random(100, Constants.WORLD_HEIGHT - 120);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "tree", 24, 16, 30);
            }
        }

        // === Камни Medium ===
        for (int i = 0; i < 25; i++) {
            float x = MathUtils.random(80, Constants.WORLD_WIDTH - 100);
            float y = MathUtils.random(80, Constants.WORLD_HEIGHT - 100);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "rockMedium", 30, 20, 22);
            }
        }

        // === Камни Small ===
        for (int i = 0; i < 20; i++) {
            float x = MathUtils.random(70, Constants.WORLD_WIDTH - 90);
            float y = MathUtils.random(70, Constants.WORLD_HEIGHT - 90);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "rockSmall", 44, 24, 18);
            }
        }

        // === Кусты Medium ===
        for (int i = 0; i < 30; i++) {
            float x = MathUtils.random(65, Constants.WORLD_WIDTH - 85);
            float y = MathUtils.random(65, Constants.WORLD_HEIGHT - 85);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "bushMedium", 44, 20, 20);
            }
        }

        // === Кусты Small ===
        for (int i = 0; i < 28; i++) {
            float x = MathUtils.random(55, Constants.WORLD_WIDTH - 75);
            float y = MathUtils.random(55, Constants.WORLD_HEIGHT - 75);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "bushSmall", 45, 20, 18);
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

    public Array<Rectangle> getObstacles() { return obstacles; }
    public Array<String> getObstacleTypes() { return obstacleTypes; }

    public void drawGround(SpriteBatch batch) {
        float tileSize = 90f;

        for (int x = 0; x < Constants.WORLD_WIDTH; x += tileSize) {
            for (int y = 0; y < Constants.WORLD_HEIGHT; y += tileSize) {
                batch.draw(assets.winterGround, x, y, tileSize, tileSize);
            }
        }
    }

    public void draw(SpriteBatch batch) {
        drawGround(batch);

        for (int i = 0; i < obstacles.size; i++) {
            Rectangle rect = obstacles.get(i);
            String type = obstacleTypes.get(i);

            switch (type) {
                case "tree":
                    batch.draw(assets.winterTree, rect.x - 55, rect.y - 25, 140, 140);
                    break;
                case "rockMedium":
                    batch.draw(assets.winterRockMedium, rect.x - 22, rect.y - 18, 65, 60);
                    break;
                case "rockSmall":
                    batch.draw(assets.winterRockSmall, rect.x - 15, rect.y - 12, 48, 45);
                    break;
                case "bushMedium":
                    batch.draw(assets.winterBushMedium, rect.x - 18, rect.y - 16, 55, 52);
                    break;
                case "bushSmall":
                    batch.draw(assets.winterBushSmall, rect.x - 14, rect.y - 12, 45, 42);
                    break;
            }
        }
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

    public void dispose() {}
}
