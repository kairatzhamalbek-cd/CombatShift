package com.pixelforge.combatshift.strategy;

/**
 * Concrete Strategy — magic (AoE) attack.
 * Hits all enemies in a radius; costs mana.
 */
public class MagicAttack implements AttackStrategy {

    private static final float DAMAGE = 35f;
    private static final float RADIUS = 120f;

    @Override
    public void attack(float x, float y) {
        System.out.printf("[MagicAttack] Casting spell at (%.0f, %.0f) — damage=%.0f, AoE radius=%.0f%n",
                x, y, DAMAGE, RADIUS);
    }

    @Override
    public String getName() {
        return "Magic";
    }
}
