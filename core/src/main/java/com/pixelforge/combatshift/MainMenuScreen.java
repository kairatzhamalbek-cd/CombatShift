package com.pixelforge.combatshift;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen implements Screen {

    private final MainGame game;
    private Stage stage;
    private Table table;
    private Table settingsTable;
    private boolean settingsOpen = false;

    private SpriteBatch batch;
    private Texture menuBackground;

    private BitmapFont titleFont;
    private BitmapFont buttonFont;

    public MainMenuScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Большой viewport для комфортного отображения
        stage = new Stage(new com.badlogic.gdx.utils.viewport.StretchViewport(1280, 720));
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();

        // Загружаем твою картинку
        menuBackground = new Texture(Gdx.files.internal("menu.png"));
        menuBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        createFonts();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        // Кнопки
        TextButton newGameBtn = new TextButton("NEW GAME", createButtonStyle(new Color(0.1f, 0.78f, 0.1f, 1)));
        TextButton settingsBtn = new TextButton("SETTINGS", createButtonStyle(new Color(0.05f, 0.45f, 0.95f, 1)));
        TextButton exitBtn = new TextButton("EXIT", createButtonStyle(new Color(0.85f, 0.1f, 0.1f, 1)));

        newGameBtn.getLabel().setFontScale(1.8f);
        settingsBtn.getLabel().setFontScale(1.8f);
        exitBtn.getLabel().setFontScale(1.8f);

        newGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        settingsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showSettings();
            }
        });

        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // ==================== РАЗМЕЩЕНИЕ КНОПОК ====================
        table.add().expandY().padTop(100).row();                   // ← Пустая ячейка, которая толкает кнопки вниз


        table.add(newGameBtn)
            .width(460)      // ← ширина
            .height(92)      // ← высота
            .pad(10)         // ← отступы
            .row();

        table.add(settingsBtn)
            .width(460)
            .height(92)
            .pad(10)
            .row();

        table.add(exitBtn)
            .width(460)
            .height(92)
            .pad(10);

        createSettingsTable();
    }

    private void createFonts() {
        titleFont = new BitmapFont();
        buttonFont = new BitmapFont();
    }

    private TextButton.TextButtonStyle createButtonStyle(Color color) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = buttonFont;
        style.fontColor = Color.WHITE;

        Pixmap pix = new Pixmap(420, 88, Pixmap.Format.RGBA8888);
        pix.setColor(color);
        pix.fill();
        style.up = new TextureRegionDrawable(new Texture(pix));

        pix.setColor(color.r + 0.22f, color.g + 0.22f, color.b + 0.22f, 1);
        pix.fill();
        style.over = new TextureRegionDrawable(new Texture(pix));

        pix.dispose();
        return style;
    }

    private void createSettingsTable() {
        settingsTable = new Table();
        settingsTable.setFillParent(true);

        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        p.setColor(0, 0, 0, 0.85f);
        p.fill();
        settingsTable.background(new TextureRegionDrawable(new Texture(p)));
        p.dispose();

        Label header = new Label("SETTINGS", new Label.LabelStyle(titleFont, Color.WHITE));
        header.setFontScale(2.3f);

        Label controls = new Label("WASD — Движение\nЛКМ — Атака\nПКМ — Щит\nПРОБЕЛ — Рывок\n1-5 — Смена оружия",
            new Label.LabelStyle(buttonFont, Color.LIGHT_GRAY));
        controls.setFontScale(1.4f);

        TextButton backBtn = new TextButton("НАЗАД В МЕНЮ", createButtonStyle(Color.DARK_GRAY));
        backBtn.getLabel().setFontScale(1.5f);

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideSettings();
            }
        });

        settingsTable.add(header).padBottom(40).row();
        settingsTable.add(controls).padBottom(60).row();
        settingsTable.add(backBtn).width(340).height(75);

        settingsTable.setVisible(false);
        stage.addActor(settingsTable);
    }

    private void showSettings() {
        settingsOpen = true;
        settingsTable.setVisible(true);
    }

    private void hideSettings() {
        settingsOpen = false;
        settingsTable.setVisible(false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // === ФОН НА ВЕСЬ ЭКРАН ===
        batch.begin();
        batch.draw(menuBackground, 0, 0, 1280, 720);   // Полное растяжение
        batch.end();

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            if (settingsOpen) hideSettings();
            else Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        menuBackground.dispose();
        titleFont.dispose();
        buttonFont.dispose();
    }
}
