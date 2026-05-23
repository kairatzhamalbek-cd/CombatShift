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

    // ====================== ТВОЙ СТАРЫЙ КОД (без изменений) ======================
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

        if (currentRound == 1) {
            for (int i = 0; i < 16; i++) {
                float x = MathUtils.random(150, Constants.WORLD_WIDTH - 150);
                float y = MathUtils.random(150, Constants.WORLD_HEIGHT - 150);
                slimes.add(new Slime(x, y, assets));
            }
        } else if (currentRound == 2) {
            for (int i = 0; i < 10; i++) {
                float x = MathUtils.random(150, Constants.WORLD_WIDTH - 150);
                float y = MathUtils.random(150, Constants.WORLD_HEIGHT - 150);
                slimes.add(new Slime(x, y, assets));
            }
            for (int i = 0; i < 6; i++) {
                float x = MathUtils.random(150, Constants.WORLD_WIDTH - 150);
                float y = MathUtils.random(150, Constants.WORLD_HEIGHT - 150);
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

        float attackRange = 55f;   // расстояние, с которого моб может атаковать

        for (Slime s : slimes) if (!s.isDead()) {
            float dx = player.getX() - s.getX();
            float dy = player.getY() - s.getY();
            float len = (float) Math.sqrt(dx * dx + dy * dy);
            if (len > 0) { dx /= len; dy /= len; }
            s.update(delta, dx, dy);

            // Атака только если в радиусе и cooldown прошёл
            if (len < attackRange && s.getAttackCooldown() <= 0) {
                player.takeDamage(s.getDamage());
                s.setAttackCooldown(1.2f);
            }
        }

        for (Vampire v : vampires) if (!v.isDead()) {
            float dx = player.getX() - v.getX();
            float dy = player.getY() - v.getY();
            float len = (float) Math.sqrt(dx * dx + dy * dy);
            if (len > 0) { dx /= len; dy /= len; }
            v.update(delta, dx, dy);

            if (len < attackRange && v.getAttackCooldown() <= 0) {
                player.takeDamage(v.getDamage());
                v.setAttackCooldown(1.0f);
            }
        }

        for (OrcBoss o : orcBosses) if (!o.isDead()) {
            float dx = player.getX() - o.getX();
            float dy = player.getY() - o.getY();
            float len = (float) Math.sqrt(dx * dx + dy * dy);
            if (len > 0) { dx /= len; dy /= len; }
            o.update(delta, dx, dy);

            if (len < attackRange && o.getAttackCooldown() <= 0) {
                player.takeDamage(o.getDamage());
                o.setAttackCooldown(1.5f);
            }
        }

        // Атака игрока (ЛКМ)
        if (player.isAttacking()) {
            checkPlayerAttack(player);
        }

        checkRoundEnd();
    }

    private void checkPlayerAttack(Player player) {
        float attackRadius = 80f;
        float px = player.getX();
        float py = player.getY();

        for (Slime s : slimes) if (!s.isDead()) {
            float dx = s.getX() - px;
            float dy = s.getY() - py;
            if (dx * dx + dy * dy < attackRadius * attackRadius) {
                s.takeDamage(14);
            }
        }
        for (Vampire v : vampires) if (!v.isDead()) {
            float dx = v.getX() - px;
            float dy = v.getY() - py;
            if (dx * dx + dy * dy < attackRadius * attackRadius) {
                v.takeDamage(14);
            }
        }
        for (OrcBoss o : orcBosses) if (!o.isDead()) {
            float dx = o.getX() - px;
            float dy = o.getY() - py;
            if (dx * dx + dy * dy < attackRadius * attackRadius) {
                o.takeDamage(14);
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

    // Методы HUD (без изменений)
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
