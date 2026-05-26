package com.pixelforge.combatshift.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;

public class Vampire {
    private float x, y;
    private float hp = 62f;
    private final float maxHp = 62f;
    private final float speed = 220f;
    private final float damage = 10f;
    private float attackCooldown = 0f;

    private Animation<TextureRegion> currentAnimation;
    private float stateTime = 0f;

    private Animation<TextureRegion> idleDown, idleUp, idleLeft, idleRight;
    private Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    private Animation<TextureRegion> attackDown, attackUp, attackLeft, attackRight;

    private boolean isDead = false;

    private final AssetManagerHelper assets;   // ← ДОБАВЛЕНО

    public Vampire(float startX, float startY, AssetManagerHelper assets) {
        this.x = startX;
        this.y = startY;
        this.assets = assets;                  // ← ДОБАВЛЕНО

        float fd = 0.09f;

        idleDown  = createAnimation(assets.vampire1IdleSheet, 0, 4, fd);
        idleUp    = createAnimation(assets.vampire1IdleSheet, 1, 4, fd);
        idleLeft  = createAnimation(assets.vampire1IdleSheet, 2, 4, fd);
        idleRight = createAnimation(assets.vampire1IdleSheet, 3, 4, fd);

        walkDown  = createAnimation(assets.vampire1WalkSheet, 0, 6, fd);
        walkUp    = createAnimation(assets.vampire1WalkSheet, 1, 6, fd);
        walkLeft  = createAnimation(assets.vampire1WalkSheet, 2, 6, fd);
        walkRight = createAnimation(assets.vampire1WalkSheet, 3, 6, fd);

        attackDown  = createAnimation(assets.vampire1AttackSheet, 0, 12, 0.07f);
        attackUp    = createAnimation(assets.vampire1AttackSheet, 1, 12, 0.07f);
        attackLeft  = createAnimation(assets.vampire1AttackSheet, 2, 12, 0.07f);
        attackRight = createAnimation(assets.vampire1AttackSheet, 3, 12, 0.07f);

        currentAnimation = idleDown;
    }

    private Animation<TextureRegion> createAnimation(Texture sheet, int row, int frameCount, float frameDuration) {
        Array<TextureRegion> frames = new Array<>();
        int fw = 64, fh = 64;
        int sy = row * fh;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(sheet, i * fw, sy, fw, fh));
        }
        return new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
    }

    public void update(float delta, float dx, float dy) {
        if (isDead) return;
        stateTime += delta;
        attackCooldown -= delta;

        if (dx != 0 || dy != 0) {
            if (Math.abs(dy) > Math.abs(dx)) {
                currentAnimation = (dy > 0) ? walkUp : walkDown;
            } else {
                currentAnimation = (dx > 0) ? walkRight : walkLeft;
            }
            x += dx * speed * delta;
            y += dy * speed * delta;
        } else {
            currentAnimation = idleDown;
        }
    }

    public void render(SpriteBatch batch) {
        if (isDead) return;
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        float size = 64 * Constants.VAMPIRE_SCALE;
        batch.draw(frame, x - size / 2f, y - size / 2f, size, size);    }

    public Rectangle getBounds() { return new Rectangle(x - 22, y - 22, 44, 44); }

    public void takeDamage(float dmg) {
        hp -= dmg;
        if (hp <= 0) isDead = true;
    }

    public boolean isDead() { return isDead; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getDamage() { return damage; }
    public float getAttackCooldown() { return attackCooldown; }
    public void setAttackCooldown(float time) { attackCooldown = time; }
    public void setPosition(float x, float y) { this.x = x; this.y = y; }
    public float getHp() { return hp; }
    public float getMaxHp() { return maxHp; }
}
