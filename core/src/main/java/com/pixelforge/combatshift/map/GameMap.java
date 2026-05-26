package com.pixelforge.combatshift.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pixelforge.combatshift.Constants;
import com.pixelforge.combatshift.assets.AssetManagerHelper;
import com.pixelforge.combatshift.entity.OrcBoss;
import com.pixelforge.combatshift.entity.Player;
import com.pixelforge.combatshift.entity.Slime;
import com.pixelforge.combatshift.entity.Vampire;

public class GameMap implements GameMapInterface {

    private final AssetManagerHelper assets;
    private final Array<Rectangle> obstacles = new Array<>();
    private final Array<String> obstacleTypes = new Array<>();

    private com.badlogic.gdx.audio.Music music;

    private int currentRound = 1;
    private final int maxRounds = 3;
    private boolean inBreak = false;
    private float breakTimer = 0f;

    private final Array<Slime> slimes = new Array<>();
    private final Array<Vampire> vampires = new Array<>();
    private final Array<OrcBoss> orcBosses = new Array<>();

    public GameMap(AssetManagerHelper assets) {
        this.assets = assets;
        generateObstacles();
        this.music = assets.forestMusic;
        startRound();
    }

    private void generateObstacles() {
        obstacles.clear();
        obstacleTypes.clear();

        float minDistance = 55f;

        for (int i = 0; i < 35; i++) {
            float x = MathUtils.random(100, Constants.WORLD_WIDTH - 120);
            float y = MathUtils.random(100, Constants.WORLD_HEIGHT - 120);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "tree", 20, 14, 28);
            }
        }
        for (int i = 0; i < 28; i++) {
            float x = MathUtils.random(80, Constants.WORLD_WIDTH - 100);
            float y = MathUtils.random(80, Constants.WORLD_HEIGHT - 100);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "rock", 20, 16, 22);
            }
        }
        for (int i = 0; i < 32; i++) {
            float x = MathUtils.random(70, Constants.WORLD_WIDTH - 90);
            float y = MathUtils.random(70, Constants.WORLD_HEIGHT - 90);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "bushMedium", 22, 14, 20);
            }
        }
        for (int i = 0; i < 22; i++) {
            float x = MathUtils.random(80, Constants.WORLD_WIDTH - 100);
            float y = MathUtils.random(80, Constants.WORLD_HEIGHT - 100);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "bushLarge", 35, 16, 24);
            }
        }
        for (int i = 0; i < 18; i++) {
            float x = MathUtils.random(60, Constants.WORLD_WIDTH - 80);
            float y = MathUtils.random(60, Constants.WORLD_HEIGHT - 80);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "stumpShort", 16, 12, 18);
            }
        }
        for (int i = 0; i < 12; i++) {
            float x = MathUtils.random(70, Constants.WORLD_WIDTH - 90);
            float y = MathUtils.random(70, Constants.WORLD_HEIGHT - 90);
            if (isFarEnough(x, y, minDistance)) {
                addObstacle(x, y, "stumpTall", 14, 12, 20);
            }
        }
    }

    private boolean isFarEnough(float x, float y, float minDist) {
        for (Rectangle obs : obstacles) {
            float dx = obs.x - x;
            float dy = obs.y - y;
            if (dx * dx + dy * dy < minDist * minDist) return false;
        }
        return true;
    }

    private void addObstacle(float x, float y, String type, float collisionW, float collisionH, float offsetY) {
        obstacles.add(new Rectangle(x, y + offsetY, collisionW, collisionH));
        obstacleTypes.add(type);
    }

    private void startRound() {
        slimes.clear();
        vampires.clear();
        orcBosses.clear();

        float spawnMin = 80f;
        float spawnMaxX = Constants.WORLD_WIDTH - 80f;
        float spawnMaxY = Constants.WORLD_HEIGHT - 80f;

        if (currentRound == 1) {
            for (int i = 0; i < 16; i++) {
                float x = MathUtils.random(spawnMin, spawnMaxX);
                float y = MathUtils.random(spawnMin, spawnMaxY);
                slimes.add(new Slime(x, y, assets));
            }
        } else if (currentRound == 2) {
            for (int i = 0; i < 10; i++) {
                float x = MathUtils.random(spawnMin, spawnMaxX);
                float y = MathUtils.random(spawnMin, spawnMaxY);
                slimes.add(new Slime(x, y, assets));
            }
            for (int i = 0; i < 6; i++) {
                float x = MathUtils.random(spawnMin, spawnMaxX);
                float y = MathUtils.random(spawnMin, spawnMaxY);
                vampires.add(new Vampire(x, y, assets));
            }
        } else if (currentRound == 3) {
            orcBosses.add(new OrcBoss(Constants.WORLD_WIDTH / 2 + 200, Constants.WORLD_HEIGHT / 2, assets));
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

        for (Slime s : slimes) if (!s.isDead()) {
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

        for (Vampire v : vampires) if (!v.isDead()) {
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

        for (OrcBoss o : orcBosses) if (!o.isDead()) {
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

        for (int i = 0; i < slimes.size; i++) {
            for (int j = i + 1; j < slimes.size; j++) {
                Slime a = slimes.get(i);
                Slime b = slimes.get(j);
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

        for (int i = 0; i < vampires.size; i++) {
            for (int j = i + 1; j < vampires.size; j++) {
                Vampire a = vampires.get(i);
                Vampire b = vampires.get(j);
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

        for (int i = 0; i < orcBosses.size; i++) {
            for (int j = i + 1; j < orcBosses.size; j++) {
                OrcBoss a = orcBosses.get(i);
                OrcBoss b = orcBosses.get(j);
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

        for (Slime s : slimes) if (!s.isDead()) {
            float dx = s.getX() - px;
            float dy = s.getY() - py;
            if (dx * dx + dy * dy < attackRadius * attackRadius) {
                s.takeDamage(Constants.PLAYER_ATTACK_DAMAGE);   // ← Используем константу
            }
        }
        for (Vampire v : vampires) if (!v.isDead()) {
            float dx = v.getX() - px;
            float dy = v.getY() - py;
            if (dx * dx + dy * dy < attackRadius * attackRadius) {
                v.takeDamage(Constants.PLAYER_ATTACK_DAMAGE);
            }
        }
        for (OrcBoss o : orcBosses) if (!o.isDead()) {
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
        for (Slime s : slimes) if (!s.isDead()) allDead = false;
        for (Vampire v : vampires) if (!v.isDead()) allDead = false;
        for (OrcBoss o : orcBosses) if (!o.isDead()) allDead = false;

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
        for (Slime s : slimes) if (!s.isDead()) count++;
        for (Vampire v : vampires) if (!v.isDead()) count++;
        for (OrcBoss o : orcBosses) if (!o.isDead()) count++;
        return count;
    }

    public Array<Slime> getSlimes() { return slimes; }
    public Array<Vampire> getVampires() { return vampires; }
    public Array<OrcBoss> getOrcBosses() { return orcBosses; }

    public void playMusic() {
        if (music != null && !music.isPlaying()) music.play();
    }

    public void stopMusic() {
        if (music != null && music.isPlaying()) music.stop();
    }

    public void drawGround(SpriteBatch batch) {
        float tileSize = 160f;
        for (int x = 0; x < Constants.WORLD_WIDTH; x += tileSize) {
            for (int y = 0; y < Constants.WORLD_HEIGHT; y += tileSize) {
                batch.draw(assets.groundTexture, x, y, tileSize, tileSize);
            }
        }
    }

    public void draw(SpriteBatch batch) {
        drawGround(batch);

        for (int i = 0; i < obstacles.size; i++) {
            Rectangle rect = obstacles.get(i);
            String type = obstacleTypes.get(i);

            switch (type) {
                case "tree":    batch.draw(assets.treeMedium, rect.x - 50, rect.y - 15, 128, 128); break;
                case "rock":    batch.draw(assets.rock, rect.x - 12, rect.y - 10, 40, 40); break;
                case "bushMedium": batch.draw(assets.bushMedium, rect.x - 18, rect.y - 12, 45, 45); break;
                case "bushLarge":  batch.draw(assets.bushLarge, rect.x - 14, rect.y - 16, 60, 55); break;
                case "stumpShort": batch.draw(assets.stumpShort, rect.x - 14, rect.y - 8, 35, 35); break;
                case "stumpTall":  batch.draw(assets.stumpTall, rect.x - 12, rect.y - 6, 38, 42); break;
            }
        }

        for (Slime s : slimes) if (!s.isDead()) s.render(batch);
        for (Vampire v : vampires) if (!v.isDead()) v.render(batch);
        for (OrcBoss o : orcBosses) if (!o.isDead()) o.render(batch);
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

    public void dispose() {
        stopMusic();
    }

    public Array<Rectangle> getObstacles() { return obstacles; }
    public Array<String> getObstacleTypes() { return obstacleTypes; }
}
