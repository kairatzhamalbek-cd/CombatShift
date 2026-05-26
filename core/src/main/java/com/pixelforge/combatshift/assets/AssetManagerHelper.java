package com.pixelforge.combatshift.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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
    public Music forestMusic;

    // ====================== ГЕРОЙ ======================
    public Texture attackSheet;
    public Texture hurtSheet;
    public Texture idleSheet;
    public Texture runAttackSheet;
    public Texture runSheet;
    public Texture walkAttackSheet;
    public Texture walkSheet;

    // ====================== МОБЫ FOREST (1) ======================
    public Texture slime1AttackSheet, slime1DeathSheet, slime1HurtSheet, slime1IdleSheet, slime1RunSheet, slime1WalkSheet;
    public Texture vampire1AttackSheet, vampire1DeathSheet, vampire1HurtSheet, vampire1IdleSheet, vampire1RunSheet, vampire1WalkSheet;
    public Texture orc1AttackSheet, orc1DeathSheet, orc1HurtSheet, orc1IdleSheet, orc1RunAttackSheet, orc1RunSheet, orc1WalkAttackSheet, orc1WalkSheet;

    // ====================== МОБЫ DESERT (2) ======================
    public Texture slime2AttackSheet, slime2DeathSheet, slime2HurtSheet, slime2IdleSheet, slime2RunSheet, slime2WalkSheet;
    public Texture vampire2AttackSheet, vampire2DeathSheet, vampire2HurtSheet, vampire2IdleSheet, vampire2RunSheet, vampire2WalkSheet;
    public Texture orc2AttackSheet, orc2DeathSheet, orc2HurtSheet, orc2IdleSheet, orc2RunAttackSheet, orc2RunSheet, orc2WalkAttackSheet, orc2WalkSheet;

    // ====================== МОБЫ WINTER (3) ======================
    public Texture slime3AttackSheet, slime3DeathSheet, slime3HurtSheet, slime3IdleSheet, slime3RunSheet, slime3WalkSheet;
    public Texture vampire3AttackSheet, vampire3DeathSheet, vampire3HurtSheet, vampire3IdleSheet, vampire3RunSheet, vampire3WalkSheet;
    public Texture orc3AttackSheet, orc3DeathSheet, orc3HurtSheet, orc3IdleSheet, orc3RunAttackSheet, orc3RunSheet, orc3WalkAttackSheet, orc3WalkSheet;

    public void load() {
        // === Текстуры локаций ===
        manager.load("Top-Down Simple Summer_Ground 43.png", Texture.class);
        manager.load("Top-Down Simple Summer_Prop - Tree Medium.png", Texture.class);
        manager.load("Top-Down Simple Summer_Prop - Rock 01.png", Texture.class);
        manager.load("Top-Down Simple Summer_Prop - Bushes Medium.png", Texture.class);
        manager.load("Top-Down Simple Summer_Prop - Bushes Large.png", Texture.class);
        manager.load("Top-Down Simple Summer_Prop - Tree Stump Short.png", Texture.class);
        manager.load("Top-Down Simple Summer_Prop - Tree Stump Tall.png", Texture.class);

        manager.load("location-2-ground-desert.jpg", Texture.class);
        manager.load("location2-treemedium-desert-removebg-preview.png", Texture.class);
        manager.load("location2rockmedium-desert-removebg-preview.png", Texture.class);
        manager.load("location2rocksmall-desert.png", Texture.class);
        manager.load("location2-bushesmedium-desert-removebg-preview.png", Texture.class);
        manager.load("location2-bushes-small-desrt-removebg-preview.png", Texture.class);

        manager.load("location3ground-winter.png", Texture.class);
        manager.load("location3-tree-winter.png", Texture.class);
        manager.load("location3-rock-medium-winter.png", Texture.class);
        manager.load("location3rocksmall-winter.png", Texture.class);
        manager.load("location3bushes-medium.png", Texture.class);
        manager.load("location3-bushes-small.png", Texture.class);

        // === Герой ===
        manager.load("Swordsman_lvl3_attack_without_shadow.png", Texture.class);
        manager.load("Swordsman_lvl3_Hurt_without_shadow.png", Texture.class);
        manager.load("Swordsman_lvl3_Idle_without_shadow.png", Texture.class);
        manager.load("Swordsman_lvl3_Run_Attack_without_shadow.png", Texture.class);
        manager.load("Swordsman_lvl3_Run_without_shadow.png", Texture.class);
        manager.load("Swordsman_lvl3_Walk_Attack_without_shadow.png", Texture.class);
        manager.load("Swordsman_lvl3_Walk_without_shadow.png", Texture.class);

        // === Мобы Forest (1) ===
        manager.load("Slime1_Attack_without_shadow.png", Texture.class);
        manager.load("Slime1_Death_without_shadow.png", Texture.class);
        manager.load("Slime1_Hurt_without_shadow.png", Texture.class);
        manager.load("Slime1_Idle_without_shadow.png", Texture.class);
        manager.load("Slime1_Run_without_shadow.png", Texture.class);
        manager.load("Slime1_Walk_without_shadow.png", Texture.class);

        manager.load("Vampires1_Attack_without_shadow.png", Texture.class);
        manager.load("Vampires1_Death_without_shadow.png", Texture.class);
        manager.load("Vampires1_Hurt_without_shadow.png", Texture.class);
        manager.load("Vampires1_Idle_without_shadow.png", Texture.class);
        manager.load("Vampires1_Run_without_shadow.png", Texture.class);
        manager.load("Vampires1_Walk_without_shadow.png", Texture.class);

        manager.load("orc1_attack_without_shadow.png", Texture.class);
        manager.load("orc1_death_without_shadow.png", Texture.class);
        manager.load("orc1_hurt_without_shadow.png", Texture.class);
        manager.load("orc1_idle_without_shadow.png", Texture.class);
        manager.load("orc1_run_attack_front_without_shadow.png", Texture.class);
        manager.load("orc1_run_without_shadow.png", Texture.class);
        manager.load("orc1_walk_attack_front _without_shadow.png", Texture.class);
        manager.load("orc1_walk_without_shadow.png", Texture.class);

        // === Мобы Desert (2) ===
        manager.load("Slime2_Attack_without_shadow.png", Texture.class);
        manager.load("Slime2_Death_without_shadow.png", Texture.class);
        manager.load("Slime2_Hurt_without_shadow.png", Texture.class);
        manager.load("Slime2_Idle_without_shadow.png", Texture.class);
        manager.load("Slime2_Run_without_shadow.png", Texture.class);
        manager.load("Slime2_Walk_without_shadow.png", Texture.class);

        manager.load("Vampires2_Attack_without_shadow.png", Texture.class);
        manager.load("Vampires2_Death_without_shadow.png", Texture.class);
        manager.load("Vampires2_Hurt_without_shadow.png", Texture.class);
        manager.load("Vampires2_Idle_without_shadow.png", Texture.class);
        manager.load("Vampires2_Run_without_shadow.png", Texture.class);
        manager.load("Vampires2_Walk_without_shadow.png", Texture.class);

        manager.load("orc2_attack_without_shadow.png", Texture.class);
        manager.load("orc2_death_without_shadow.png", Texture.class);
        manager.load("orc2_hurt_without_shadow.png", Texture.class);
        manager.load("orc2_idle_without_shadow.png", Texture.class);
        manager.load("orc2_run_attack_without_shadow.png", Texture.class);
        manager.load("orc2_run_without_shadow.png", Texture.class);
        manager.load("orc2_walk_attack_without_shadow.png", Texture.class);
        manager.load("orc2_walk_without_shadow.png", Texture.class);

        // === Мобы Winter (3) ===
        manager.load("Slime3_Attack_without_shadow.png", Texture.class);
        manager.load("Slime3_Death_without_shadow.png", Texture.class);
        manager.load("Slime3_Hurt_without_shadow.png", Texture.class);
        manager.load("Slime3_Idle_without_shadow.png", Texture.class);
        manager.load("Slime3_Run_without_shadow.png", Texture.class);
        manager.load("Slime3_Walk_without_shadow.png", Texture.class);

        manager.load("Vampires3_Attack_without_shadow.png", Texture.class);
        manager.load("Vampires3_Death_without_shadow.png", Texture.class);
        manager.load("Vampires3_Hurt_without_shadow.png", Texture.class);
        manager.load("Vampires3_Idle_without_shadow.png", Texture.class);
        manager.load("Vampires3_Run_without_shadow.png", Texture.class);
        manager.load("Vampires3_Walk_without_shadow.png", Texture.class);

        manager.load("orc3_attack_without_shadow.png", Texture.class);
        manager.load("orc3_death_without_shadow.png", Texture.class);
        manager.load("orc3_hurt_without_shadow.png", Texture.class);
        manager.load("orc3_idle_without_shadow.png", Texture.class);
        manager.load("orc3_run_attack_without_shadow.png", Texture.class);
        manager.load("orc3_run_without_shadow.png", Texture.class);
        manager.load("orc3_walk_attack_without_shadow.png", Texture.class);
        manager.load("orc3_walk_without_shadow.png", Texture.class);

        // === МУЗЫКА ===
        manager.load("Rozen - Forest Temple (OST из игры _Shadows of Hyrule_) (muzmos.net).mp3", Music.class);

        manager.finishLoading();

        // === Получаем все ассеты ===
        groundTexture = manager.get("Top-Down Simple Summer_Ground 43.png", Texture.class);
        treeMedium = manager.get("Top-Down Simple Summer_Prop - Tree Medium.png", Texture.class);
        rock = manager.get("Top-Down Simple Summer_Prop - Rock 01.png", Texture.class);
        bushMedium = manager.get("Top-Down Simple Summer_Prop - Bushes Medium.png", Texture.class);
        bushLarge = manager.get("Top-Down Simple Summer_Prop - Bushes Large.png", Texture.class);
        stumpShort = manager.get("Top-Down Simple Summer_Prop - Tree Stump Short.png", Texture.class);
        stumpTall = manager.get("Top-Down Simple Summer_Prop - Tree Stump Tall.png", Texture.class);

        desertGround = manager.get("location-2-ground-desert.jpg", Texture.class);
        desertTreeMedium = manager.get("location2-treemedium-desert-removebg-preview.png", Texture.class);
        desertRockMedium = manager.get("location2rockmedium-desert-removebg-preview.png", Texture.class);
        desertRockSmall = manager.get("location2rocksmall-desert.png", Texture.class);
        desertBushMedium = manager.get("location2-bushesmedium-desert-removebg-preview.png", Texture.class);
        desertBushSmall = manager.get("location2-bushes-small-desrt-removebg-preview.png", Texture.class);

        winterGround = manager.get("location3ground-winter.png", Texture.class);
        winterTree = manager.get("location3-tree-winter.png", Texture.class);
        winterRockMedium = manager.get("location3-rock-medium-winter.png", Texture.class);
        winterRockSmall = manager.get("location3rocksmall-winter.png", Texture.class);
        winterBushMedium = manager.get("location3bushes-medium.png", Texture.class);
        winterBushSmall = manager.get("location3-bushes-small.png", Texture.class);

        // Hero
        attackSheet = manager.get("Swordsman_lvl3_attack_without_shadow.png", Texture.class);
        hurtSheet = manager.get("Swordsman_lvl3_Hurt_without_shadow.png", Texture.class);
        idleSheet = manager.get("Swordsman_lvl3_Idle_without_shadow.png", Texture.class);
        runAttackSheet = manager.get("Swordsman_lvl3_Run_Attack_without_shadow.png", Texture.class);
        runSheet = manager.get("Swordsman_lvl3_Run_without_shadow.png", Texture.class);
        walkAttackSheet = manager.get("Swordsman_lvl3_Walk_Attack_without_shadow.png", Texture.class);
        walkSheet = manager.get("Swordsman_lvl3_Walk_without_shadow.png", Texture.class);

        // Forest Mobs (1)
        slime1AttackSheet = manager.get("Slime1_Attack_without_shadow.png", Texture.class);
        slime1DeathSheet = manager.get("Slime1_Death_without_shadow.png", Texture.class);
        slime1HurtSheet = manager.get("Slime1_Hurt_without_shadow.png", Texture.class);
        slime1IdleSheet = manager.get("Slime1_Idle_without_shadow.png", Texture.class);
        slime1RunSheet = manager.get("Slime1_Run_without_shadow.png", Texture.class);
        slime1WalkSheet = manager.get("Slime1_Walk_without_shadow.png", Texture.class);

        vampire1AttackSheet = manager.get("Vampires1_Attack_without_shadow.png", Texture.class);
        vampire1DeathSheet = manager.get("Vampires1_Death_without_shadow.png", Texture.class);
        vampire1HurtSheet = manager.get("Vampires1_Hurt_without_shadow.png", Texture.class);
        vampire1IdleSheet = manager.get("Vampires1_Idle_without_shadow.png", Texture.class);
        vampire1RunSheet = manager.get("Vampires1_Run_without_shadow.png", Texture.class);
        vampire1WalkSheet = manager.get("Vampires1_Walk_without_shadow.png", Texture.class);

        orc1AttackSheet = manager.get("orc1_attack_without_shadow.png", Texture.class);
        orc1DeathSheet = manager.get("orc1_death_without_shadow.png", Texture.class);
        orc1HurtSheet = manager.get("orc1_hurt_without_shadow.png", Texture.class);
        orc1IdleSheet = manager.get("orc1_idle_without_shadow.png", Texture.class);
        orc1RunAttackSheet = manager.get("orc1_run_attack_front_without_shadow.png", Texture.class);
        orc1RunSheet = manager.get("orc1_run_without_shadow.png", Texture.class);
        orc1WalkAttackSheet = manager.get("orc1_walk_attack_front _without_shadow.png", Texture.class);
        orc1WalkSheet = manager.get("orc1_walk_without_shadow.png", Texture.class);

        // Desert Mobs (2)
        slime2AttackSheet = manager.get("Slime2_Attack_without_shadow.png", Texture.class);
        slime2DeathSheet = manager.get("Slime2_Death_without_shadow.png", Texture.class);
        slime2HurtSheet = manager.get("Slime2_Hurt_without_shadow.png", Texture.class);
        slime2IdleSheet = manager.get("Slime2_Idle_without_shadow.png", Texture.class);
        slime2RunSheet = manager.get("Slime2_Run_without_shadow.png", Texture.class);
        slime2WalkSheet = manager.get("Slime2_Walk_without_shadow.png", Texture.class);

        vampire2AttackSheet = manager.get("Vampires2_Attack_without_shadow.png", Texture.class);
        vampire2DeathSheet = manager.get("Vampires2_Death_without_shadow.png", Texture.class);
        vampire2HurtSheet = manager.get("Vampires2_Hurt_without_shadow.png", Texture.class);
        vampire2IdleSheet = manager.get("Vampires2_Idle_without_shadow.png", Texture.class);
        vampire2RunSheet = manager.get("Vampires2_Run_without_shadow.png", Texture.class);
        vampire2WalkSheet = manager.get("Vampires2_Walk_without_shadow.png", Texture.class);

        orc2AttackSheet = manager.get("orc2_attack_without_shadow.png", Texture.class);
        orc2DeathSheet = manager.get("orc2_death_without_shadow.png", Texture.class);
        orc2HurtSheet = manager.get("orc2_hurt_without_shadow.png", Texture.class);
        orc2IdleSheet = manager.get("orc2_idle_without_shadow.png", Texture.class);
        orc2RunAttackSheet = manager.get("orc2_run_attack_without_shadow.png", Texture.class);
        orc2RunSheet = manager.get("orc2_run_without_shadow.png", Texture.class);
        orc2WalkAttackSheet = manager.get("orc2_walk_attack_without_shadow.png", Texture.class);
        orc2WalkSheet = manager.get("orc2_walk_without_shadow.png", Texture.class);

        // Winter Mobs (3)
        slime3AttackSheet = manager.get("Slime3_Attack_without_shadow.png", Texture.class);
        slime3DeathSheet = manager.get("Slime3_Death_without_shadow.png", Texture.class);
        slime3HurtSheet = manager.get("Slime3_Hurt_without_shadow.png", Texture.class);
        slime3IdleSheet = manager.get("Slime3_Idle_without_shadow.png", Texture.class);
        slime3RunSheet = manager.get("Slime3_Run_without_shadow.png", Texture.class);
        slime3WalkSheet = manager.get("Slime3_Walk_without_shadow.png", Texture.class);

        vampire3AttackSheet = manager.get("Vampires3_Attack_without_shadow.png", Texture.class);
        vampire3DeathSheet = manager.get("Vampires3_Death_without_shadow.png", Texture.class);
        vampire3HurtSheet = manager.get("Vampires3_Hurt_without_shadow.png", Texture.class);
        vampire3IdleSheet = manager.get("Vampires3_Idle_without_shadow.png", Texture.class);
        vampire3RunSheet = manager.get("Vampires3_Run_without_shadow.png", Texture.class);
        vampire3WalkSheet = manager.get("Vampires3_Walk_without_shadow.png", Texture.class);

        orc3AttackSheet = manager.get("orc3_attack_without_shadow.png", Texture.class);
        orc3DeathSheet = manager.get("orc3_death_without_shadow.png", Texture.class);
        orc3HurtSheet = manager.get("orc3_hurt_without_shadow.png", Texture.class);
        orc3IdleSheet = manager.get("orc3_idle_without_shadow.png", Texture.class);
        orc3RunAttackSheet = manager.get("orc3_run_attack_without_shadow.png", Texture.class);
        orc3RunSheet = manager.get("orc3_run_without_shadow.png", Texture.class);
        orc3WalkAttackSheet = manager.get("orc3_walk_attack_without_shadow.png", Texture.class);
        orc3WalkSheet = manager.get("orc3_walk_without_shadow.png", Texture.class);

        // Music
        forestMusic = manager.get("Rozen - Forest Temple (OST из игры _Shadows of Hyrule_) (muzmos.net).mp3", Music.class);
        forestMusic.setLooping(true);
        forestMusic.setVolume(0.65f);
    }

    public void dispose() {
        manager.dispose();
    }
}
