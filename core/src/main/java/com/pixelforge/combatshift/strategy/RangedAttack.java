package com.pixelforge.combatshift.strategy;

/**
 * Concrete Strategy — ranged (bow/gun) attack.
 * Lower damage per hit, safe distance from enemy.
 */
public class RangedAttack implements AttackStrategy {

    private static final float DAMAGE = 20f;
    private static final float RANGE  = 300f;

    @Override
    public void attack(float x, float y) {
        System.out.printf("[RangedAttack] Shooting arrow at (%.0f, %.0f) — damage=%.0f, range=%.0f%n",
                x, y, DAMAGE, RANGE);
    }

    @Override
    public String getName() {
        return "Ranged";
    }
}
