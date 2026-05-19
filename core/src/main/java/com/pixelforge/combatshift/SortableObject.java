package com.pixelforge.combatshift;

public class SortableObject {
    public final float y;
    public final Runnable drawAction;

    public SortableObject(float y, Runnable drawAction) {
        this.y = y;
        this.drawAction = drawAction;
    }
}
