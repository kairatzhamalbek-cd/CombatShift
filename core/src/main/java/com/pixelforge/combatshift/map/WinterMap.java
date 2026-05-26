package com.pixelforge.combatshift.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;
import com.pixelforge.combatshift.entity.OrcBoss3;
import com.pixelforge.combatshift.entity.Player;
import com.pixelforge.combatshift.entity.Slime3;
import com.pixelforge.combatshift.entity.Vampire3;

public class WinterMap implements GameMapInterface {

    private final AssetManagerHelper assets;
    private final Array<Rectangle> obstacles = new Array<>();
    private final Array<String> obstacleTypes = new Array<>();

    private int currentRound = 1;
    private final int maxRounds = 3;
    private boolean inBreak = false;
    private float breakTimer = 0f;

    private final Array<Slime3> slimes3 = new Array<>();
    private final Array<Vampire3> vampires3 = new Array<>();
    private final Array<OrcBoss3> orcBosses3 = new Array<>();

    public WinterMap(AssetManagerHelper assets) {
        this.assets = assets;
        generateObstacles();
        startRound();
    }

    private void generateObstacles() {
        obstacles.clear();
        obstacleTypes.clear();

        float minDistance = 60f;

        for (int i = 0; i < 35; i++) {
            float x = MathUtils.random(100, Constants.WORLD_WIDTH - 120);
            float y = MathUtils.random(100, Constants.WORLD_HEIGHT - 120);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "tree", 24, 16, 30);
            }
        }

        for (int i = 0; i < 25; i++) {
            float x = MathUtils.random(80, Constants.WORLD_WIDTH - 100);
            float y = MathUtils.random(80, Constants.WORLD_HEIGHT - 100);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "rockMedium", 30, 20, 22);
            }
        }

        for (int i = 0; i < 20; i++) {
            float x = MathUtils.random(70, Constants.WORLD_WIDTH - 90);
            float y = MathUtils.random(70, Constants.WORLD_HEIGHT - 90);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "rockSmall", 44, 24, 18);
            }
        }

        for (int i = 0; i < 30; i++) {
            float x = MathUtils.random(65, Constants.WORLD_WIDTH - 85);
            float y = MathUtils.random(65, Constants.WORLD_HEIGHT - 85);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "bushMedium", 44, 20, 20);
            }
        }

        for (int i = 0; i < 28; i++) {
            float x = MathUtils.random(55, Constants.WORLD_WIDTH - 75);
            float y = MathUtils.random(55, Constants.WORLD_HEIGHT - 75);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "bushSmall", 45, 20, 18);
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
        slimes3.clear();
        vampires3.clear();
        orcBosses3.clear();

        float spawnMin = 80f;
        float spawnMaxX = Constants.WORLD_WIDTH - 80f;
        float spawnMaxY = Constants.WORLD_HEIGHT - 80f;

        if (currentRound == 1) {
            for (int i = 0; i < 16; i++) {
                float x = MathUtils.random(spawnMin, spawnMaxX);
                float y = MathUtils.random(spawnMin, spawnMaxY);
                slimes3.add(new Slime3(x, y, assets));
            }
        } else if (currentRound == 2) {
            for (int i = 0; i < 10; i++) {
                float x = MathUtils.random(spawnMin, spawnMaxX);
                float y = MathUtils.random(spawnMin, spawnMaxY);
                slimes3.add(new Slime3(x, y, assets));
            }
            for (int i = 0; i < 6; i++) {
                float x = MathUtils.random(spawnMin, spawnMaxX);
                float y = MathUtils.random(spawnMin, spawnMaxY);
                vampires3.add(new Vampire3(x, y, assets));
            }
        } else if (currentRound == 3) {
            orcBosses3.add(new OrcBoss3(Constants.WORLD_WIDTH / 2 + 200, Constants.WORLD_HEIGHT / 2, assets));
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

        for (Slime3 s : slimes3) if (!s.isDead()) {
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

        for (Vampire3 v : vampires3) if (!v.isDead()) {
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

        for (OrcBoss3 o : orcBosses3) if (!o.isDead()) {
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

        for (int i = 0; i < slimes3.size; i++) {
            for (int j = i + 1; j < slimes3.size; j++) {
                Slime3 a = slimes3.get(i);
                Slime3 b = slimes3.get(j);
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

        for (int i = 0; i < vampires3.size; i++) {
            for (int j = i + 1; j < vampires3.size; j++) {
                Vampire3 a = vampires3.get(i);
                Vampire3 b = vampires3.get(j);
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

        for (int i = 0; i < orcBosses3.size; i++) {
            for (int j = i + 1; j < orcBosses3.size; j++) {
                OrcBoss3 a = orcBosses3.get(i);
                OrcBoss3 b = orcBosses3.get(j);
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

        for (Slime3 s : slimes3) if (!s.isDead()) {
            float dx = s.getX() - px;
            float dy = s.getY() - py;
            if (dx * dx + dy * dy < attackRadius * attackRadius) {
                s.takeDamage(Constants.PLAYER_ATTACK_DAMAGE);
            }
        }
        for (Vampire3 v : vampires3) if (!v.isDead()) {
            float dx = v.getX() - px;
            float dy = v.getY() - py;
            if (dx * dx + dy * dy < attackRadius * attackRadius) {
                v.takeDamage(Constants.PLAYER_ATTACK_DAMAGE);
            }
        }
        for (OrcBoss3 o : orcBosses3) if (!o.isDead()) {
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
        for (Slime3 s : slimes3) if (!s.isDead()) allDead = false;
        for (Vampire3 v : vampires3) if (!v.isDead()) allDead = false;
        for (OrcBoss3 o : orcBosses3) if (!o.isDead()) allDead = false;

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
        for (Slime3 s : slimes3) if (!s.isDead()) count++;
        for (Vampire3 v : vampires3) if (!v.isDead()) count++;
        for (OrcBoss3 o : orcBosses3) if (!o.isDead()) count++;
        return count;
    }

    public Array<Slime3> getSlimes3() { return slimes3; }
    public Array<Vampire3> getVampires3() { return vampires3; }
    public Array<OrcBoss3> getOrcBosses3() { return orcBosses3; }

    public Array<Rectangle> getObstacles() { return obstacles; }
    public Array<String> getObstacleTypes() { return obstacleTypes; }

    public void drawGround(SpriteBatch batch) {
        float tileSize = 90f;
        for (int x = 0; x < Constants.WORLD_WIDTH; x += tileSize) {
            for (int y = 0; y < Constants.WORLD_HEIGHT; y += tileSize) {
                batch.draw(assets.winterGround, x, y, tileSize, tileSize);
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
                    batch.draw(assets.winterTree, rect.x - 55, rect.y - 25, 140, 140);
                    break;
                case "rockMedium":
                    batch.draw(assets.winterRockMedium, rect.x - 22, rect.y - 18, 65, 60);
                    break;
                case "rockSmall":
                    batch.draw(assets.winterRockSmall, rect.x - 15, rect.y - 12, 48, 45);
                    break;
                case "bushMedium":
                    batch.draw(assets.winterBushMedium, rect.x - 18, rect.y - 16, 55, 52);
                    break;
                case "bushSmall":
                    batch.draw(assets.winterBushSmall, rect.x - 14, rect.y - 12, 45, 42);
                    break;
            }
        }

        for (Slime3 s : slimes3) if (!s.isDead()) s.render(batch);
        for (Vampire3 v : vampires3) if (!v.isDead()) v.render(batch);
        for (OrcBoss3 o : orcBosses3) if (!o.isDead()) o.render(batch);
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
