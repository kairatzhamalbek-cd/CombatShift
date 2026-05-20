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
    //-------------------------------------------------------------------ЛОКАЦИЯ 2
    public Texture desertGround;
    public Texture desertTreeMedium;
    public Texture desertRockMedium;
    public Texture desertRockSmall;
    public Texture desertBushMedium;
    public Texture desertBushSmall;

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
        //-------------------------------------------------------------------------
        manager.load("location-2-ground-desert.jpg", Texture.class);
        manager.load("location2-treemedium-desert-removebg-preview.png", Texture.class);
        manager.load("location2rockmedium-desert-removebg-preview.png", Texture.class);
        manager.load("location2rocksmall-desert.png", Texture.class);
        manager.load("location2-bushesmedium-desert-removebg-preview.png", Texture.class);
        manager.load("location2-bushes-small-desrt-removebg-preview.png", Texture.class);

        manager.finishLoading();

        desertGround       = manager.get("location-2-ground-desert.jpg", Texture.class);
        desertTreeMedium   = manager.get("location2-treemedium-desert-removebg-preview.png", Texture.class);
        desertRockMedium   = manager.get("location2rockmedium-desert-removebg-preview.png", Texture.class);
        desertRockSmall    = manager.get("location2rocksmall-desert.png", Texture.class);
        desertBushMedium   = manager.get("location2-bushesmedium-desert-removebg-preview.png", Texture.class);
        desertBushSmall    = manager.get("location2-bushes-small-desrt-removebg-preview.png", Texture.class);

    }

    public void dispose() {
        manager.dispose();
    }
}
