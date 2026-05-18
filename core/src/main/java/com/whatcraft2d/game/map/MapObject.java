package com.whatcraft2d.game.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class MapObject {
    private final MapObjectType type;
    private final Rectangle bounds;
    private final TextureRegion region;
    private final boolean blocking;
    private final float drawWidth;
    private final float drawHeight;
    private final float drawOffsetX;
    private final float drawOffsetY;

    public MapObject(MapObjectType type,
                     Rectangle bounds,
                     TextureRegion region,
                     boolean blocking,
                     float drawWidth,
                     float drawHeight,
                     float drawOffsetX,
                     float drawOffsetY) {
        this.type = type;
        this.bounds = bounds;
        this.region = region;
        this.blocking = blocking;
        this.drawWidth = drawWidth;
        this.drawHeight = drawHeight;
        this.drawOffsetX = drawOffsetX;
        this.drawOffsetY = drawOffsetY;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(region, bounds.x + drawOffsetX, bounds.y + drawOffsetY, drawWidth, drawHeight);
    }

    public MapObjectType getType() {
        return type;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isBlocking() {
        return blocking;
    }
}
