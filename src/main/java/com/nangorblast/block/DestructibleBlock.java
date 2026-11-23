package com.nangorblast.block;

import com.nangorblast.entity.bomb.BombType;
import com.nangorblast.powerup.PowerUp;
import com.nangorblast.powerup.PowerUpFactory;
import com.nangorblast.powerup.PowerUpType;
import com.nangorblast.world.Position;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class DestructibleBlock extends Block {
    public static final char SYMBOL = 'D';
    public static final double DROP_CHANCE = 0.30;

    private PowerUpType guaranteedPowerUp;

    public DestructibleBlock(Position pos) {
        super(pos);
    }

    public void setGuaranteedPowerUp(PowerUpType type) {
        this.guaranteedPowerUp = type;
    }

    public PowerUpType getGuaranteedPowerUp() {
        return guaranteedPowerUp;
    }

    @Override
    public boolean isDestructibleBy(BombType type) {
        return type != null;
    }

    public Optional<PowerUp> dropPowerUp() {
        if (guaranteedPowerUp != null) {
            return Optional.of(PowerUpFactory.createPowerUp(guaranteedPowerUp, position));
        }
        if (ThreadLocalRandom.current().nextDouble() < DROP_CHANCE) {
            return Optional.of(PowerUpFactory.createRandomPowerUp(position));
        }
        return Optional.empty();
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}