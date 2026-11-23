package com.nangorblast.powerup;

import com.nangorblast.world.Position;

import java.util.concurrent.ThreadLocalRandom;

public class PowerUpFactory {
    private static final PowerUpType[] ALL_TYPES = PowerUpType.values();

    private PowerUpFactory() {
    }

    public static PowerUp createRandomPowerUp(Position pos) {
        PowerUpType randomType = ALL_TYPES[ThreadLocalRandom.current().nextInt(ALL_TYPES.length)];
        return new PowerUp(pos, randomType);
    }

    public static PowerUp createPowerUp(PowerUpType type, Position pos) {
        return new PowerUp(pos, type);
    }
}