package com.whatcraft2d.game;

public final class Constants {
    private Constants() {}

    public static final float TILE_SIZE = 32f;
    public static final int MAP_WIDTH_TILES = 120;
    public static final int MAP_HEIGHT_TILES = 120;

    public static final float WORLD_WIDTH = MAP_WIDTH_TILES * TILE_SIZE;
    public static final float WORLD_HEIGHT = MAP_HEIGHT_TILES * TILE_SIZE;

    // Visual sizes now match the 16x32 full-body character / NPC frames.
    // A 16x32 sprite is drawn as 32x64 world pixels, so it stays crisp and proportional.
    public static final float PLAYER_SIZE = 32f;
    public static final float PLAYER_DRAW_WIDTH = 32f;
    public static final float PLAYER_DRAW_HEIGHT = 64f;
    public static final float PLAYER_COLLISION_SIZE = 22f;
    public static final float PLAYER_COLLISION_WIDTH = 20f;
    public static final float PLAYER_COLLISION_HEIGHT = 26f;

    public static final float MOB_SIZE = 32f;
    public static final float AGGRO_RADIUS = TILE_SIZE * 18f;

    public static final float ROUND_BREAK_SECONDS = 30f;
    public static final float START_ROUND_DELAY_SECONDS = 2f;
}
