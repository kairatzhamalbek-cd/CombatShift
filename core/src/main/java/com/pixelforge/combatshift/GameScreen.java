package com.pixelforge.combatshift;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.pixelforge.combatshift.assets.AssetManagerHelper;
import com.pixelforge.combatshift.entity.Mob;
import com.pixelforge.combatshift.entity.Player;
import com.pixelforge.combatshift.map.DesertMap;
import com.pixelforge.combatshift.map.GameMap;
import com.pixelforge.combatshift.map.GameMapInterface;
import com.pixelforge.combatshift.map.WinterMap;
import com.pixelforge.combatshift.ui.HudRenderer;

public class GameScreen implements Screen {

    private final MainGame game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Player player;
    private GameMapInterface map;
    private AssetManagerHelper assets;

    private HudRenderer hud;   // ← Добавлено

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

        map = new GameMap(assets);
        player = new Player(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2, assets);

        hud = new HudRenderer();   // ← Инициализация HUD

        if (map instanceof GameMap) {
            ((GameMap) map).playMusic();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta) {
        float dx = 0, dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) dy += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) dy -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) dx -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dx += 1;

        if (dx != 0 || dy != 0) {
            float len = (float) Math.sqrt(dx * dx + dy * dy);
            dx /= len;
            dy /= len;

            float newX = player.getX() + dx * Constants.PLAYER_SPEED * delta;
            float newY = player.getY() + dy * Constants.PLAYER_SPEED * delta;

            Rectangle futureBounds = new Rectangle(newX, newY, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);

            if (!map.collides(futureBounds)) {
                player.setPosition(newX, newY);
            }
        }

        player.update(delta, dx, dy);

        if (map instanceof GameMap) {
            GameMap gm = (GameMap) map;
            gm.updateMobs(delta, player);
            gm.checkRoundEnd();
            gm.updateBreak(delta, player);
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
            GameMap forestMap = (GameMap) map;
            for (Mob mob : forestMap.getMobs()) {
                if (!mob.isDead()) {  // добавлено чтобы не рисовать мёртвых
                    drawList.add(new SortableObject(mob.getY(), () -> mob.render(batch)));
                }
            }
        }

        for (int i = 0; i < map.getObstacles().size; i++) {
            Rectangle rect = map.getObstacles().get(i);
            String type = map.getObstacleTypes().get(i);
            final Rectangle r = rect;
            final String t = type;

            drawList.add(new SortableObject(rect.y, () -> {
                if (map instanceof com.pixelforge.combatshift.map.WinterMap) {
                    // Winter drawing...
                } else if (map instanceof com.pixelforge.combatshift.map.DesertMap) {
                    // Desert drawing...
                } else {
                    // Forest
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

        for (SortableObject obj : drawList) {
            obj.drawAction.run();
        }

        batch.end();

        // HUD
        if (map instanceof GameMap) {
            GameMap gm = (GameMap) map;
            hud.render(batch, player, gm.getLocationName(), gm.getCurrentRound(), camera.viewportWidth);
        }
    }

    @Override
    public void dispose() {
        if (map instanceof GameMap) ((GameMap) map).stopMusic();
        assets.dispose();
        map.dispose();
        batch.dispose();
        player.dispose();
        if (hud != null) hud.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
