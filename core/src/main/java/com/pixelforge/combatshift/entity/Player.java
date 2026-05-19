package com.pixelforge.combatshift.entity;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pixelforge.combatshift.Constants;

public class Player {
    private float x;
    private float y;
    private final float speed;
    private final Texture texture;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.speed = Constants.PLAYER_SPEED;
        this.texture = createTexture();
    }

    public void update(float delta) {
        // Движение будет обрабатываться в GameScreen
    }

    public void move(float dx, float dy, float delta) {
        x += dx * speed * delta;
        y += dy * speed * delta;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
    }

    private Texture createTexture() {
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.2f, 0.8f, 0.3f, 1f);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    public void dispose() {
        texture.dispose();
    }

    // Getters
    public float getX() { return x; }
    public float getY() { return y; }
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
