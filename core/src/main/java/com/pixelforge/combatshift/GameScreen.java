package com.pixelforge.combatshift;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pixelforge.combatshift.assets.AssetManagerHelper;
import com.pixelforge.combatshift.entity.*;
import com.pixelforge.combatshift.map.*;

public class GameScreen implements Screen {

    private final MainGame game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;
    private Player player;
    private GameMapInterface map;
    private AssetManagerHelper assets;

    private boolean isRunning = false;
    private boolean isAttacking = false;

    private ShapeRenderer shape;
    private BitmapFont font;

    private int currentLocation = 0;

    // === ИНВЕНТАРЬ ===
    private int currentSlot = 0;   // 0–5

    // === ПАУЗА ===
    private boolean isPaused = false;
    private Stage pauseStage;
    private Table pauseTable;

    // === ЭКРАН СМЕРТИ ===
    private boolean isGameOver = false;
    private float gameOverTimer = 10f;
    private Stage gameOverStage;
    private Table gameOverTable;
    private Label deathLabel;
    private Label deathTimerLabel;

    // === ЭКРАН ПОБЕДЫ ===
    private boolean isWin = false;
    private float winTimer = 10f;
    private Stage winStage;
    private Table winTable;
    private Label winLabel;
    private Label winTimerLabel;

    public GameScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        assets = new AssetManagerHelper();
        assets.load();

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, 1280, 720);

        createMapForCurrentLocation();
        player = new Player(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2, assets);

        shape = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        createPauseMenu();
        createGameOverMenu();
        createWinMenu();
    }

    private void createPauseMenu() {
        pauseStage = new Stage(new StretchViewport(1280, 720));

        pauseTable = new Table();
        pauseTable.setFillParent(true);

        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        p.setColor(0, 0, 0, 0.75f);
        p.fill();
        pauseTable.background(new TextureRegionDrawable(new Texture(p)));
        p.dispose();

        TextButton continueBtn = new TextButton("CONTINUE", createPauseButtonStyle());
        TextButton menuBtn = new TextButton("MAIN MENU", createPauseButtonStyle());
        TextButton exitBtn = new TextButton("EXIT", createPauseButtonStyle());

        continueBtn.getLabel().setFontScale(2.2f);
        menuBtn.getLabel().setFontScale(2.2f);
        exitBtn.getLabel().setFontScale(2.2f);

        continueBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resumeGame();
            }
        });

        menuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resumeGame();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        pauseTable.add(continueBtn).width(480).height(95).pad(18).row();
        pauseTable.add(menuBtn).width(480).height(95).pad(18).row();
        pauseTable.add(exitBtn).width(480).height(95).pad(18);

        pauseStage.addActor(pauseTable);
        pauseTable.setVisible(false);
    }

    private TextButton.TextButtonStyle createPauseButtonStyle() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.WHITE;

        Pixmap pix = new Pixmap(480, 95, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(0.15f, 0.15f, 0.15f, 1f));
        pix.fill();
        style.up = new TextureRegionDrawable(new Texture(pix));

        pix.setColor(new Color(0.25f, 0.25f, 0.25f, 1f));
        pix.fill();
        style.over = new TextureRegionDrawable(new Texture(pix));
        pix.dispose();

        return style;
    }

    private void createGameOverMenu() {
        gameOverStage = new Stage(new StretchViewport(1280, 720));

        gameOverTable = new Table();
        gameOverTable.setFillParent(true);

        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        p.setColor(0.15f, 0.0f, 0.0f, 0.92f);
        p.fill();
        gameOverTable.background(new TextureRegionDrawable(new Texture(p)));
        p.dispose();

        deathLabel = new Label("YOU DIED", new Label.LabelStyle(new BitmapFont(), Color.RED));
        deathLabel.setFontScale(6.0f);

        deathTimerLabel = new Label("Restarting in 10...", new Label.LabelStyle(new BitmapFont(), Color.LIGHT_GRAY));
        deathTimerLabel.setFontScale(2.8f);

        gameOverTable.add(deathLabel).padBottom(120).row();
        gameOverTable.add(deathTimerLabel);

        gameOverStage.addActor(gameOverTable);
        gameOverTable.setVisible(false);
    }

    private void createWinMenu() {
        winStage = new Stage(new StretchViewport(1280, 720));

        winTable = new Table();
        winTable.setFillParent(true);

        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        p.setColor(0.0f, 0.12f, 0.05f, 0.92f);
        p.fill();
        winTable.background(new TextureRegionDrawable(new Texture(p)));
        p.dispose();

        winLabel = new Label("YOU WIN", new Label.LabelStyle(new BitmapFont(), new Color(0.2f, 1f, 0.3f, 1f)));
        winLabel.setFontScale(6.0f);

        winTimerLabel = new Label("Restarting in 10...", new Label.LabelStyle(new BitmapFont(), Color.LIGHT_GRAY));
        winTimerLabel.setFontScale(2.8f);

        winTable.add(winLabel).padBottom(120).row();
        winTable.add(winTimerLabel);

        winStage.addActor(winTable);
        winTable.setVisible(false);
    }

    private void pauseGame() {
        isPaused = true;
        pauseTable.setVisible(true);
        Gdx.input.setInputProcessor(pauseStage);
    }

    private void resumeGame() {
        isPaused = false;
        pauseTable.setVisible(false);
        Gdx.input.setInputProcessor(null);
    }

    private void createMapForCurrentLocation() {
        if (map != null) {
            if (map instanceof GameMap) ((GameMap) map).stopMusic();
            map.dispose();
        }

        switch (currentLocation) {
            case 0:
                map = new GameMap(assets);
                if (map instanceof GameMap) ((GameMap) map).playMusic();
                break;
            case 1:
                map = new DesertMap(assets);
                break;
            case 2:
                map = new WinterMap(assets);
                break;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta) {
        // === ЭКРАН СМЕРТИ ===
        if (isGameOver) {
            gameOverTimer -= delta;
            deathTimerLabel.setText("Restarting in " + (int) Math.ceil(gameOverTimer) + "...");

            if (gameOverTimer <= 0) {
                currentLocation = 0;
                createMapForCurrentLocation();
                player = new Player(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2, assets);
                isGameOver = false;
                gameOverTable.setVisible(false);
                Gdx.input.setInputProcessor(null);
            }
            return;
        }

        // === ЭКРАН ПОБЕДЫ ===
        if (isWin) {
            winTimer -= delta;
            winTimerLabel.setText("Restarting in " + (int) Math.ceil(winTimer) + "...");

            if (winTimer <= 0) {
                currentLocation = 0;
                createMapForCurrentLocation();
                player = new Player(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2, assets);
                isWin = false;
                winTable.setVisible(false);
                Gdx.input.setInputProcessor(null);
            }
            return;
        }

        // === ОБРАБОТКА ESC ===
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (isPaused) {
                resumeGame();
            } else {
                pauseGame();
            }
            return;
        }

        if (isPaused) return;

        float dx = 0, dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) dy += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) dy -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) dx -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dx += 1;

        isRunning = Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT);
        isAttacking = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

        // === ПЕРЕКЛЮЧЕНИЕ СЛОТОВ ИНВЕНТАРЯ (1-6) ===
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) currentSlot = 0;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) currentSlot = 1;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) currentSlot = 2;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) currentSlot = 3;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) currentSlot = 4;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) currentSlot = 5;

        if (dx != 0 || dy != 0) {
            float len = (float) Math.sqrt(dx * dx + dy * dy);
            dx /= len;
            dy /= len;

            float currentSpeed = isRunning ? Constants.PLAYER_SPEED * 1.65f : Constants.PLAYER_SPEED;
            float newX = player.getX() + dx * currentSpeed * delta;
            float newY = player.getY() + dy * currentSpeed * delta;

            Rectangle futureBounds = new Rectangle(newX, newY, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);

            if (!map.collides(futureBounds)) {
                player.setPosition(newX, newY);
            }
        }

        player.update(delta, dx, dy, isRunning, isAttacking);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (map instanceof GameMap) ((GameMap) map).checkPlayerAttack(player);
            else if (map instanceof DesertMap) ((DesertMap) map).checkPlayerAttack(player);
            else if (map instanceof WinterMap) ((WinterMap) map).checkPlayerAttack(player);
        }

        if (map instanceof GameMap) ((GameMap) map).update(delta, player);
        else if (map instanceof DesertMap) ((DesertMap) map).update(delta, player);
        else if (map instanceof WinterMap) ((WinterMap) map).update(delta, player);

        // === ПРОВЕРКА СМЕРТИ ГЕРОЯ ===
        if (player.getHp() <= 0f && !isGameOver && !isWin) {
            isGameOver = true;
            gameOverTimer = 10f;
            gameOverTable.setVisible(true);
            Gdx.input.setInputProcessor(gameOverStage);
            if (map instanceof GameMap) ((GameMap) map).stopMusic();
            return;
        }

        // === ПРОВЕРКА ПОБЕДЫ (финальный босс OrcBoss3) ===
        if (map instanceof WinterMap) {
            WinterMap wm = (WinterMap) map;
            if (wm.getCurrentRound() == 3 && wm.getAliveMobCount() == 0 && !isWin && !isGameOver) {
                isWin = true;
                winTimer = 10f;
                winTable.setVisible(true);
                Gdx.input.setInputProcessor(winStage);
                if (map instanceof GameMap) ((GameMap) map).stopMusic();
                return;
            }
        }

        // === ПРОВЕРКА ЗАВЕРШЕНИЯ ЛОКАЦИИ ===
        boolean locationCompleted = false;

        if (map instanceof GameMap) {
            GameMap gm = (GameMap) map;
            if (!gm.isInBreak() && gm.getCurrentRound() > 3) locationCompleted = true;
        } else if (map instanceof DesertMap) {
            DesertMap dm = (DesertMap) map;
            if (!dm.isInBreak() && dm.getCurrentRound() > 3) locationCompleted = true;
        } else if (map instanceof WinterMap) {
            WinterMap wm = (WinterMap) map;
            if (!wm.isInBreak() && wm.getCurrentRound() > 3) locationCompleted = true;
        }

        if (locationCompleted) {
            currentLocation++;
            if (currentLocation > 2) currentLocation = 0;
            createMapForCurrentLocation();
            player.fullHeal();
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0.07f, 0.14f, 0.09f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float camX = MathUtils.clamp(player.getX(), camera.viewportWidth / 2f, Constants.WORLD_WIDTH - camera.viewportWidth / 2f);
        float camY = MathUtils.clamp(player.getY(), camera.viewportHeight / 2f, Constants.WORLD_HEIGHT - camera.viewportHeight / 2f);

        camera.position.set(camX, camY, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        map.drawGround(batch);

        com.badlogic.gdx.utils.Array<SortableObject> drawList = new com.badlogic.gdx.utils.Array<>();
        drawList.add(new SortableObject(player.getY(), () -> player.render(batch)));

        if (map instanceof GameMap) {
            GameMap gm = (GameMap) map;
            for (Slime s : gm.getSlimes()) if (!s.isDead()) drawList.add(new SortableObject(s.getY(), () -> s.render(batch)));
            for (Vampire v : gm.getVampires()) if (!v.isDead()) drawList.add(new SortableObject(v.getY(), () -> v.render(batch)));
            for (OrcBoss o : gm.getOrcBosses()) if (!o.isDead()) drawList.add(new SortableObject(o.getY(), () -> o.render(batch)));
        } else if (map instanceof DesertMap) {
            DesertMap dm = (DesertMap) map;
            for (Slime2 s : dm.getSlimes2()) if (!s.isDead()) drawList.add(new SortableObject(s.getY(), () -> s.render(batch)));
            for (Vampire2 v : dm.getVampires2()) if (!v.isDead()) drawList.add(new SortableObject(v.getY(), () -> v.render(batch)));
            for (OrcBoss2 o : dm.getOrcBosses2()) if (!o.isDead()) drawList.add(new SortableObject(o.getY(), () -> o.render(batch)));
        } else if (map instanceof WinterMap) {
            WinterMap wm = (WinterMap) map;
            for (Slime3 s : wm.getSlimes3()) if (!s.isDead()) drawList.add(new SortableObject(s.getY(), () -> s.render(batch)));
            for (Vampire3 v : wm.getVampires3()) if (!v.isDead()) drawList.add(new SortableObject(v.getY(), () -> v.render(batch)));
            for (OrcBoss3 o : wm.getOrcBosses3()) if (!o.isDead()) drawList.add(new SortableObject(o.getY(), () -> o.render(batch)));
        }

        for (int i = 0; i < map.getObstacles().size; i++) {
            Rectangle rect = map.getObstacles().get(i);
            String type = map.getObstacleTypes().get(i);
            final Rectangle r = rect;
            final String t = type;

            drawList.add(new SortableObject(rect.y, () -> {
                if (map instanceof WinterMap) {
                    switch (t) {
                        case "tree": batch.draw(assets.winterTree, r.x - 55, r.y - 25, 140, 140); break;
                        case "rockMedium": batch.draw(assets.winterRockMedium, r.x - 22, r.y - 18, 65, 60); break;
                        case "rockSmall": batch.draw(assets.winterRockSmall, r.x - 15, r.y - 12, 48, 45); break;
                        case "bushMedium": batch.draw(assets.winterBushMedium, r.x - 18, r.y - 16, 55, 52); break;
                        case "bushSmall": batch.draw(assets.winterBushSmall, r.x - 14, r.y - 12, 45, 42); break;
                    }
                } else if (map instanceof DesertMap) {
                    switch (t) {
                        case "tree": batch.draw(assets.desertTreeMedium, r.x - 70, r.y - 28, 160, 160); break;
                        case "rockMedium": batch.draw(assets.desertRockMedium, r.x - 23, r.y - 14, 75, 75); break;
                        case "rockSmall": batch.draw(assets.desertRockSmall, r.x - 16, r.y - 14, 78, 78); break;
                        case "bushMedium": batch.draw(assets.desertBushMedium, r.x - 16, r.y - 20, 70, 70); break;
                        case "bushSmall": batch.draw(assets.desertBushSmall, r.x - 27, r.y - 18, 70, 70); break;
                    }
                } else {
                    switch (t) {
                        case "tree": batch.draw(assets.treeMedium, r.x - 50, r.y - 15, 128, 128); break;
                        case "rock": batch.draw(assets.rock, r.x - 12, r.y - 10, 40, 40); break;
                        case "bushMedium": batch.draw(assets.bushMedium, r.x - 18, r.y - 12, 45, 45); break;
                        case "bushLarge": batch.draw(assets.bushLarge, r.x - 14, r.y - 16, 60, 55); break;
                        case "stumpShort": batch.draw(assets.stumpShort, r.x - 14, r.y - 8, 35, 35); break;
                        case "stumpTall": batch.draw(assets.stumpTall, r.x - 12, r.y - 6, 38, 42); break;
                    }
                }
            }));
        }

        drawList.sort((a, b) -> Float.compare(b.y, a.y));
        for (SortableObject obj : drawList) obj.drawAction.run();

        batch.end();
        drawHUD();

        if (isPaused) {
            pauseStage.act(Gdx.graphics.getDeltaTime());
            pauseStage.draw();
        }

        if (isGameOver) {
            gameOverStage.act(Gdx.graphics.getDeltaTime());
            gameOverStage.draw();
        }

        if (isWin) {
            winStage.act(Gdx.graphics.getDeltaTime());
            winStage.draw();
        }
    }

    private void drawHUD() {
        shape.setProjectionMatrix(uiCamera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        // === HP и Stamina ===
        float barW = 300f, barH = 25f;
        float x = 40f;

        shape.setColor(0.1f, 0.1f, 0.1f, 1f);
        shape.rect(x, 950, barW, barH);
        shape.setColor(0.9f, 0.1f, 0.1f, 1f);
        shape.rect(x, 950, barW * (player.getHp() / 100f), barH);

        shape.setColor(0.1f, 0.1f, 0.1f, 1f);
        shape.rect(x, 900, barW, barH);
        shape.setColor(0.1f, 0.7f, 0.9f, 1f);
        shape.rect(x, 900, barW * (player.getStamina() / 100f), barH);

        shape.end();

        // === ИНВЕНТАРЬ (6 слотов) ===
        float slotSize = 64f;
        float spacing = 8f;
        float totalWidth = 6 * slotSize + 5 * spacing;
        float startX = (1280 - totalWidth) / 2f;
        float invY = 45f;

        shape.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < 6; i++) {
            float sx = startX + i * (slotSize + spacing);

            // Фон слота
            shape.setColor(0.18f, 0.18f, 0.18f, 0.95f);
            shape.rect(sx, invY, slotSize, slotSize);

            // Выделение выбранного слота (золотая рамка)
            if (i == currentSlot) {
                shape.setColor(1f, 0.85f, 0.2f, 1f);           // золотой
                shape.rect(sx - 4, invY - 4, slotSize + 8, slotSize + 8); // внешняя рамка
                shape.setColor(0.12f, 0.12f, 0.12f, 1f);
                shape.rect(sx, invY, slotSize, slotSize);      // внутренний фон
            }
        }

        shape.end();

        // === Номера слотов ===
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();

        font.getData().setScale(1.4f);
        font.setColor(Color.WHITE);

        for (int i = 0; i < 6; i++) {
            float sx = startX + i * (slotSize + spacing);
            font.draw(batch, String.valueOf(i + 1), sx + 8, invY + 22);
        }

        batch.end();

        // === Остальной HUD (раунды и мобы) ===
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        float barHeight = 8f;
        float barOffsetY = 48f;

        if (map instanceof GameMap) {
            GameMap gm = (GameMap) map;
            for (Slime s : gm.getSlimes()) if (!s.isDead()) drawHealthBar(s.getX(), s.getY(), s.getHp(), 42f, barOffsetY, barHeight);
            for (Vampire v : gm.getVampires()) if (!v.isDead()) drawHealthBar(v.getX(), v.getY(), v.getHp(), 62f, barOffsetY, barHeight);
            for (OrcBoss o : gm.getOrcBosses()) if (!o.isDead()) drawHealthBar(o.getX(), o.getY(), o.getHp(), 220f, 58f, barHeight);
        } else if (map instanceof DesertMap) {
            DesertMap dm = (DesertMap) map;
            for (Slime2 s : dm.getSlimes2()) if (!s.isDead()) drawHealthBar(s.getX(), s.getY(), s.getHp(), 58f, barOffsetY, barHeight);
            for (Vampire2 v : dm.getVampires2()) if (!v.isDead()) drawHealthBar(v.getX(), v.getY(), v.getHp(), 82f, barOffsetY, barHeight);
            for (OrcBoss2 o : dm.getOrcBosses2()) if (!o.isDead()) drawHealthBar(o.getX(), o.getY(), o.getHp(), 280f, 58f, barHeight);
        } else if (map instanceof WinterMap) {
            WinterMap wm = (WinterMap) map;
            for (Slime3 s : wm.getSlimes3()) if (!s.isDead()) drawHealthBar(s.getX(), s.getY(), s.getHp(), 78f, barOffsetY, barHeight);
            for (Vampire3 v : wm.getVampires3()) if (!v.isDead()) drawHealthBar(v.getX(), v.getY(), v.getHp(), 105f, barOffsetY, barHeight);
            for (OrcBoss3 o : wm.getOrcBosses3()) if (!o.isDead()) drawHealthBar(o.getX(), o.getY(), o.getHp(), 350f, 58f, barHeight);
        }

        shape.end();

        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();

        font.getData().setScale(2.0f);
        font.setColor(Color.CYAN);

        String roundText = "";
        int aliveMobs = 0;

        if (map instanceof GameMap) {
            GameMap gm = (GameMap) map;
            roundText = gm.isInBreak() ? "BREAK: " + (int) gm.getBreakTimeLeft() : "ROUND " + gm.getCurrentRound();
            aliveMobs = gm.getAliveMobCount();
        } else if (map instanceof DesertMap) {
            DesertMap dm = (DesertMap) map;
            roundText = dm.isInBreak() ? "BREAK: " + (int) dm.getBreakTimeLeft() : "ROUND " + dm.getCurrentRound();
            aliveMobs = dm.getAliveMobCount();
        } else if (map instanceof WinterMap) {
            WinterMap wm = (WinterMap) map;
            roundText = wm.isInBreak() ? "BREAK: " + (int) wm.getBreakTimeLeft() : "ROUND " + wm.getCurrentRound();
            aliveMobs = wm.getAliveMobCount();
        }

        font.draw(batch, roundText, 480, 970);
        font.getData().setScale(1.6f);
        font.setColor(Color.YELLOW);
        font.draw(batch, "Mobs left: " + aliveMobs, 480, 920);

        batch.end();
    }

    private void drawHealthBar(float mobX, float mobY, float currentHp, float maxHp, float offsetY, float barHeight) {
        float barWidth = 48f;
        float barX = mobX - barWidth / 2f;
        float barY = mobY + offsetY;

        shape.setColor(0.15f, 0.15f, 0.15f, 1f);
        shape.rect(barX, barY, barWidth, barHeight);

        float healthPercent = currentHp / maxHp;
        shape.setColor(0.95f, 0.1f, 0.1f, 1f);
        shape.rect(barX, barY, barWidth * healthPercent, barHeight);
    }

    @Override
    public void dispose() {
        if (map instanceof GameMap) ((GameMap) map).stopMusic();
        assets.dispose();
        map.dispose();
        batch.dispose();
        player.dispose();
        if (shape != null) shape.dispose();
        if (font != null) font.dispose();
        if (pauseStage != null) pauseStage.dispose();
        if (gameOverStage != null) gameOverStage.dispose();
        if (winStage != null) winStage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        uiCamera.setToOrtho(false, width, height);
        if (pauseStage != null) pauseStage.getViewport().update(width, height, true);
        if (gameOverStage != null) gameOverStage.getViewport().update(width, height, true);
        if (winStage != null) winStage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
