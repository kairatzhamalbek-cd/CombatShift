package com.pixelforge.combatshift;

public final class Constants {
    private Constants() {}

    public static final float TILE_SIZE = 32f;
    public static final int MAP_WIDTH_TILES = 120;
    public static final int MAP_HEIGHT_TILES = 120;

    public static final float WORLD_WIDTH = MAP_WIDTH_TILES * TILE_SIZE;
    public static final float WORLD_HEIGHT = MAP_HEIGHT_TILES * TILE_SIZE;

    public static final float PLAYER_SIZE = 32f;
    public static final float PLAYER_COLLISION_SIZE = 22f;

    public static final float MOB_SIZE = 30f;
    public static final float AGGRO_RADIUS = TILE_SIZE * 18f;

    public static final float ROUND_BREAK_SECONDS = 30f;
    public static final float START_ROUND_DELAY_SECONDS = 2f;
}

