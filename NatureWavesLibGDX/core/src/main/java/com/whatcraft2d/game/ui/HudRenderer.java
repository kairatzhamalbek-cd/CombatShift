package com.whatcraft2d.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.whatcraft2d.game.combat.Inventory;
import com.whatcraft2d.game.combat.Weapon;
import com.whatcraft2d.game.entities.Player;
import com.whatcraft2d.game.systems.WaveManager;
import com.whatcraft2d.game.systems.WaveState;
import com.whatcraft2d.game.assets.AssetManagerHelper;

public class HudRenderer {
    private final Color bg = new Color(0f, 0f, 0f, 0.55f);
    private final Color hp = new Color(0.82f, 0.12f, 0.12f, 1f);
    private final Color stamina = new Color(0.16f, 0.56f, 0.96f, 1f);
    private final Color slot = new Color(0.09f, 0.09f, 0.09f, 0.72f);
    private final Color selected = new Color(0.88f, 0.79f, 0.28f, 0.85f);

    public void draw(SpriteBatch batch,
                     ShapeRenderer shapes,
                     OrthographicCamera uiCamera,
                     AssetManagerHelper assets,
                     Player player,
                     WaveManager waves) {
        shapes.setProjectionMatrix(uiCamera.combined);
        batch.setProjectionMatrix(uiCamera.combined);

        float screenW = uiCamera.viewportWidth;
        float screenH = uiCamera.viewportHeight;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        drawBar(shapes, 24f, screenH - 34f, 220f, 16f, player.getHp() / player.getMaxHp(), bg, hp);
        drawBar(shapes, 24f, screenH - 58f, 220f, 13f, player.getStamina() / player.getMaxStamina(), bg, stamina);
        drawInventoryBoxes(shapes, screenW, player.getInventory());
        shapes.end();

        batch.begin();
        assets.font.setColor(Color.WHITE);
        assets.font.draw(batch, "HP", 26f, screenH - 20f);
        assets.font.draw(batch, "ST", 26f, screenH - 45f);
        assets.font.draw(batch, "WASD move | LMB attack | RMB shield | SPACE dodge | 1-5 weapon", 24f, 24f);

        Weapon weapon = player.selectedWeapon();
        String weaponName = weapon == null ? "None" : weapon.getName();
        String stateText = waveText(waves);
        assets.font.draw(batch, "Weapon: " + weaponName, 270f, screenH - 22f);
        assets.font.draw(batch, "Round: " + Math.max(1, waves.getCurrentRound()) + "/3  " + stateText, 270f, screenH - 44f);
        assets.font.draw(batch, waves.getLastRewardText(), 270f, screenH - 66f);
        if (player.isInWater()) {
            assets.font.setColor(new Color(0.55f, 0.85f, 1f, 1f));
            assets.font.draw(batch, "Water: movement slowed, attack disabled", 270f, screenH - 88f);
            assets.font.setColor(Color.WHITE);
        }
        drawInventoryText(batch, assets, screenW, player.getInventory());
        batch.end();
    }

    private String waveText(WaveManager waves) {
        if (waves.getState() == WaveState.LOCATION_CLEARED) {
            return "Location cleared";
        }
        if (waves.getState() == WaveState.WAITING) {
            return "Next wave in " + Math.max(0, (int) waves.getTimer() + 1) + "s";
        }
        return "Enemies alive: " + waves.getMobs().size;
    }

    private void drawBar(ShapeRenderer shapes, float x, float y, float w, float h, float ratio, Color background, Color fill) {
        ratio = Math.max(0f, Math.min(1f, ratio));
        shapes.setColor(background);
        shapes.rect(x, y, w, h);
        shapes.setColor(fill);
        shapes.rect(x, y, w * ratio, h);
    }

    private void drawInventoryBoxes(ShapeRenderer shapes, float screenW, Inventory inventory) {
        float startX = screenW * 0.5f - 5f * 44f * 0.5f;
        float y = 38f;
        for (int i = 0; i < 5; i++) {
            shapes.setColor(i == inventory.getSelectedIndex() ? selected : slot);
            shapes.rect(startX + i * 44f, y, 38f, 38f);
        }
    }

    private void drawInventoryText(SpriteBatch batch, AssetManagerHelper assets, float screenW, Inventory inventory) {
        float startX = screenW * 0.5f - 5f * 44f * 0.5f;
        float y = 63f;
        for (int i = 0; i < 5; i++) {
            String label = String.valueOf(i + 1);
            if (i < inventory.getWeapons().size) {
                Weapon weapon = inventory.getWeapons().get(i);
                label += " " + shortName(weapon.getName());
            }
            assets.font.draw(batch, label, startX + i * 44f + 4f, y);
        }
    }

    private String shortName(String name) {
        if (name.length() <= 5) return name;
        String[] parts = name.split(" ");
        if (parts.length == 1) return name.substring(0, Math.min(5, name.length()));
        return parts[0].substring(0, Math.min(1, parts[0].length())) + parts[1].substring(0, Math.min(3, parts[1].length()));
    }
}
