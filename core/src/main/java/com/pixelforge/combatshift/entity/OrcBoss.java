package com.pixelforge.combatshift.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;

public class OrcBoss {
    private float x, y;
    private float hp = 220f;
    private final float maxHp = 220f;
    private final float speed = 140f;
    private final float damage = 34f;
    private float scale = Constants.PLAYER_SIZE * 3f;
    private float attackCooldown = 0f;

    private Animation<TextureRegion> currentAnimation;
    private float stateTime = 0f;

    private boolean isFatigued = false;
    private float fatigueTimer = 5f;

    private Animation<TextureRegion> idleDown, idleUp, idleLeft, idleRight;
    private Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    private Animation<TextureRegion> attackDown, attackUp, attackLeft, attackRight;

    private boolean isDead = false;

    private final AssetManagerHelper assets;

    public OrcBoss(float startX, float startY, AssetManagerHelper assets) {
        this.x = startX;
        this.y = startY;
        this.assets = assets;

        float fd = 0.1f;

        idleDown = createAnimation(assets.orc1IdleSheet, 0, 4, fd);
        idleUp = createAnimation(assets.orc1IdleSheet, 1, 4, fd);
        idleLeft = createAnimation(assets.orc1IdleSheet, 2, 4, fd);
        idleRight = createAnimation(assets.orc1IdleSheet, 3, 4, fd);

        walkDown = createAnimation(assets.orc1WalkSheet, 0, 6, fd);
        walkUp = createAnimation(assets.orc1WalkSheet, 1, 6, fd);
        walkLeft = createAnimation(assets.orc1WalkSheet, 2, 6, fd);
        walkRight = createAnimation(assets.orc1WalkSheet, 3, 6, fd);

        attackDown = createAnimation(assets.orc1AttackSheet, 0, 8, 0.08f);
        attackUp = createAnimation(assets.orc1AttackSheet, 1, 8, 0.08f);
        attackLeft = createAnimation(assets.orc1AttackSheet, 2, 8, 0.08f);
        attackRight = createAnimation(assets.orc1AttackSheet, 3, 8, 0.08f);

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
        fatigueTimer -= delta;

        if (fatigueTimer <= 0) {
            isFatigued = !isFatigued;
            fatigueTimer = isFatigued ? 2f : 5f;
        }

        float currentSpeed = isFatigued ? 0 : speed;

        if (!isFatigued && (dx != 0 || dy != 0)) {
            if (Math.abs(dy) > Math.abs(dx)) {
                currentAnimation = (dy > 0) ? walkUp : walkDown;
            } else {
                currentAnimation = (dx > 0) ? walkRight : walkLeft;
            }
            x += dx * currentSpeed * delta;
            y += dy * currentSpeed * delta;
        } else {
            currentAnimation = idleDown;
        }
    }

    public void render(SpriteBatch batch) {
        if (isDead) return;
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        float size = 64 * Constants.ORC_SCALE;
        batch.draw(frame, x - size / 2f, y - size / 2f, size, size);    }

    public Rectangle getBounds() {
        float size = 64 * Constants.ORC_SCALE;
        return new Rectangle(x - size / 2f + 10, y - size / 2f + 10, size - 20, size - 20);    }

    public void takeDamage(float dmg) {
        hp -= dmg;
        if (hp <= 0) isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getDamage() {
        return damage;
    }

    public float getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(float time) {
        attackCooldown = time;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getHp() {
        return hp;
    }

    public float getMaxHp() {
        return maxHp;
    }
}
