package com.pixelforge.combatshift.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;

public class DesertMap implements GameMapInterface {

    private final AssetManagerHelper assets;
    private final Array<Rectangle> obstacles = new Array<>();
    private final Array<String> obstacleTypes = new Array<>();

    public DesertMap(AssetManagerHelper assets) {
        this.assets = assets;
        generateObstacles();
    }

    private void generateObstacles() {
        obstacles.clear();
        obstacleTypes.clear();

        // === Деревья (Мёртвые деревья пустыни) ===
        for (int i = 0; i < 38; i++) {
            float x = MathUtils.random(80, Constants.WORLD_WIDTH - 100);
            float y = MathUtils.random(80, Constants.WORLD_HEIGHT - 100);
            addObstacle(x, y, "tree", 22, 13, 26);
        }

        // === Камни Medium ===
        for (int i = 0; i < 22; i++) {
            float x = MathUtils.random(60, Constants.WORLD_WIDTH - 80);
            float y = MathUtils.random(60, Constants.WORLD_HEIGHT - 80);
            addObstacle(x, y, "rockMedium", 38, 37, 20);
        }

        // === Камни Small ===
        for (int i = 0; i < 18; i++) {
            float x = MathUtils.random(50, Constants.WORLD_WIDTH - 70);
            float y = MathUtils.random(50, Constants.WORLD_HEIGHT - 70);
            addObstacle(x, y, "rockSmall", 43,42, 16);
        }

        // === Кусты Medium (пустынные) ===
        for (int i = 0; i < 32; i++) {
            float x = MathUtils.random(55, Constants.WORLD_WIDTH - 75);
            float y = MathUtils.random(55, Constants.WORLD_HEIGHT - 75);
            addObstacle(x, y, "bushMedium", 34, 14, 18);
        }

        // === Кусты Small (пустынные) ===
        for (int i = 0; i < 28; i++) {
            float x = MathUtils.random(45, Constants.WORLD_WIDTH - 65);
            float y = MathUtils.random(45, Constants.WORLD_HEIGHT - 65);
            addObstacle(x, y, "bushSmall", 28, 18, 16);
        }
    }

    private void addObstacle(float x, float y, String type, float collisionW, float collisionH, float offsetY) {
        obstacles.add(new Rectangle(x, y + offsetY, collisionW, collisionH));
        obstacleTypes.add(type);
    }

    public Array<Rectangle> getObstacles() {
        return obstacles;
    }

    public Array<String> getObstacleTypes() {
        return obstacleTypes;
    }

    public void drawGround(SpriteBatch batch) {
        for (int x = 0; x < Constants.WORLD_WIDTH; x += 256) {
            for (int y = 0; y < Constants.WORLD_HEIGHT; y += 256) {
                batch.draw(assets.desertGround, x, y, 256, 256);
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
                    batch.draw(assets.desertTreeMedium, rect.x - 50, rect.y - 15, 400, 400);
                    break;
                case "rockMedium":
                    batch.draw(assets.desertRockMedium, rect.x - 18, rect.y - 14, 55, 50);
                    break;
                case "rockSmall":
                    batch.draw(assets.desertRockSmall, rect.x - 17, rect.y - 10, 100, 100);
                    break;
                case "bushMedium":
                    batch.draw(assets.desertBushMedium, rect.x - 16, rect.y - 14, 80, 80);
                    break;
                case "bushSmall":
                    batch.draw(assets.desertBushSmall, rect.x - 12, rect.y - 10, 80, 80);
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
