package com.pixelforge.combatshift.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetManagerHelper {

    public final AssetManager manager = new AssetManager();

    // Текстуры карты
    public Texture groundTexture;
    public Texture treeTexture;

    public void load() {
        // Загружаем текстуры
        manager.load("Top-Down Simple Summer_Ground 43.png", Texture.class);
        manager.load("Top-Down Simple Summer_Prop - Tree Small.png", Texture.class);

        manager.finishLoading(); // Ждём, пока всё загрузится

        // Получаем текстуры после загрузки
        groundTexture = manager.get("Top-Down Simple Summer_Ground 43.png", Texture.class);
        treeTexture   = manager.get("Top-Down Simple Summer_Prop - Tree Small.png", Texture.class);
    }

    public void dispose() {
        manager.dispose();
    }
}
