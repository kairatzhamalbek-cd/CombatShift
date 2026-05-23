package com.pixelforge.combatshift.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.assets.AssetManagerHelper;

public class Mob {
    private float x, y;
    private final float speed = 90f;
    private float hp = 40f;           // ← Добавлено

    private Animation<TextureRegion> walkUp, walkDown, walkLeft, walkRight;
    private Animation<TextureRegion> currentAnimation;

    private float stateTime = 0f;

    public Mob(float x, float y, AssetManagerHelper assets) {
        this.x = x;
        this.y = y;

        float frameDuration = 0.15f;

        walkUp    = createAnimation(assets.boarMoveUp,    assets.boarMoveUp,    frameDuration);
        walkDown  = createAnimation(assets.boarMoveDown,  assets.boarMoveDown,  frameDuration);
        walkLeft  = createAnimation(assets.boarMoveLeft,  assets.boarMoveLeft,  frameDuration);
        walkRight = createAnimation(assets.boarMoveRight, assets.boarMoveRight, frameDuration);

        currentAnimation = walkDown;
    }

    private Animation<TextureRegion> createAnimation(Texture t1, Texture t2, float frameDuration) {
        Array<TextureRegion> frames = new Array<>();
        frames.add(new TextureRegion(t1));
        frames.add(new TextureRegion(t2));
        return new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
    }

    public void update(float delta, float dx, float dy) {
        stateTime += delta;

        if (dx != 0 || dy != 0) {
            if (Math.abs(dy) > Math.abs(dx)) {
                currentAnimation = (dy > 0) ? walkUp : walkDown;
            } else {
                currentAnimation = (dx > 0) ? walkRight : walkLeft;
            }
        }

        x += dx * speed * delta;
        y += dy * speed * delta;
    }

    public void render(SpriteBatch batch) {
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        float scale = 48f;
        batch.draw(frame, x - scale/2, y - scale/2 + 4, scale, scale);
    }

    public Rectangle getBounds() {
        return new Rectangle(x - 18, y - 18, 36, 36);
    }

    // ==================== HP ====================
    public void takeDamage(float damage) {
        hp -= damage;
    }

    public boolean isDead() {           // ← Добавлен этот метод
        return hp <= 0;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void dispose() {
        // Текстуры удаляются через AssetManager
    }
}
