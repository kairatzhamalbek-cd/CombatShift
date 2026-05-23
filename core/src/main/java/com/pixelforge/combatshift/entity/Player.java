package com.pixelforge.combatshift.entity;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pixelforge.combatshift.Constants;

public class Player {
    private float x, y;
    private final float speed = Constants.PLAYER_SPEED;
    private final Texture texture;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.texture = createTexture();
    }

    private Texture createTexture() {
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.2f, 0.8f, 0.3f, 1f); // зелёный квадрат
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    public void update(float delta, float dx, float dy) {
        // движение обрабатывается в GameScreen
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void dispose() {
        texture.dispose();
    }
}
