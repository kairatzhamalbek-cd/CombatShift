package com.pixelforge.combatshift.entity;

import com.badlogic.gdx.graphics.Texture;           // ← Вот эта строка была пропущена
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;

public class Player {
    private float x, y;
    private final float walkSpeed = Constants.PLAYER_SPEED;
    private final float runSpeed = Constants.PLAYER_SPEED * 1.65f;

    // Анимации
    private Animation<TextureRegion> idleDown, idleLeft, idleRight, idleUp;
    private Animation<TextureRegion> walkDown, walkLeft, walkRight, walkUp;
    private Animation<TextureRegion> runDown, runLeft, runRight, runUp;
    private Animation<TextureRegion> attackDown, attackLeft, attackRight, attackUp;
    private Animation<TextureRegion> walkAttackDown, walkAttackLeft, walkAttackRight, walkAttackUp;
    private Animation<TextureRegion> runAttackDown, runAttackLeft, runAttackRight, runAttackUp;
    private Animation<TextureRegion> hurtDown, hurtLeft, hurtRight, hurtUp;

    private Animation<TextureRegion> currentAnimation;
    private float stateTime = 0f;

    private boolean isAttacking = false;
    private float attackTimer = 0f;

    public Player(float startX, float startY, AssetManagerHelper assets) {
        this.x = startX;
        this.y = startY;

        float frameDuration = 0.08f;

        // Idle
        idleDown  = createAnimation(assets.idleSheet, 0, 12, frameDuration);
        idleLeft  = createAnimation(assets.idleSheet, 1, 12, frameDuration);
        idleRight = createAnimation(assets.idleSheet, 2, 12, frameDuration);
        idleUp    = createAnimation(assets.idleSheet, 3, 4, frameDuration);

        // Walk
        walkDown  = createAnimation(assets.walkSheet, 0, 6, frameDuration);
        walkLeft  = createAnimation(assets.walkSheet, 1, 6, frameDuration);
        walkRight = createAnimation(assets.walkSheet, 2, 6, frameDuration);
        walkUp    = createAnimation(assets.walkSheet, 3, 6, frameDuration);

        // Run
        runDown  = createAnimation(assets.runSheet, 0, 8, frameDuration);
        runLeft  = createAnimation(assets.runSheet, 1, 8, frameDuration);
        runRight = createAnimation(assets.runSheet, 2, 8, frameDuration);
        runUp    = createAnimation(assets.runSheet, 3, 8, frameDuration);

        // Attack
        attackDown  = createAnimation(assets.attackSheet, 0, 8, 0.07f);
        attackLeft  = createAnimation(assets.attackSheet, 1, 8, 0.07f);
        attackRight = createAnimation(assets.attackSheet, 2, 8, 0.07f);
        attackUp    = createAnimation(assets.attackSheet, 3, 8, 0.07f);

        // Walk + Attack
        walkAttackDown  = createAnimation(assets.walkAttackSheet, 0, 6, 0.07f);
        walkAttackLeft  = createAnimation(assets.walkAttackSheet, 1, 6, 0.07f);
        walkAttackRight = createAnimation(assets.walkAttackSheet, 2, 6, 0.07f);
        walkAttackUp    = createAnimation(assets.walkAttackSheet, 3, 6, 0.07f);

        // Run + Attack
        runAttackDown  = createAnimation(assets.runAttackSheet, 0, 8, 0.07f);
        runAttackLeft  = createAnimation(assets.runAttackSheet, 1, 8, 0.07f);
        runAttackRight = createAnimation(assets.runAttackSheet, 2, 8, 0.07f);
        runAttackUp    = createAnimation(assets.runAttackSheet, 3, 8, 0.07f);

        // Hurt
        hurtDown  = createAnimation(assets.hurtSheet, 0, 5, 0.1f);
        hurtLeft  = createAnimation(assets.hurtSheet, 1, 5, 0.1f);
        hurtRight = createAnimation(assets.hurtSheet, 2, 5, 0.1f);
        hurtUp    = createAnimation(assets.hurtSheet, 3, 5, 0.1f);

        currentAnimation = idleDown;
    }

    private Animation<TextureRegion> createAnimation(Texture sheet, int row, int frameCount, float frameDuration) {
        Array<TextureRegion> frames = new Array<>();
        int frameWidth = 64;
        int frameHeight = 64;
        int startY = row * frameHeight;

        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(sheet, i * frameWidth, startY, frameWidth, frameHeight));
        }
        return new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
    }

    public void update(float delta, float dx, float dy, boolean isRunning, boolean isAttackingNow) {
        stateTime += delta;

        if (isAttackingNow && !isAttacking) {
            isAttacking = true;
            attackTimer = 0.5f;
        }
        if (isAttacking) {
            attackTimer -= delta;
            if (attackTimer <= 0) isAttacking = false;
        }

        if (isAttacking) {
            if (Math.abs(dy) > Math.abs(dx)) {
                currentAnimation = (dy > 0) ? attackUp : attackDown;
            } else if (dx != 0) {
                currentAnimation = (dx > 0) ? attackRight : attackLeft;
            } else {
                currentAnimation = attackDown;
            }
        } else if (dx != 0 || dy != 0) {
            if (Math.abs(dy) > Math.abs(dx)) {
                currentAnimation = (dy > 0) ? (isRunning ? runUp : walkUp) : (isRunning ? runDown : walkDown);
            } else {
                currentAnimation = (dx > 0) ? (isRunning ? runRight : walkRight) : (isRunning ? runLeft : walkLeft);
            }
        } else {
            if (currentAnimation == walkDown || currentAnimation == runDown) currentAnimation = idleDown;
            else if (currentAnimation == walkLeft || currentAnimation == runLeft) currentAnimation = idleLeft;
            else if (currentAnimation == walkRight || currentAnimation == runRight) currentAnimation = idleRight;
            else if (currentAnimation == walkUp || currentAnimation == runUp) currentAnimation = idleUp;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        float scale = Constants.PLAYER_SIZE * 3.3f;

        batch.draw(frame, x - scale / 2f, y - scale / 2f + 8, scale, scale);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void dispose() {}
}
