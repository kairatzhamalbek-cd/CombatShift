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
    //-------------------------------------------------------------------- location-3
    public Texture winterGround;
    public Texture winterTree;
    public Texture winterRockMedium;
    public Texture winterRockSmall;
    public Texture winterBushMedium;
    public Texture winterBushSmall;
    //------------------------------------------------------------ character
    public Texture playerIdle; // можно использовать одну из текстур как idle

    public Texture moveUp1;
    public Texture moveUp2;
    public Texture moveDown1;
    public Texture moveDown2;
    public Texture moveLeft1;
    public Texture moveLeft2;
    public Texture moveRight1;
    public Texture moveRight2;

    //------------------------------------------ music forest
    // Музыка
    public com.badlogic.gdx.audio.Music forestMusic;

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
        //-------------------------------------------------------------------------
        manager.load("location3ground-winter.png", Texture.class);
        manager.load("location3-tree-winter.png", Texture.class);
        manager.load("location3-rock-medium-winter.png", Texture.class);
        manager.load("location3rocksmall-winter.png", Texture.class);
        manager.load("location3bushes-medium.png", Texture.class);
        manager.load("location3-bushes-small.png", Texture.class);

        manager.finishLoading();

        winterGround       = manager.get("location3ground-winter.png", Texture.class);
        winterTree         = manager.get("location3-tree-winter.png", Texture.class);
        winterRockMedium   = manager.get("location3-rock-medium-winter.png", Texture.class);
        winterRockSmall    = manager.get("location3rocksmall-winter.png", Texture.class);
        winterBushMedium   = manager.get("location3bushes-medium.png", Texture.class);
        winterBushSmall    = manager.get("location3-bushes-small.png", Texture.class);

        //---------------------------------------------------------------------------------------------------
        // ====================== ГЕРОЙ ======================
        manager.load("move-top.png", Texture.class);
        manager.load("move-top-top.png", Texture.class);
        manager.load("move-bottom.png", Texture.class);
        manager.load("move-bottom-bottom.png", Texture.class);
        manager.load("move-left.png", Texture.class);
        manager.load("move-left-left.png", Texture.class);
        manager.load("move-right.png", Texture.class);
        manager.load("move-right-right.png", Texture.class);

        manager.finishLoading();

// Загружаем текстуры героя
        moveUp1    = manager.get("move-top.png", Texture.class);
        moveUp2    = manager.get("move-top-top.png", Texture.class);
        moveDown1  = manager.get("move-bottom.png", Texture.class);
        moveDown2  = manager.get("move-bottom-bottom.png", Texture.class);
        moveLeft1  = manager.get("move-left.png", Texture.class);
        moveLeft2  = manager.get("move-left-left.png", Texture.class);
        moveRight1 = manager.get("move-right.png", Texture.class);
        moveRight2 = manager.get("move-right-right.png", Texture.class);

        //-------------------------------------------------------------------
        // ====================== МУЗЫКА ======================
        manager.load("Rozen - Forest Temple (OST из игры _Shadows of Hyrule_) (muzmos.net).mp3",
            com.badlogic.gdx.audio.Music.class);

        manager.finishLoading();

        forestMusic = manager.get("Rozen - Forest Temple (OST из игры _Shadows of Hyrule_) (muzmos.net).mp3",
            com.badlogic.gdx.audio.Music.class);

        forestMusic.setLooping(true);
        forestMusic.setVolume(0.65f); // можно регулировать

    }

    public void dispose() {
        manager.dispose();
    }
}
