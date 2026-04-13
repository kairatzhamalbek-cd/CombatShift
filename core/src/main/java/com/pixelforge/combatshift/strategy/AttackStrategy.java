package com.pixelforge.combatshift.strategy;

/**
 * Strategy Pattern — defines a family of attack algorithms.
 * Allows switching combat behavior at runtime.
 */
public interface AttackStrategy {
    void attack(float x, float y);
    String getName();
}
