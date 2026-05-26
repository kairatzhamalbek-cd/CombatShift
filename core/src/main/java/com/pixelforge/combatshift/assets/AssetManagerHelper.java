package com.pixelforge.combatshift.assets;

import com.badlogic.gdx.Gdx;
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
        loadTextureSafe("Top-Down Simple Summer_Ground 43.png");
        loadTextureSafe("Top-Down Simple Summer_Prop - Tree Medium.png");
        loadTextureSafe("Top-Down Simple Summer_Prop - Rock 01.png");
        loadTextureSafe("Top-Down Simple Summer_Prop - Bushes Medium.png");
        loadTextureSafe("Top-Down Simple Summer_Prop - Bushes Large.png");
        loadTextureSafe("Top-Down Simple Summer_Prop - Tree Stump Short.png");
        loadTextureSafe("Top-Down Simple Summer_Prop - Tree Stump Tall.png");

        loadTextureSafe("location-2-ground-desert.jpg");
        loadTextureSafe("location2-treemedium-desert-removebg-preview.png");
        loadTextureSafe("location2rockmedium-desert-removebg-preview.png");
        loadTextureSafe("location2rocksmall-desert.png");
        loadTextureSafe("location2-bushesmedium-desert-removebg-preview.png");
        loadTextureSafe("location2-bushes-small-desrt-removebg-preview.png");

        loadTextureSafe("location3ground-winter.png");
        loadTextureSafe("location3-tree-winter.png");
        loadTextureSafe("location3-rock-medium-winter.png");
        loadTextureSafe("location3rocksmall-winter.png");
        loadTextureSafe("location3bushes-medium.png");
        loadTextureSafe("location3-bushes-small.png");

        // === Герой ===
        loadTextureSafe("Swordsman_lvl3_attack_without_shadow.png");
        loadTextureSafe("Swordsman_lvl3_Hurt_without_shadow.png");
        loadTextureSafe("Swordsman_lvl3_Idle_without_shadow.png");
        loadTextureSafe("Swordsman_lvl3_Run_Attack_without_shadow.png");
        loadTextureSafe("Swordsman_lvl3_Run_without_shadow.png");
        loadTextureSafe("Swordsman_lvl3_Walk_Attack_without_shadow.png");
        loadTextureSafe("Swordsman_lvl3_Walk_without_shadow.png");

        // === Мобы Forest (1) ===
        loadTextureSafe("Slime1_Attack_without_shadow.png");
        loadTextureSafe("Slime1_Death_without_shadow.png");
        loadTextureSafe("Slime1_Hurt_without_shadow.png");
        loadTextureSafe("Slime1_Idle_without_shadow.png");
        loadTextureSafe("Slime1_Run_without_shadow.png");
        loadTextureSafe("Slime1_Walk_without_shadow.png");

        loadTextureSafe("Vampires1_Attack_without_shadow.png");
        loadTextureSafe("Vampires1_Death_without_shadow.png");
        loadTextureSafe("Vampires1_Hurt_without_shadow.png");
        loadTextureSafe("Vampires1_Idle_without_shadow.png");
        loadTextureSafe("Vampires1_Run_without_shadow.png");
        loadTextureSafe("Vampires1_Walk_without_shadow.png");

        loadTextureSafe("orc1_attack_without_shadow.png");
        loadTextureSafe("orc1_death_without_shadow.png");
        loadTextureSafe("orc1_hurt_without_shadow.png");
        loadTextureSafe("orc1_idle_without_shadow.png");
        loadTextureSafe("orc1_run_attack_front_without_shadow.png");
        loadTextureSafe("orc1_run_without_shadow.png");
        loadTextureSafe("orc1_walk_attack_front _without_shadow.png");
        loadTextureSafe("orc1_walk_without_shadow.png");

        // === Мобы Desert (2) ===
        loadTextureSafe("Slime2_Attack_without_shadow.png");
        loadTextureSafe("Slime2_Death_without_shadow.png");
        loadTextureSafe("Slime2_Hurt_without_shadow.png");
        loadTextureSafe("Slime2_Idle_without_shadow.png");
        loadTextureSafe("Slime2_Run_without_shadow.png");
        loadTextureSafe("Slime2_Walk_without_shadow.png");

        loadTextureSafe("Vampires2_Attack_without_shadow.png");
        loadTextureSafe("Vampires2_Death_without_shadow.png");
        loadTextureSafe("Vampires2_Hurt_without_shadow.png");
        loadTextureSafe("Vampires2_Idle_without_shadow.png");
        loadTextureSafe("Vampires2_Run_without_shadow.png");
        loadTextureSafe("Vampires2_Walk_without_shadow.png");

        loadTextureSafe("orc2_attack_without_shadow.png");
        loadTextureSafe("orc2_death_without_shadow.png");
        loadTextureSafe("orc2_hurt_without_shadow.png");
        loadTextureSafe("orc2_idle_without_shadow.png");
        loadTextureSafe("orc2_run_attack_without_shadow.png");
        loadTextureSafe("orc2_run_without_shadow.png");
        loadTextureSafe("orc2_walk_attack_without_shadow.png");
        loadTextureSafe("orc2_walk_without_shadow.png");

        // === Мобы Winter (3) ===
        loadTextureSafe("Slime3_Attack_without_shadow.png");
        loadTextureSafe("Slime3_Death_without_shadow.png");
        loadTextureSafe("Slime3_Hurt_without_shadow.png");
        loadTextureSafe("Slime3_Idle_without_shadow.png");
        loadTextureSafe("Slime3_Run_without_shadow.png");
        loadTextureSafe("Slime3_Walk_without_shadow.png");

        loadTextureSafe("Vampires3_Attack_without_shadow.png");
        loadTextureSafe("Vampires3_Death_without_shadow.png");
        loadTextureSafe("Vampires3_Hurt_without_shadow.png");
        loadTextureSafe("Vampires3_Idle_without_shadow.png");
        loadTextureSafe("Vampires3_Run_without_shadow.png");
        loadTextureSafe("Vampires3_Walk_without_shadow.png");

        loadTextureSafe("orc3_attack_without_shadow.png");
        loadTextureSafe("orc3_death_without_shadow.png");
        loadTextureSafe("orc3_hurt_without_shadow.png");
        loadTextureSafe("orc3_idle_without_shadow.png");
        loadTextureSafe("orc3_run_attack_without_shadow.png");
        loadTextureSafe("orc3_run_without_shadow.png");
        loadTextureSafe("orc3_walk_attack_without_shadow.png");
        loadTextureSafe("orc3_walk_without_shadow.png");

        // === МУЗЫКА ===
        loadMusicSafe("Rozen - Forest Temple (OST из игры _Shadows of Hyrule_) (muzmos.net).mp3");

        manager.finishLoading();

        // === Получаем все ассеты ===
        groundTexture = getTextureSafe("Top-Down Simple Summer_Ground 43.png");
        treeMedium = getTextureSafe("Top-Down Simple Summer_Prop - Tree Medium.png");
        rock = getTextureSafe("Top-Down Simple Summer_Prop - Rock 01.png");
        bushMedium = getTextureSafe("Top-Down Simple Summer_Prop - Bushes Medium.png");
        bushLarge = getTextureSafe("Top-Down Simple Summer_Prop - Bushes Large.png");
        stumpShort = getTextureSafe("Top-Down Simple Summer_Prop - Tree Stump Short.png");
        stumpTall = getTextureSafe("Top-Down Simple Summer_Prop - Tree Stump Tall.png");

        desertGround = getTextureSafe("location-2-ground-desert.jpg");
        desertTreeMedium = getTextureSafe("location2-treemedium-desert-removebg-preview.png");
        desertRockMedium = getTextureSafe("location2rockmedium-desert-removebg-preview.png");
        desertRockSmall = getTextureSafe("location2rocksmall-desert.png");
        desertBushMedium = getTextureSafe("location2-bushesmedium-desert-removebg-preview.png");
        desertBushSmall = getTextureSafe("location2-bushes-small-desrt-removebg-preview.png");

        winterGround = getTextureSafe("location3ground-winter.png");
        winterTree = getTextureSafe("location3-tree-winter.png");
        winterRockMedium = getTextureSafe("location3-rock-medium-winter.png");
        winterRockSmall = getTextureSafe("location3rocksmall-winter.png");
        winterBushMedium = getTextureSafe("location3bushes-medium.png");
        winterBushSmall = getTextureSafe("location3-bushes-small.png");

        // Hero
        attackSheet = getTextureSafe("Swordsman_lvl3_attack_without_shadow.png");
        hurtSheet = getTextureSafe("Swordsman_lvl3_Hurt_without_shadow.png");
        idleSheet = getTextureSafe("Swordsman_lvl3_Idle_without_shadow.png");
        runAttackSheet = getTextureSafe("Swordsman_lvl3_Run_Attack_without_shadow.png");
        runSheet = getTextureSafe("Swordsman_lvl3_Run_without_shadow.png");
        walkAttackSheet = getTextureSafe("Swordsman_lvl3_Walk_Attack_without_shadow.png");
        walkSheet = getTextureSafe("Swordsman_lvl3_Walk_without_shadow.png");

        // Forest Mobs (1)
        slime1AttackSheet = getTextureSafe("Slime1_Attack_without_shadow.png");
        slime1DeathSheet = getTextureSafe("Slime1_Death_without_shadow.png");
        slime1HurtSheet = getTextureSafe("Slime1_Hurt_without_shadow.png");
        slime1IdleSheet = getTextureSafe("Slime1_Idle_without_shadow.png");
        slime1RunSheet = getTextureSafe("Slime1_Run_without_shadow.png");
        slime1WalkSheet = getTextureSafe("Slime1_Walk_without_shadow.png");

        vampire1AttackSheet = getTextureSafe("Vampires1_Attack_without_shadow.png");
        vampire1DeathSheet = getTextureSafe("Vampires1_Death_without_shadow.png");
        vampire1HurtSheet = getTextureSafe("Vampires1_Hurt_without_shadow.png");
        vampire1IdleSheet = getTextureSafe("Vampires1_Idle_without_shadow.png");
        vampire1RunSheet = getTextureSafe("Vampires1_Run_without_shadow.png");
        vampire1WalkSheet = getTextureSafe("Vampires1_Walk_without_shadow.png");

        orc1AttackSheet = getTextureSafe("orc1_attack_without_shadow.png");
        orc1DeathSheet = getTextureSafe("orc1_death_without_shadow.png");
        orc1HurtSheet = getTextureSafe("orc1_hurt_without_shadow.png");
        orc1IdleSheet = getTextureSafe("orc1_idle_without_shadow.png");
        orc1RunAttackSheet = getTextureSafe("orc1_run_attack_front_without_shadow.png");
        orc1RunSheet = getTextureSafe("orc1_run_without_shadow.png");
        orc1WalkAttackSheet = getTextureSafe("orc1_walk_attack_front _without_shadow.png");
        orc1WalkSheet = getTextureSafe("orc1_walk_without_shadow.png");

        // Desert Mobs (2)
        slime2AttackSheet = getTextureSafe("Slime2_Attack_without_shadow.png");
        slime2DeathSheet = getTextureSafe("Slime2_Death_without_shadow.png");
        slime2HurtSheet = getTextureSafe("Slime2_Hurt_without_shadow.png");
        slime2IdleSheet = getTextureSafe("Slime2_Idle_without_shadow.png");
        slime2RunSheet = getTextureSafe("Slime2_Run_without_shadow.png");
        slime2WalkSheet = getTextureSafe("Slime2_Walk_without_shadow.png");

        vampire2AttackSheet = getTextureSafe("Vampires2_Attack_without_shadow.png");
        vampire2DeathSheet = getTextureSafe("Vampires2_Death_without_shadow.png");
        vampire2HurtSheet = getTextureSafe("Vampires2_Hurt_without_shadow.png");
        vampire2IdleSheet = getTextureSafe("Vampires2_Idle_without_shadow.png");
        vampire2RunSheet = getTextureSafe("Vampires2_Run_without_shadow.png");
        vampire2WalkSheet = getTextureSafe("Vampires2_Walk_without_shadow.png");

        orc2AttackSheet = getTextureSafe("orc2_attack_without_shadow.png");
        orc2DeathSheet = getTextureSafe("orc2_death_without_shadow.png");
        orc2HurtSheet = getTextureSafe("orc2_hurt_without_shadow.png");
        orc2IdleSheet = getTextureSafe("orc2_idle_without_shadow.png");
        orc2RunAttackSheet = getTextureSafe("orc2_run_attack_without_shadow.png");
        orc2RunSheet = getTextureSafe("orc2_run_without_shadow.png");
        orc2WalkAttackSheet = getTextureSafe("orc2_walk_attack_without_shadow.png");
        orc2WalkSheet = getTextureSafe("orc2_walk_without_shadow.png");

        // Winter Mobs (3)
        slime3AttackSheet = getTextureSafe("Slime3_Attack_without_shadow.png");
        slime3DeathSheet = getTextureSafe("Slime3_Death_without_shadow.png");
        slime3HurtSheet = getTextureSafe("Slime3_Hurt_without_shadow.png");
        slime3IdleSheet = getTextureSafe("Slime3_Idle_without_shadow.png");
        slime3RunSheet = getTextureSafe("Slime3_Run_without_shadow.png");
        slime3WalkSheet = getTextureSafe("Slime3_Walk_without_shadow.png");

        vampire3AttackSheet = getTextureSafe("Vampires3_Attack_without_shadow.png");
        vampire3DeathSheet = getTextureSafe("Vampires3_Death_without_shadow.png");
        vampire3HurtSheet = getTextureSafe("Vampires3_Hurt_without_shadow.png");
        vampire3IdleSheet = getTextureSafe("Vampires3_Idle_without_shadow.png");
        vampire3RunSheet = getTextureSafe("Vampires3_Run_without_shadow.png");
        vampire3WalkSheet = getTextureSafe("Vampires3_Walk_without_shadow.png");

        orc3AttackSheet = getTextureSafe("orc3_attack_without_shadow.png");
        orc3DeathSheet = getTextureSafe("orc3_death_without_shadow.png");
        orc3HurtSheet = getTextureSafe("orc3_hurt_without_shadow.png");
        orc3IdleSheet = getTextureSafe("orc3_idle_without_shadow.png");
        orc3RunAttackSheet = getTextureSafe("orc3_run_attack_without_shadow.png");
        orc3RunSheet = getTextureSafe("orc3_run_without_shadow.png");
        orc3WalkAttackSheet = getTextureSafe("orc3_walk_attack_without_shadow.png");
        orc3WalkSheet = getTextureSafe("orc3_walk_without_shadow.png");

        // Music
        forestMusic = getMusicSafe("Rozen - Forest Temple (OST из игры _Shadows of Hyrule_) (muzmos.net).mp3");
        if (forestMusic != null) {
            forestMusic.setLooping(true);
            forestMusic.setVolume(0.65f);
        }
    }

    // ==================== БЕЗОПАСНЫЕ МЕТОДЫ ЗАГРУЗКИ ====================

    private void loadTextureSafe(String fileName) {
        if (Gdx.files.internal(fileName).exists()) {
            manager.load(fileName, Texture.class);
        } else {
            System.err.println("⚠️ Пропущен файл (не найден): " + fileName);
        }
    }

    private void loadMusicSafe(String fileName) {
        if (Gdx.files.internal(fileName).exists()) {
            manager.load(fileName, Music.class);
        } else {
            System.err.println("⚠️ Пропущен файл музыки (не найден): " + fileName);
        }
    }

    private Texture getTextureSafe(String fileName) {
        if (manager.isLoaded(fileName)) {
            return manager.get(fileName, Texture.class);
        } else {
            System.err.println("⚠️ Текстура не загружена: " + fileName);
            return null;
        }
    }

    private Music getMusicSafe(String fileName) {
        if (manager.isLoaded(fileName)) {
            return manager.get(fileName, Music.class);
        } else {
            System.err.println("⚠️ Музыка не загружена: " + fileName);
            return null;
        }
    }

    public void dispose() {
        manager.dispose();
    }
}
