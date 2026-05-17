package com.whatcraft2d.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.whatcraft2d.game.assets.AssetManagerHelper;
import com.whatcraft2d.game.combat.Weapon;
import com.whatcraft2d.game.entities.Mob;
import com.whatcraft2d.game.entities.Player;
import com.whatcraft2d.game.input.InputHandler;
import com.whatcraft2d.game.map.GameMap;
import com.whatcraft2d.game.systems.CameraController;
import com.whatcraft2d.game.systems.WaveManager;
import com.whatcraft2d.game.ui.HudRenderer;

public class GameScreen implements Screen {
    private final MainGame game;
    private final AssetManagerHelper assets;

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;
    private Viewport viewport;
    private ScreenViewport uiViewport;
    private InputHandler input;
    private GameMap map;
    private Player player;
    private WaveManager waveManager;
    private CameraController cameraController;
    private HudRenderer hud;

    public GameScreen(MainGame game, AssetManagerHelper assets) {
        this.game = game;
        this.assets = assets;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        camera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();
        viewport = new FitViewport(960f, 540f, camera);
        uiViewport = new ScreenViewport(uiCamera);
        input = new InputHandler();
        Gdx.input.setInputProcessor(input);
        hud = new HudRenderer();
        restartWorld();
    }

    private void restartWorld() {
        map = new GameMap(Constants.MAP_WIDTH_TILES, Constants.MAP_HEIGHT_TILES, assets);
        player = new Player(map.startPosition().x, map.startPosition().y);
        waveManager = new WaveManager(map);
        cameraController = new CameraController(camera, map);
        cameraController.follow(player);
    }

    @Override
    public void render(float delta) {
        float clampedDelta = Math.min(delta, 1f / 30f);
        update(clampedDelta);
        draw();
    }

    private void update(float delta) {
        if (player.isDead()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                restartWorld();
            }
            return;
        }

        player.update(delta, input, map);
        if (input.consumeAttackRequested()) {
            player.tryAttack(waveManager.getMobs());
        }
        waveManager.update(delta, player);
        cameraController.follow(player);
    }

    private void draw() {
        Gdx.gl.glClearColor(0.07f, 0.14f, 0.09f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        map.draw(batch, camera, assets);
        for (Mob mob : waveManager.getMobs()) {
            mob.draw(batch, assets);
        }
        player.draw(batch, assets);
        batch.end();

        drawWorldOverlays();

        hud.draw(batch, shapes, uiCamera, assets, player, waveManager);
        drawDeathOverlay();
    }

    private void drawWorldOverlays() {
        Weapon weapon = player.selectedWeapon();
        if (weapon != null) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapes.setProjectionMatrix(camera.combined);
            shapes.begin(ShapeRenderer.ShapeType.Filled);
            if (player.isInWater()) {
                shapes.setColor(0.25f, 0.55f, 1f, 0.08f);
            } else {
                shapes.setColor(0.75f, 0.75f, 0.75f, 0.10f);
            }
            shapes.circle(player.getPosition().x, player.getPosition().y, weapon.getRange(), 72);
            shapes.end();

            shapes.begin(ShapeRenderer.ShapeType.Line);
            shapes.setColor(0.82f, 0.82f, 0.82f, player.isInWater() ? 0.18f : 0.42f);
            shapes.circle(player.getPosition().x, player.getPosition().y, weapon.getRange(), 72);
            shapes.end();
        }

        shapes.setProjectionMatrix(camera.combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        for (Mob mob : waveManager.getMobs()) {
            drawMobHealth(mob);
        }
        if (player.isShieldActive()) {
            shapes.setColor(0.35f, 0.58f, 1f, 0.22f);
            shapes.circle(player.getPosition().x, player.getPosition().y, 35f, 32);
        }
        shapes.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void drawMobHealth(Mob mob) {
        float ratio = mob.getHp() / mob.getMaxHp();
        Rectangle b = mob.bounds();
        float x = b.x;
        float y = b.y + b.height + 8f;
        shapes.setColor(0f, 0f, 0f, 0.60f);
        shapes.rect(x, y, b.width, 4f);
        shapes.setColor(0.86f, 0.08f, 0.08f, 0.95f);
        shapes.rect(x, y, b.width * Math.max(0f, ratio), 4f);
    }

    private void drawDeathOverlay() {
        if (!player.isDead()) return;
        shapes.setProjectionMatrix(uiCamera.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 0.70f);
        shapes.rect(0f, 0f, uiCamera.viewportWidth, uiCamera.viewportHeight);
        shapes.end();

        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        assets.font.setColor(1f, 0.25f, 0.25f, 1f);
        assets.font.getData().setScale(2f);
        assets.font.draw(batch, "YOU DIED", uiCamera.viewportWidth * 0.5f - 78f, uiCamera.viewportHeight * 0.5f + 24f);
        assets.font.getData().setScale(1f);
        assets.font.setColor(1f, 1f, 1f, 1f);
        assets.font.draw(batch, "Press R to restart", uiCamera.viewportWidth * 0.5f - 62f, uiCamera.viewportHeight * 0.5f - 10f);
        batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        uiViewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (shapes != null) shapes.dispose();
    }
}
