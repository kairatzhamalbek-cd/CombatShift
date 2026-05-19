package com.pixelforge.combatshift.map;

public enum TileType {
    GRASS(false, false, 1f),
    LIGHT_GRASS(false, false, 1f),
    DARK_GRASS(false, false, 1f),
    DIRT(false, false, 0.96f),
    WATER(false, true, 0.60f),
    FLOWERS(false, false, 1f);

    private final boolean blocking;
    private final boolean water;
    private final float speedMultiplier;

    TileType(boolean blocking, boolean water, float speedMultiplier) {
        this.blocking = blocking;
        this.water = water;
        this.speedMultiplier = speedMultiplier;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public boolean isWater() {
        return water;
    }

    public float speedMultiplier() {
        return speedMultiplier;
    }
}

