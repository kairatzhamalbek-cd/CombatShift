package com.pixelforge.combatshift.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetManagerHelper {

    public final AssetManager manager = new AssetManager();

    public Texture groundTexture;
    public Texture treeMedium;
    public Texture rock;
    public Texture bushMedium;
    public Texture bushLarge;
    public Texture stumpShort;
    public Texture stumpTall;

    public void load() {
        manager.load("Top-Down Simple Summer_Ground 43.png", Texture.class);
        manager.load("Top-Down Simple Summer_Prop - Tree Medium.png", Texture.class);
        manager.load("Top-Down Simple Summer_Prop - Rock 01.png", Texture.class);
        manager.load("Top-Down Simple Summer_Prop - Bushes Medium.png", Texture.class);
        manager.load("Top-Down Simple Summer_Prop - Bushes Large.png", Texture.class);
        manager.load("Top-Down Simple Summer_Prop - Tree Stump Short.png", Texture.class);
        manager.load("Top-Down Simple Summer_Prop - Tree Stump Tall.png", Texture.class);

        manager.finishLoading();

        groundTexture = manager.get("Top-Down Simple Summer_Ground 43.png", Texture.class);
        treeMedium    = manager.get("Top-Down Simple Summer_Prop - Tree Medium.png", Texture.class);
        rock          = manager.get("Top-Down Simple Summer_Prop - Rock 01.png", Texture.class);
        bushMedium    = manager.get("Top-Down Simple Summer_Prop - Bushes Medium.png", Texture.class);
        bushLarge     = manager.get("Top-Down Simple Summer_Prop - Bushes Large.png", Texture.class);
        stumpShort    = manager.get("Top-Down Simple Summer_Prop - Tree Stump Short.png", Texture.class);
        stumpTall     = manager.get("Top-Down Simple Summer_Prop - Tree Stump Tall.png", Texture.class);
    }

    public void dispose() {
        manager.dispose();
    }
}
