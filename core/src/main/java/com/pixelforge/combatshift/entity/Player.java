package com.pixelforge.combatshift.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;

public class Player {
    private float x, y;
    private final float speed = Constants.PLAYER_SPEED;

    // Анимации ходьбы
    private Animation<TextureRegion> walkUp, walkDown, walkLeft, walkRight;
    private Animation<TextureRegion> currentAnimation;

    private float stateTime = 0f;
    private boolean isMoving = false;

    public Player(float x, float y, AssetManagerHelper assets) {
        this.x = x;
        this.y = y;

        float frameDuration = 0.12f; // скорость смены кадров (можно подкрутить)

        walkUp    = createAnimation(assets.moveUp1,    assets.moveUp2,    frameDuration);
        walkDown  = createAnimation(assets.moveDown1,  assets.moveDown2,  frameDuration);
        walkLeft  = createAnimation(assets.moveLeft1,  assets.moveLeft2,  frameDuration);
        walkRight = createAnimation(assets.moveRight1, assets.moveRight2, frameDuration);

        currentAnimation = walkDown; // начальное направление
    }

    private Animation<TextureRegion> createAnimation(Texture t1, Texture t2, float frameDuration) {
        Array<TextureRegion> frames = new Array<>();
        frames.add(new TextureRegion(t1));
        frames.add(new TextureRegion(t2));
        return new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
    }

    public void update(float delta, float dx, float dy) {
        stateTime += delta;

        isMoving = (dx != 0 || dy != 0);

        if (isMoving) {
            if (Math.abs(dy) > Math.abs(dx)) {
                currentAnimation = (dy > 0) ? walkUp : walkDown;
            } else {
                currentAnimation = (dx > 0) ? walkRight : walkLeft;
            }
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);

        float scale = Constants.PLAYER_SIZE * 4.8f; // подбирай под размер спрайтов

        batch.draw(frame,
            x - scale / 2f,
            y - scale / 2f + 6,   // небольшой подъём, чтобы ноги выглядели правильно
            scale, scale);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void dispose() {
        // Текстуры удаляются через AssetManagerHelper
    }
}
