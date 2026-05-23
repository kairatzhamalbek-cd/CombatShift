package com.pixelforge.combatshift.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetManagerHelper {

    public final AssetManager manager = new AssetManager();

    // Forest
    public Texture groundTexture;
    public Texture treeMedium;
    public Texture rock;
    public Texture bushMedium;
    public Texture bushLarge;
    public Texture stumpShort;
    public Texture stumpTall;

    // Desert
    public Texture desertGround;
    public Texture desertTreeMedium;
    public Texture desertRockMedium;
    public Texture desertRockSmall;
    public Texture desertBushMedium;
    public Texture desertBushSmall;

    // Winter
    public Texture winterGround;
    public Texture winterTree;
    public Texture winterRockMedium;
    public Texture winterRockSmall;
    public Texture winterBushMedium;
    public Texture winterBushSmall;

    // Music
    public com.badlogic.gdx.audio.Music forestMusic;

    // ====================== ГЕРОЙ (7 спрайтшитов) ======================
    public Texture attackSheet;
    public Texture hurtSheet;
    public Texture idleSheet;
    public Texture runAttackSheet;
    public Texture runSheet;
    public Texture walkAttackSheet;
    public Texture walkSheet;

    // ====================== МОБЫ (только для Forest) ======================
    // Slime1
    public Texture slimeAttackSheet;
    public Texture slimeDeathSheet;
    public Texture slimeHurtSheet;
    public Texture slimeIdleSheet;
    public Texture slimeRunSheet;
    public Texture slimeWalkSheet;

    // Vampire1
    public Texture vampireAttackSheet;
    public Texture vampireDeathSheet;
    public Texture vampireHurtSheet;
    public Texture vampireIdleSheet;
    public Texture vampireRunSheet;
    public Texture vampireWalkSheet;

    // OrcBoss1
    public Texture orcAttackSheet;
    public Texture orcDeathSheet;
    public Texture orcHurtSheet;
    public Texture orcIdleSheet;
    public Texture orcRunAttackSheet;
    public Texture orcRunSheet;
    public Texture orcWalkAttackSheet;
    public Texture orcWalkSheet;

    public void load() {
        // ====================== FOREST ======================
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

        // ====================== DESERT ======================
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

        // ====================== WINTER ======================
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

        // ====================== ГЕРОЙ ======================
        manager.load("Swordsman_lvl3_attack_without_shadow.png", Texture.class);
        manager.load("Swordsman_lvl3_Hurt_without_shadow.png", Texture.class);
        manager.load("Swordsman_lvl3_Idle_without_shadow.png", Texture.class);
        manager.load("Swordsman_lvl3_Run_Attack_without_shadow.png", Texture.class);
        manager.load("Swordsman_lvl3_Run_without_shadow.png", Texture.class);
        manager.load("Swordsman_lvl3_Walk_Attack_without_shadow.png", Texture.class);
        manager.load("Swordsman_lvl3_Walk_without_shadow.png", Texture.class);

        manager.finishLoading();

        attackSheet     = manager.get("Swordsman_lvl3_attack_without_shadow.png", Texture.class);
        hurtSheet       = manager.get("Swordsman_lvl3_Hurt_without_shadow.png", Texture.class);
        idleSheet       = manager.get("Swordsman_lvl3_Idle_without_shadow.png", Texture.class);
        runAttackSheet  = manager.get("Swordsman_lvl3_Run_Attack_without_shadow.png", Texture.class);
        runSheet        = manager.get("Swordsman_lvl3_Run_without_shadow.png", Texture.class);
        walkAttackSheet = manager.get("Swordsman_lvl3_Walk_Attack_without_shadow.png", Texture.class);
        walkSheet       = manager.get("Swordsman_lvl3_Walk_without_shadow.png", Texture.class);

        // ====================== МОБЫ ======================
        // Slime1
        manager.load("Slime1_Attack_without_shadow.png", Texture.class);
        manager.load("Slime1_Death_without_shadow.png", Texture.class);
        manager.load("Slime1_Hurt_without_shadow.png", Texture.class);
        manager.load("Slime1_Idle_without_shadow.png", Texture.class);
        manager.load("Slime1_Run_without_shadow.png", Texture.class);
        manager.load("Slime1_Walk_without_shadow.png", Texture.class);

        // Vampire1
        manager.load("Vampires1_Attack_without_shadow.png", Texture.class);
        manager.load("Vampires1_Death_without_shadow.png", Texture.class);
        manager.load("Vampires1_Hurt_without_shadow.png", Texture.class);
        manager.load("Vampires1_Idle_without_shadow.png", Texture.class);
        manager.load("Vampires1_Run_without_shadow.png", Texture.class);
        manager.load("Vampires1_Walk_without_shadow.png", Texture.class);

        // OrcBoss1
        manager.load("orc1_attack_without_shadow.png", Texture.class);
        manager.load("orc1_death_without_shadow.png", Texture.class);
        manager.load("orc1_hurt_without_shadow.png", Texture.class);
        manager.load("orc1_idle_without_shadow.png", Texture.class);
        manager.load("orc1_run_attack_front_without_shadow.png", Texture.class);
        manager.load("orc1_run_without_shadow.png", Texture.class);
        manager.load("orc1_walk_attack_front _without_shadow.png", Texture.class);
        manager.load("orc1_walk_without_shadow.png", Texture.class);

        manager.finishLoading();

        slimeAttackSheet = manager.get("Slime1_Attack_without_shadow.png", Texture.class);
        slimeDeathSheet  = manager.get("Slime1_Death_without_shadow.png", Texture.class);
        slimeHurtSheet   = manager.get("Slime1_Hurt_without_shadow.png", Texture.class);
        slimeIdleSheet   = manager.get("Slime1_Idle_without_shadow.png", Texture.class);
        slimeRunSheet    = manager.get("Slime1_Run_without_shadow.png", Texture.class);
        slimeWalkSheet   = manager.get("Slime1_Walk_without_shadow.png", Texture.class);

        vampireAttackSheet = manager.get("Vampires1_Attack_without_shadow.png", Texture.class);
        vampireDeathSheet  = manager.get("Vampires1_Death_without_shadow.png", Texture.class);
        vampireHurtSheet   = manager.get("Vampires1_Hurt_without_shadow.png", Texture.class);
        vampireIdleSheet   = manager.get("Vampires1_Idle_without_shadow.png", Texture.class);
        vampireRunSheet    = manager.get("Vampires1_Run_without_shadow.png", Texture.class);
        vampireWalkSheet   = manager.get("Vampires1_Walk_without_shadow.png", Texture.class);

        orcAttackSheet      = manager.get("orc1_attack_without_shadow.png", Texture.class);
        orcDeathSheet       = manager.get("orc1_death_without_shadow.png", Texture.class);
        orcHurtSheet        = manager.get("orc1_hurt_without_shadow.png", Texture.class);
        orcIdleSheet        = manager.get("orc1_idle_without_shadow.png", Texture.class);
        orcRunAttackSheet   = manager.get("orc1_run_attack_front_without_shadow.png", Texture.class);
        orcRunSheet         = manager.get("orc1_run_without_shadow.png", Texture.class);
        orcWalkAttackSheet  = manager.get("orc1_walk_attack_front _without_shadow.png", Texture.class);
        orcWalkSheet        = manager.get("orc1_walk_without_shadow.png", Texture.class);

        // ====================== МУЗЫКА ======================
        manager.load("Rozen - Forest Temple (OST из игры _Shadows of Hyrule_) (muzmos.net).mp3",
            com.badlogic.gdx.audio.Music.class);

        manager.finishLoading();

        forestMusic = manager.get("Rozen - Forest Temple (OST из игры _Shadows of Hyrule_) (muzmos.net).mp3",
            com.badlogic.gdx.audio.Music.class);
        forestMusic.setLooping(true);
        forestMusic.setVolume(0.65f);
    }

    public void dispose() {
        manager.dispose();
    }
}
