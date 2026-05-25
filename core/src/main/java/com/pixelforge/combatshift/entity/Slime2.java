package com.pixelforge.combatshift.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;

public class Slime2 {
    private float x, y;
    private float hp = 45f;           // больше чем Slime1
    private final float speed = 180f;
    private final float damage = 8f;  // больше урона
    private float attackCooldown = 0f;

    private Animation<TextureRegion> currentAnimation;
    private float stateTime = 0f;

    private Animation<TextureRegion> idleDown, idleUp, idleLeft, idleRight;
    private Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    private Animation<TextureRegion> attackDown, attackUp, attackLeft, attackRight;

    private boolean isDead = false;

    public Slime2(float startX, float startY, AssetManagerHelper assets) {
        this.x = startX;
        this.y = startY;

        float fd = 0.1f;

        idleDown  = createAnimation(assets.slime2IdleSheet, 0, 6, fd);
        idleUp    = createAnimation(assets.slime2IdleSheet, 1, 6, fd);
        idleLeft  = createAnimation(assets.slime2IdleSheet, 2, 6, fd);
        idleRight = createAnimation(assets.slime2IdleSheet, 3, 6, fd);

        walkDown  = createAnimation(assets.slime2WalkSheet, 0, 8, fd);
        walkUp    = createAnimation(assets.slime2WalkSheet, 1, 8, fd);
        walkLeft  = createAnimation(assets.slime2WalkSheet, 2, 8, fd);
        walkRight = createAnimation(assets.slime2WalkSheet, 3, 8, fd);

        attackDown  = createAnimation(assets.slime2AttackSheet, 0, 11, 0.08f);
        attackUp    = createAnimation(assets.slime2AttackSheet, 1, 11, 0.08f);
        attackLeft  = createAnimation(assets.slime2AttackSheet, 2, 11, 0.08f);
        attackRight = createAnimation(assets.slime2AttackSheet, 3, 11, 0.08f);

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
        batch.draw(frame, x - 32, y - 32, 64, 64);
    }

    public Rectangle getBounds() {
        return new Rectangle(x - 22, y - 22, 44, 44);
    }

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
}
