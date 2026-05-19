package com.pixelforge.combatshift;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;           // ← Добавь эту строку
import com.pixelforge.combatshift.assets.AssetManagerHelper;
import com.pixelforge.combatshift.entity.Player;
import com.pixelforge.combatshift.map.GameMap;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Player player;
    private GameMap map;
    private AssetManagerHelper assets;

    @Override
    public void show() {
        assets = new AssetManagerHelper();
        assets.load();                    // ← Загружаем ассеты

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        map = new GameMap(assets);
        player = new Player(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta) {
        float dx = 0, dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))    dy += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))  dy -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))  dx -= 1;
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
    }

    private void draw() {
        Gdx.gl.glClearColor(0.07f, 0.14f, 0.09f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        map.draw(batch);
        player.render(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        assets.dispose();
        map.dispose();           // можно оставить, даже если пустой
        batch.dispose();
        player.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
