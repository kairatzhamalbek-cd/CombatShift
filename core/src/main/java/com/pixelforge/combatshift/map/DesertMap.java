package com.pixelforge.combatshift.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;
import com.pixelforge.combatshift.entity.OrcBoss2;
import com.pixelforge.combatshift.entity.Player;
import com.pixelforge.combatshift.entity.Slime2;
import com.pixelforge.combatshift.entity.Vampire2;

public class DesertMap implements GameMapInterface {

    private final AssetManagerHelper assets;
    private final Array<Rectangle> obstacles = new Array<>();
    private final Array<String> obstacleTypes = new Array<>();

    private int currentRound = 1;
    private final int maxRounds = 3;
    private boolean inBreak = false;
    private float breakTimer = 0f;

    private final Array<Slime2> slimes2 = new Array<>();
    private final Array<Vampire2> vampires2 = new Array<>();
    private final Array<OrcBoss2> orcBosses2 = new Array<>();

    public DesertMap(AssetManagerHelper assets) {
        this.assets = assets;
        generateObstacles();
        startRound();
    }

    private void generateObstacles() {
        obstacles.clear();
        obstacleTypes.clear();

        float minDistance = 58f;

        for (int i = 0; i < 38; i++) {
            float x = MathUtils.random(100, Constants.WORLD_WIDTH - 120);
            float y = MathUtils.random(100, Constants.WORLD_HEIGHT - 120);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "tree", 22, 13, 26);
            }
        }

        for (int i = 0; i < 22; i++) {
            float x = MathUtils.random(80, Constants.WORLD_WIDTH - 100);
            float y = MathUtils.random(80, Constants.WORLD_HEIGHT - 100);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "rockMedium", 38, 37, 20);
            }
        }

        for (int i = 0; i < 18; i++) {
            float x = MathUtils.random(70, Constants.WORLD_WIDTH - 90);
            float y = MathUtils.random(70, Constants.WORLD_HEIGHT - 90);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "rockSmall", 43, 42, 16);
            }
        }

        for (int i = 0; i < 32; i++) {
            float x = MathUtils.random(60, Constants.WORLD_WIDTH - 80);
            float y = MathUtils.random(60, Constants.WORLD_HEIGHT - 80);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "bushMedium", 34, 14, 18);
            }
        }

        for (int i = 0; i < 28; i++) {
            float x = MathUtils.random(55, Constants.WORLD_WIDTH - 75);
            float y = MathUtils.random(55, Constants.WORLD_HEIGHT - 75);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "bushSmall", 28, 18, 16);
            }
        }
    }

    private boolean isFarEnough(float x, float y, float minDist) {
        for (Rectangle obs : obstacles) {
            float dx = obs.x - x;
            float dy = obs.y - y;
            if (dx * dx + dy * dy < minDist * minDist) {
                return false;
            }
        }
        return true;
    }

    private void addObstacle(float x, float y, String type, float collisionW, float collisionH, float offsetY) {
        obstacles.add(new Rectangle(x, y + offsetY, collisionW, collisionH));
        obstacleTypes.add(type);
    }

    private void startRound() {
        slimes2.clear();
        vampires2.clear();
        orcBosses2.clear();

        float spawnMin = 80f;
        float spawnMaxX = Constants.WORLD_WIDTH - 80f;
        float spawnMaxY = Constants.WORLD_HEIGHT - 80f;

        if (currentRound == 1) {
            for (int i = 0; i < 16; i++) {
                float x = MathUtils.random(spawnMin, spawnMaxX);
                float y = MathUtils.random(spawnMin, spawnMaxY);
                slimes2.add(new Slime2(x, y, assets));
            }
        } else if (currentRound == 2) {
            for (int i = 0; i < 10; i++) {
                float x = MathUtils.random(spawnMin, spawnMaxX);
                float y = MathUtils.random(spawnMin, spawnMaxY);
                slimes2.add(new Slime2(x, y, assets));
            }
            for (int i = 0; i < 6; i++) {
                float x = MathUtils.random(spawnMin, spawnMaxX);
                float y = MathUtils.random(spawnMin, spawnMaxY);
                vampires2.add(new Vampire2(x, y, assets));
            }
        } else if (currentRound == 3) {
            orcBosses2.add(new OrcBoss2(Constants.WORLD_WIDTH / 2 + 200, Constants.WORLD_HEIGHT / 2, assets));
        }
    }

    public void update(float delta, Player player) {
        if (inBreak) {
            breakTimer -= delta;
            if (breakTimer <= 0) {
                inBreak = false;
                currentRound++;
                if (currentRound <= maxRounds) {
                    startRound();
                    player.fullHeal();
                }
            }
            return;
        }

        for (Slime2 s : slimes2) if (!s.isDead()) {
            float dx = player.getX() - s.getX();
            float dy = player.getY() - s.getY();
            float len = (float) Math.sqrt(dx * dx + dy * dy);

            if (len < Constants.MOB_DETECTION_RANGE && len > 0.1f) {
                dx /= len;
                dy /= len;
                s.update(delta, dx, dy);

                if (len < Constants.SLIME_ATTACK_RANGE && s.getAttackCooldown() <= 0) {
                    player.takeDamage(s.getDamage());
                    s.setAttackCooldown(1.2f);
                }
            } else {
                s.update(delta, 0, 0);
            }
        }

        for (Vampire2 v : vampires2) if (!v.isDead()) {
            float dx = player.getX() - v.getX();
            float dy = player.getY() - v.getY();
            float len = (float) Math.sqrt(dx * dx + dy * dy);

            if (len < Constants.MOB_DETECTION_RANGE && len > 0.1f) {
                dx /= len;
                dy /= len;
                v.update(delta, dx, dy);

                if (len < Constants.VAMPIRE_ATTACK_RANGE && v.getAttackCooldown() <= 0) {
                    player.takeDamage(v.getDamage());
                    v.setAttackCooldown(1.0f);
                }
            } else {
                v.update(delta, 0, 0);
            }
        }

        for (OrcBoss2 o : orcBosses2) if (!o.isDead()) {
            float dx = player.getX() - o.getX();
            float dy = player.getY() - o.getY();
            float len = (float) Math.sqrt(dx * dx + dy * dy);

            if (len < Constants.MOB_DETECTION_RANGE && len > 0.1f) {
                dx /= len;
                dy /= len;
                o.update(delta, dx, dy);

                if (len < Constants.ORC_ATTACK_RANGE && o.getAttackCooldown() <= 0) {
                    player.takeDamage(o.getDamage());
                    o.setAttackCooldown(1.5f);
                }
            } else {
                o.update(delta, 0, 0);
            }
        }

        separateMobs();
        checkRoundEnd();
    }

    private void separateMobs() {
        float minDist = 48f;

        for (int i = 0; i < slimes2.size; i++) {
            for (int j = i + 1; j < slimes2.size; j++) {
                Slime2 a = slimes2.get(i);
                Slime2 b = slimes2.get(j);
                if (!a.isDead() && !b.isDead()) {
                    float dx = a.getX() - b.getX();
                    float dy = a.getY() - b.getY();
                    float distSq = dx * dx + dy * dy;
                    if (distSq < minDist * minDist && distSq > 0.1f) {
                        float dist = (float) Math.sqrt(distSq);
                        float push = (minDist - dist) / dist * 0.45f;
                        a.setPosition(a.getX() + dx * push, a.getY() + dy * push);
                        b.setPosition(b.getX() - dx * push, b.getY() - dy * push);
                    }
                }
            }
        }

        for (int i = 0; i < vampires2.size; i++) {
            for (int j = i + 1; j < vampires2.size; j++) {
                Vampire2 a = vampires2.get(i);
                Vampire2 b = vampires2.get(j);
                if (!a.isDead() && !b.isDead()) {
                    float dx = a.getX() - b.getX();
                    float dy = a.getY() - b.getY();
                    float distSq = dx * dx + dy * dy;
                    if (distSq < minDist * minDist && distSq > 0.1f) {
                        float dist = (float) Math.sqrt(distSq);
                        float push = (minDist - dist) / dist * 0.45f;
                        a.setPosition(a.getX() + dx * push, a.getY() + dy * push);
                        b.setPosition(b.getX() - dx * push, b.getY() - dy * push);
                    }
                }
            }
        }

        for (int i = 0; i < orcBosses2.size; i++) {
            for (int j = i + 1; j < orcBosses2.size; j++) {
                OrcBoss2 a = orcBosses2.get(i);
                OrcBoss2 b = orcBosses2.get(j);
                if (!a.isDead() && !b.isDead()) {
                    float dx = a.getX() - b.getX();
                    float dy = a.getY() - b.getY();
                    float distSq = dx * dx + dy * dy;
                    if (distSq < minDist * minDist && distSq > 0.1f) {
                        float dist = (float) Math.sqrt(distSq);
                        float push = (minDist - dist) / dist * 0.45f;
                        a.setPosition(a.getX() + dx * push, a.getY() + dy * push);
                        b.setPosition(b.getX() - dx * push, b.getY() - dy * push);
                    }
                }
            }
        }
    }

    public void checkPlayerAttack(Player player) {
        float attackRadius = 45f;
        float px = player.getX();
        float py = player.getY();

        for (Slime2 s : slimes2) if (!s.isDead()) {
            float dx = s.getX() - px;
            float dy = s.getY() - py;
            if (dx * dx + dy * dy < attackRadius * attackRadius) {
                s.takeDamage(Constants.PLAYER_ATTACK_DAMAGE);
            }
        }
        for (Vampire2 v : vampires2) if (!v.isDead()) {
            float dx = v.getX() - px;
            float dy = v.getY() - py;
            if (dx * dx + dy * dy < attackRadius * attackRadius) {
                v.takeDamage(Constants.PLAYER_ATTACK_DAMAGE);
            }
        }
        for (OrcBoss2 o : orcBosses2) if (!o.isDead()) {
            float dx = o.getX() - px;
            float dy = o.getY() - py;
            if (dx * dx + dy * dy < attackRadius * attackRadius) {
                o.takeDamage(Constants.PLAYER_ATTACK_DAMAGE);
            }
        }
    }

    private void checkRoundEnd() {
        if (inBreak) return;
        boolean allDead = true;
        for (Slime2 s : slimes2) if (!s.isDead()) allDead = false;
        for (Vampire2 v : vampires2) if (!v.isDead()) allDead = false;
        for (OrcBoss2 o : orcBosses2) if (!o.isDead()) allDead = false;

        if (allDead) {
            inBreak = true;
            breakTimer = 10f;
        }
    }

    public int getCurrentRound() { return currentRound; }
    public boolean isInBreak() { return inBreak; }
    public float getBreakTimeLeft() { return breakTimer; }
    public int getAliveMobCount() {
        int count = 0;
        for (Slime2 s : slimes2) if (!s.isDead()) count++;
        for (Vampire2 v : vampires2) if (!v.isDead()) count++;
        for (OrcBoss2 o : orcBosses2) if (!o.isDead()) count++;
        return count;
    }

    public Array<Slime2> getSlimes2() { return slimes2; }
    public Array<Vampire2> getVampires2() { return vampires2; }
    public Array<OrcBoss2> getOrcBosses2() { return orcBosses2; }

    public Array<Rectangle> getObstacles() { return obstacles; }
    public Array<String> getObstacleTypes() { return obstacleTypes; }

    public void drawGround(SpriteBatch batch) {
        float tileSize = 100f;
        for (int x = 0; x < Constants.WORLD_WIDTH; x += tileSize) {
            for (int y = 0; y < Constants.WORLD_HEIGHT; y += tileSize) {
                batch.draw(assets.desertGround, x, y, tileSize, tileSize);
            }
        }
    }

    public void draw(SpriteBatch batch) {
        drawGround(batch);

        for (int i = 0; i < obstacles.size; i++) {
            Rectangle rect = obstacles.get(i);
            String type = obstacleTypes.get(i);

            switch (type) {
                case "tree":
                    batch.draw(assets.desertTreeMedium, rect.x - 50, rect.y - 15, 400, 400);
                    break;
                case "rockMedium":
                    batch.draw(assets.desertRockMedium, rect.x - 18, rect.y - 14, 55, 50);
                    break;
                case "rockSmall":
                    batch.draw(assets.desertRockSmall, rect.x - 17, rect.y - 10, 100, 100);
                    break;
                case "bushMedium":
                    batch.draw(assets.desertBushMedium, rect.x - 16, rect.y - 14, 80, 80);
                    break;
                case "bushSmall":
                    batch.draw(assets.desertBushSmall, rect.x - 12, rect.y - 10, 80, 80);
                    break;
            }
        }

        for (Slime2 s : slimes2) if (!s.isDead()) s.render(batch);
        for (Vampire2 v : vampires2) if (!v.isDead()) v.render(batch);
        for (OrcBoss2 o : orcBosses2) if (!o.isDead()) o.render(batch);
    }

    public boolean collides(Rectangle bounds) {
        for (Rectangle obstacle : obstacles) {
            if (obstacle.overlaps(bounds)) return true;
        }
        if (bounds.x < 0 || bounds.y < 0 ||
            bounds.x + bounds.width > Constants.WORLD_WIDTH ||
            bounds.y + bounds.height > Constants.WORLD_HEIGHT) {
            return true;
        }
        return false;
    }

    public void dispose() {}
}
