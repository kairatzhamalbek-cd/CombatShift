package com.pixelforge.combatshift;

public final class Constants {
    private Constants() {}

    public static final float TILE_SIZE = 32f;
    public static final int MAP_WIDTH_TILES = 80;
    public static final int MAP_HEIGHT_TILES = 80;

    public static final float WORLD_WIDTH = MAP_WIDTH_TILES * TILE_SIZE;
    public static final float WORLD_HEIGHT = MAP_HEIGHT_TILES * TILE_SIZE;

    public static final float PLAYER_SIZE = 22f;
    public static final float PLAYER_SPEED = 250f;   // пикселей в секунду
}
