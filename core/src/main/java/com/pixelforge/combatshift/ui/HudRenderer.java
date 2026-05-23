package com.pixelforge.combatshift.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pixelforge.combatshift.entity.Player;

public class HudRenderer {
    private ShapeRenderer shape;
    private BitmapFont font;

    public HudRenderer() {
        shape = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    public void render(SpriteBatch batch, Player player, String locationName, int round, float screenWidth) {
        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Filled);

        float barW = 320f, barH = 26f;
        float startX = 40f;

        // HP Bar (фиолетовый)
        float hpY = 680f;
        shape.setColor(0.1f, 0.1f, 0.1f, 1f);
        shape.rect(startX, hpY, barW, barH);
        shape.setColor(0.7f, 0.2f, 0.9f, 1f); // фиолетовый
        shape.rect(startX, hpY, barW * player.getHpRatio(), barH);

        // Stamina Bar (жёлтый)
        float stamY = hpY - 40f;
        shape.setColor(0.1f, 0.1f, 0.1f, 1f);
        shape.rect(startX, stamY, barW, barH);
        shape.setColor(1f, 0.85f, 0.1f, 1f); // жёлтый
        shape.rect(startX, stamY, barW * player.getStaminaRatio(), barH);

        shape.end();

        batch.begin();
        font.getData().setScale(1.3f);
        font.setColor(Color.WHITE);
        font.draw(batch, "HP", startX - 45, hpY + 20);
        font.draw(batch, "ENERGY", startX - 45, stamY + 20);

        // Location + Round
        font.getData().setScale(2.2f);
        font.setColor(Color.CYAN);
        font.draw(batch, locationName + " — ROUND " + round,
            screenWidth / 2 - 160, 700);

        batch.end();
    }

    public void dispose() {
        shape.dispose();
        font.dispose();
    }
}
