package com.pixelforge.combatshift.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;

public class GameMap {
    private final int width;
    private final int height;
    private final TileType[][] tiles;
    private final Array<MapObject> objects = new Array<>();

    public GameMap(int width, int height, AssetManagerHelper assets) {
        this.width = width;
        this.height = height;
        this.tiles = new TileType[width][height];
        generateNatureLocation(assets);
    }

    private void generateNatureLocation(AssetManagerHelper assets) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float noise = MathUtils.random();
                if (noise < 0.08f) {
                    tiles[x][y] = TileType.LIGHT_GRASS;
                } else if (noise < 0.15f) {
                    tiles[x][y] = TileType.DARK_GRASS;
                } else {
                    tiles[x][y] = TileType.GRASS;
                }
            }
        }

        addDirtPatches();
        addLakes();
        addFlowers(assets);
        addTreesAndRocks(assets);
        clearSafeStartArea();
    }

    private void addDirtPatches() {
        for (int i = 0; i < 18; i++) {
            int cx = MathUtils.random(6, width - 7);
            int cy = MathUtils.random(6, height - 7);
            int rx = MathUtils.random(2, 5);
            int ry = MathUtils.random(2, 5);
            for (int x = cx - rx; x <= cx + rx; x++) {
                for (int y = cy - ry; y <= cy + ry; y++) {
                    if (inBounds(x, y) && MathUtils.randomBoolean(0.65f)) {
                        tiles[x][y] = TileType.DIRT;
                    }
                }
            }
        }
    }

    private void addLakes() {
        for (int i = 0; i < 7; i++) {
            int cx = MathUtils.random(12, width - 13);
            int cy = MathUtils.random(12, height - 13);
            int rx = MathUtils.random(4, 8);
            int ry = MathUtils.random(3, 6);
            for (int x = cx - rx; x <= cx + rx; x++) {
                for (int y = cy - ry; y <= cy + ry; y++) {
                    if (!inBounds(x, y)) continue;
                    float nx = (x - cx) / (float) rx;
                    float ny = (y - cy) / (float) ry;
                    if (nx * nx + ny * ny <= 1f + MathUtils.random(-0.08f, 0.15f)) {
                        tiles[x][y] = TileType.WATER;
                    }
                }
            }
        }
    }

    private void addFlowers(AssetManagerHelper assets) {
        for (int i = 0; i < 90; i++) {
            int tileX = MathUtils.random(2, width - 3);
            int tileY = MathUtils.random(2, height - 3);
            if (tiles[tileX][tileY].isWater()) continue;
            tiles[tileX][tileY] = TileType.FLOWERS;
            Rectangle bounds = new Rectangle(tileX * Constants.TILE_SIZE + 8f,
                    tileY * Constants.TILE_SIZE + 8f,
                    16f,
                    16f);
            objects.add(new MapObject(MapObjectType.FLOWER, bounds, assets.flower(), false,
                    20f, 20f, -2f, -2f));
        }
    }

    private void addTreesAndRocks(AssetManagerHelper assets) {
        for (int i = 0; i < 180; i++) {
            int tileX = MathUtils.random(2, width - 3);
            int tileY = MathUtils.random(2, height - 3);
            if (!canPlaceBlockingObject(tileX, tileY)) continue;
            Rectangle bounds = new Rectangle(tileX * Constants.TILE_SIZE, tileY * Constants.TILE_SIZE, 32f, 32f);
            objects.add(new MapObject(MapObjectType.TREE, bounds, assets.tree(), true,
                    64f, 64f, -16f, -8f));
        }

        for (int i = 0; i < 75; i++) {
            int tileX = MathUtils.random(2, width - 3);
            int tileY = MathUtils.random(2, height - 3);
            if (!canPlaceBlockingObject(tileX, tileY)) continue;
            Rectangle bounds = new Rectangle(tileX * Constants.TILE_SIZE + 3f, tileY * Constants.TILE_SIZE + 4f, 26f, 24f);
            objects.add(new MapObject(MapObjectType.ROCK, bounds, assets.rock(), true,
                    42f, 30f, -8f, -2f));
        }
    }

    private boolean canPlaceBlockingObject(int tileX, int tileY) {
        if (!inBounds(tileX, tileY) || tiles[tileX][tileY].isWater()) {
            return false;
        }
        int centerX = width / 2;
        int centerY = height / 2;
        if (Math.abs(tileX - centerX) < 7 && Math.abs(tileY - centerY) < 7) {
            return false;
        }
        Rectangle candidate = new Rectangle(tileX * Constants.TILE_SIZE, tileY * Constants.TILE_SIZE, 32f, 32f);
        for (MapObject object : objects) {
            if (object.isBlocking() && object.getBounds().overlaps(candidate)) {
                return false;
            }
        }
        return true;
    }

    private void clearSafeStartArea() {
        int centerX = width / 2;
        int centerY = height / 2;
        for (int x = centerX - 5; x <= centerX + 5; x++) {
            for (int y = centerY - 5; y <= centerY + 5; y++) {
                if (inBounds(x, y)) tiles[x][y] = TileType.GRASS;
            }
        }
        for (int i = objects.size - 1; i >= 0; i--) {
            MapObject object = objects.get(i);
            float ox = object.getBounds().x / Constants.TILE_SIZE;
            float oy = object.getBounds().y / Constants.TILE_SIZE;
            if (Math.abs(ox - centerX) < 7 && Math.abs(oy - centerY) < 7) {
                objects.removeIndex(i);
            }
        }
    }

    public void draw(SpriteBatch batch, OrthographicCamera camera, AssetManagerHelper assets) {
        float tile = Constants.TILE_SIZE;
        int startX = Math.max(0, (int) ((camera.position.x - camera.viewportWidth * camera.zoom * 0.55f) / tile) - 1);
        int endX = Math.min(width - 1, (int) ((camera.position.x + camera.viewportWidth * camera.zoom * 0.55f) / tile) + 1);
        int startY = Math.max(0, (int) ((camera.position.y - camera.viewportHeight * camera.zoom * 0.55f) / tile) - 1);
        int endY = Math.min(height - 1, (int) ((camera.position.y + camera.viewportHeight * camera.zoom * 0.55f) / tile) + 1);

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                batch.draw(assets.tile(tiles[x][y]), x * tile, y * tile, tile, tile);
            }
        }

        for (MapObject object : objects) {
            Rectangle bounds = object.getBounds();
            if (bounds.x + 80f < camera.position.x - camera.viewportWidth * camera.zoom * 0.6f) continue;
            if (bounds.x - 80f > camera.position.x + camera.viewportWidth * camera.zoom * 0.6f) continue;
            if (bounds.y + 80f < camera.position.y - camera.viewportHeight * camera.zoom * 0.6f) continue;
            if (bounds.y - 80f > camera.position.y + camera.viewportHeight * camera.zoom * 0.6f) continue;
            object.draw(batch);
        }
    }

    public boolean collides(Rectangle bounds) {
        if (bounds.x < 0f || bounds.y < 0f || bounds.x + bounds.width > getWorldWidth() || bounds.y + bounds.height > getWorldHeight()) {
            return true;
        }

        int minX = Math.max(0, (int) (bounds.x / Constants.TILE_SIZE));
        int maxX = Math.min(width - 1, (int) ((bounds.x + bounds.width) / Constants.TILE_SIZE));
        int minY = Math.max(0, (int) (bounds.y / Constants.TILE_SIZE));
        int maxY = Math.min(height - 1, (int) ((bounds.y + bounds.height) / Constants.TILE_SIZE));
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (tiles[x][y].isBlocking()) return true;
            }
        }

        for (MapObject object : objects) {
            if (object.isBlocking() && object.getBounds().overlaps(bounds)) return true;
        }
        return false;
    }

    public boolean isWater(Rectangle bounds) {
        int tileX = MathUtils.clamp((int) ((bounds.x + bounds.width * 0.5f) / Constants.TILE_SIZE), 0, width - 1);
        int tileY = MathUtils.clamp((int) ((bounds.y + bounds.height * 0.5f) / Constants.TILE_SIZE), 0, height - 1);
        return tiles[tileX][tileY].isWater();
    }

    public float speedMultiplier(Rectangle bounds) {
        int tileX = MathUtils.clamp((int) ((bounds.x + bounds.width * 0.5f) / Constants.TILE_SIZE), 0, width - 1);
        int tileY = MathUtils.clamp((int) ((bounds.y + bounds.height * 0.5f) / Constants.TILE_SIZE), 0, height - 1);
        return tiles[tileX][tileY].speedMultiplier();
    }

    public Vector2 randomFreePositionFarFrom(Vector2 target, float minDistance, float maxDistance) {
        for (int attempt = 0; attempt < 160; attempt++) {
            float angle = MathUtils.random(MathUtils.PI2);
            float distance = MathUtils.random(minDistance, maxDistance);
            float x = MathUtils.clamp(target.x + MathUtils.cos(angle) * distance, Constants.TILE_SIZE * 3f, getWorldWidth() - Constants.TILE_SIZE * 3f);
            float y = MathUtils.clamp(target.y + MathUtils.sin(angle) * distance, Constants.TILE_SIZE * 3f, getWorldHeight() - Constants.TILE_SIZE * 3f);
            Rectangle candidate = new Rectangle(x - 14f, y - 14f, 28f, 28f);
            if (!collides(candidate) && !isWater(candidate)) {
                return new Vector2(x, y);
            }
        }
        return new Vector2(getWorldWidth() * 0.5f + 120f, getWorldHeight() * 0.5f + 120f);
    }

    public Vector2 startPosition() {
        return new Vector2(getWorldWidth() * 0.5f, getWorldHeight() * 0.5f);
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public float getWorldWidth() {
        return width * Constants.TILE_SIZE;
    }

    public float getWorldHeight() {
        return height * Constants.TILE_SIZE;
    }

    public Array<MapObject> getObjects() {
        return objects;
    }
}

