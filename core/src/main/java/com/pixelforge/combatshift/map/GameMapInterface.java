package com.pixelforge.combatshift.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public interface GameMapInterface {

    void drawGround(SpriteBatch batch);

    void draw(SpriteBatch batch);

    boolean collides(Rectangle bounds);

    Array<Rectangle> getObstacles();

    Array<String> getObstacleTypes();

    void dispose();
}
