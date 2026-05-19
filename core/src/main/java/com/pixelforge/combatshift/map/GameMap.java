package com.pixelforge.combatshift.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;

public class GameMap {

    private final AssetManagerHelper assets;
    private final Array<Rectangle> obstacles = new Array<>();

    public GameMap(AssetManagerHelper assets) {
        this.assets = assets;
        generateObstacles();
    }
    public void dispose() {
        // Ничего не делаем здесь,
        // потому что AssetManagerHelper.dispose() вызывается из GameScreen
    }

    private void generateObstacles() {
        // Размер отрисовки дерева (визуальный)
        float drawSize = 64;

        // Размер коллизии (гораздо меньше!)
        float collisionWidth  = 22;   // ширина коллизии
        float collisionHeight = 15;   // высота коллизии

        // Смещение коллизии вниз (чтобы она была у ствола, а не в кроне)
        float collisionOffsetY = 22;

        // Деревья
        obstacles.add(new Rectangle(300, 400 + collisionOffsetY, collisionWidth, collisionHeight));
        obstacles.add(new Rectangle(600, 300 + collisionOffsetY, collisionWidth, collisionHeight));
        obstacles.add(new Rectangle(900, 550 + collisionOffsetY, collisionWidth, collisionHeight));
        obstacles.add(new Rectangle(400, 700 + collisionOffsetY, collisionWidth, collisionHeight));
        obstacles.add(new Rectangle(1100, 200 + collisionOffsetY, collisionWidth, collisionHeight));
    }

    public void draw(SpriteBatch batch) {
        // Рисуем землю
        for (int x = 0; x < Constants.WORLD_WIDTH; x += 256) {
            for (int y = 0; y < Constants.WORLD_HEIGHT; y += 256) {
                batch.draw(assets.groundTexture, x, y, 256, 256);
            }
        }

        // Рисуем деревья (полный размер)
        float treeDrawSize = 128;
        for (Rectangle tree : obstacles) {
            // Рисуем дерево чуть выше коллизии, чтобы visually было красиво
            float collisionOffsetY = 22;
            batch.draw(assets.treeTexture,
                tree.x - 60,                    // смещаем влево, чтобы дерево было по центру коллизии
                tree.y - collisionOffsetY,      // поднимаем дерево вверх
                treeDrawSize, treeDrawSize);
        }
    }

    public boolean collides(Rectangle bounds) {
        for (Rectangle obstacle : obstacles) {
            if (obstacle.overlaps(bounds)) {
                return true;
            }
        }
        return false;
    }
}
