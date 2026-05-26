package com.pixelforge.combatshift;

public final class Constants {
    private Constants() {}

    public static final float TILE_SIZE = 32f;
    public static final int MAP_WIDTH_TILES = 80;
    public static final int MAP_HEIGHT_TILES = 80;

    public static final float WORLD_WIDTH = MAP_WIDTH_TILES * TILE_SIZE;
    public static final float WORLD_HEIGHT = MAP_HEIGHT_TILES * TILE_SIZE;

    public static final float PLAYER_SIZE = 22f;
    public static final float PLAYER_SPEED = 250f;

    // === НАСТРОЙКИ ПОВЕДЕНИЯ МОБОВ ===
    public static final float MOB_DETECTION_RANGE = 560f;

    // === РАЗНЫЕ РАДИУСЫ АТАКИ ДЛЯ РАЗНЫХ МОБОВ ===
    public static final float SLIME_ATTACK_RANGE   = 30f;
    public static final float VAMPIRE_ATTACK_RANGE = 30f;
    public static final float ORC_ATTACK_RANGE     = 30f;

    // === УРОН ГЕРОЯ ===
    public static final float PLAYER_ATTACK_DAMAGE = 1000f;

    // === СТАМИНА ===
    public static final float STAMINA_DRAIN_RATE = 30f;
    public static final float STAMINA_REGEN_RATE = 20f;

    // === РАЗМЕРЫ МОБОВ (регулируй по отдельности!) ===
    public static final float SLIME_SCALE   = 1.0f;   // ← Размер всех слаймов
    public static final float VAMPIRE_SCALE = 2f;   // ← Размер всех вампиров
    public static final float ORC_SCALE     = 3f;   // ← Размер всех орков
}
