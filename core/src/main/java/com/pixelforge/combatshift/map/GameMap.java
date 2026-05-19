package com.pixelforge.combatshift.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;

public class GameMap {

    private final AssetManagerHelper assets;
    private final Array<Rectangle> obstacles = new Array<>();

    // Для удобства будем хранить тип объекта
    private final Array<String> obstacleTypes = new Array<>();

    public GameMap(AssetManagerHelper assets) {
        this.assets = assets;
        generateObstacles();
    }

    private void generateObstacles() {
        obstacles.clear();
        obstacleTypes.clear();

        // === Деревья (Tree Medium) ===
        for (int i = 0; i < 40; i++) {
            float x = MathUtils.random(80, Constants.WORLD_WIDTH - 100);
            float y = MathUtils.random(80, Constants.WORLD_HEIGHT - 100);
            addObstacle(x, y, "tree", 20, 14, 28);
        }

        // === Камни ===
        for (int i = 0; i < 30; i++) {
            float x = MathUtils.random(60, Constants.WORLD_WIDTH - 80);
            float y = MathUtils.random(60, Constants.WORLD_HEIGHT - 80);
            addObstacle(x, y, "rock", 20, 16, 22);
        }

        // === Кусты Medium ===
        for (int i = 0; i < 35; i++) {
            float x = MathUtils.random(50, Constants.WORLD_WIDTH - 70);
            float y = MathUtils.random(50, Constants.WORLD_HEIGHT - 70);
            addObstacle(x, y, "bushMedium", 22, 14, 20);
        }

        // === Кусты Large ===
        for (int i = 0; i < 25; i++) {
            float x = MathUtils.random(55, Constants.WORLD_WIDTH - 75);
            float y = MathUtils.random(55, Constants.WORLD_HEIGHT - 75);
            addObstacle(x, y, "bushLarge", 35, 16, 24);
        }

        // === Пни Short ===
        for (int i = 0; i < 20; i++) {
            float x = MathUtils.random(45, Constants.WORLD_WIDTH - 65);
            float y = MathUtils.random(45, Constants.WORLD_HEIGHT - 65);
            addObstacle(x, y, "stumpShort", 16, 12, 18);
        }

        // === Пни Tall ===
        for (int i = 0; i < 15; i++) {
            float x = MathUtils.random(50, Constants.WORLD_WIDTH - 70);
            float y = MathUtils.random(50, Constants.WORLD_HEIGHT - 70);
            addObstacle(x, y, "stumpTall", 14, 12, 20);
        }
    }

    private void addObstacle(float x, float y, String type, float collisionW, float collisionH, float offsetY) {
        obstacles.add(new Rectangle(x, y + offsetY, collisionW, collisionH));
        obstacleTypes.add(type);
    }

    public Array<Rectangle> getObstacles() {
        return obstacles;
    }

    public void draw(SpriteBatch batch) {
        // Земля
        for (int x = 0; x < Constants.WORLD_WIDTH; x += 256) {
            for (int y = 0; y < Constants.WORLD_HEIGHT; y += 256) {
                batch.draw(assets.groundTexture, x, y, 256, 256);
            }
        }

        // Объекты
        for (int i = 0; i < obstacles.size; i++) {
            Rectangle rect = obstacles.get(i);
            String type = obstacleTypes.get(i);

            switch (type) {
                case "tree":
                    batch.draw(assets.treeMedium, rect.x - 50, rect.y - 15, 128, 128);
                    break;
                case "rock":
                    batch.draw(assets.rock, rect.x - 12, rect.y - 10, 40, 40);
                    break;
                case "bushMedium":
                    batch.draw(assets.bushMedium, rect.x - 18, rect.y - 12, 45, 45);
                    break;
                case "bushLarge":
                    batch.draw(assets.bushLarge, rect.x - 14, rect.y - 16, 60, 55);
                    break;
                case "stumpShort":
                    batch.draw(assets.stumpShort, rect.x - 14, rect.y - 8, 35, 35);
                    break;
                case "stumpTall":
                    batch.draw(assets.stumpTall, rect.x - 12, rect.y - 6, 38, 42);
                    break;
            }
        }
    }

    public boolean collides(Rectangle bounds) {
        for (Rectangle obstacle : obstacles) {
            if (obstacle.overlaps(bounds)) return true;
        }

        // Границы мира
        if (bounds.x < 0 || bounds.y < 0 ||
            bounds.x + bounds.width > Constants.WORLD_WIDTH ||
            bounds.y + bounds.height > Constants.WORLD_HEIGHT) {
            return true;
        }
        return false;
    }

    public void dispose() {}
}
