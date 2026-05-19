package com.pixelforge.combatshift.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class InputHandler extends InputAdapter {
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean shield;
    private boolean dodgeRequested;
    private boolean attackRequested;
    private int selectSlot = -1;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                up = true;
                return true;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                down = true;
                return true;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                left = true;
                return true;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                right = true;
                return true;
            case Input.Keys.SPACE:
                dodgeRequested = true;
                return true;
            case Input.Keys.NUM_1:
                selectSlot = 0;
                return true;
            case Input.Keys.NUM_2:
                selectSlot = 1;
                return true;
            case Input.Keys.NUM_3:
                selectSlot = 2;
                return true;
            case Input.Keys.NUM_4:
                selectSlot = 3;
                return true;
            case Input.Keys.NUM_5:
                selectSlot = 4;
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                up = false;
                return true;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                down = false;
                return true;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                left = false;
                return true;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                right = false;
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            attackRequested = true;
            return true;
        }
        if (button == Input.Buttons.RIGHT) {
            shield = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) {
            shield = false;
            return true;
        }
        return false;
    }

    public float horizontalAxis() {
        float value = 0f;
        if (left) value -= 1f;
        if (right) value += 1f;
        return value;
    }

    public float verticalAxis() {
        float value = 0f;
        if (down) value -= 1f;
        if (up) value += 1f;
        return value;
    }

    public boolean consumeAttackRequested() {
        boolean value = attackRequested;
        attackRequested = false;
        return value;
    }

    public boolean isShieldHeld() {
        return shield;
    }

    public boolean consumeDodgeRequested() {
        boolean value = dodgeRequested;
        dodgeRequested = false;
        return value;
    }

    public int consumeSelectSlot() {
        int value = selectSlot;
        selectSlot = -1;
        return value;
    }
}

