package com.pixelforge.combatshift.strategy;

/**
 * Concrete Strategy — melee (close-range) attack.
 * High damage, requires proximity to enemy.
 */
public class MeleeAttack implements AttackStrategy {

    private static final float DAMAGE = 50f;
    private static final float RANGE  = 60f;

    @Override
    public void attack(float x, float y) {
        System.out.printf("[MeleeAttack] Swinging sword at (%.0f, %.0f) — damage=%.0f, range=%.0f%n",
                x, y, DAMAGE, RANGE);
    }

    @Override
    public String getName() {
        return "Melee";
    }
}
