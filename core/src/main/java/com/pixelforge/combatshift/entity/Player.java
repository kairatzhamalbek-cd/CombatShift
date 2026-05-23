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

    private Animation<TextureRegion> walkUp, walkDown, walkLeft, walkRight;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime = 0f;

    // === HP ===
    private float hp = 100f;
    private final float maxHp = 100f;

    // === СТАМИНА ===
    private float stamina = 100f;
    private final float maxStamina = 100f;
    private float staminaRegenTimer = 0f;

    public Player(float x, float y, AssetManagerHelper assets) {
        this.x = x;
        this.y = y;

        float frameDuration = 0.12f;
        walkUp    = createAnimation(assets.moveUp1, assets.moveUp2, frameDuration);
        walkDown  = createAnimation(assets.moveDown1, assets.moveDown2, frameDuration);
        walkLeft  = createAnimation(assets.moveLeft1, assets.moveLeft2, frameDuration);
        walkRight = createAnimation(assets.moveRight1, assets.moveRight2, frameDuration);

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

        // Stamina regen
        staminaRegenTimer += delta;
        if (staminaRegenTimer >= 3f) {
            stamina = Math.min(maxStamina, stamina + 8f);
            staminaRegenTimer = 0f;
        }
    }

    public void takeDamage(float damage) {
        hp = Math.max(0, hp - damage);
    }

    public void fullHeal() {
        hp = maxHp;
    }

    public void render(SpriteBatch batch) {
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        float scale = Constants.PLAYER_SIZE * 4.8f;
        batch.draw(frame, x - scale/2, y - scale/2 + 6, scale, scale);
    }

    // === ГЕТТЕРЫ ДЛЯ HUD ===
    public float getHp() { return hp; }
    public float getMaxHp() { return maxHp; }
    public float getHpRatio() { return hp / maxHp; }

    public float getStamina() { return stamina; }
    public float getMaxStamina() { return maxStamina; }
    public float getStaminaRatio() { return stamina / maxStamina; }

    public float getX() { return x; }
    public float getY() { return y; }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void dispose() {}
}
