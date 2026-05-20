package com.pixelforge.combatshift;

import com.badlogic.gdx.Game;

public class MainGame extends Game {
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));   // ← Главное меню при запуске
    }
}
