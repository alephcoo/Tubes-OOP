package com.nangorblast.powerup;

import com.nangorblast.entity.Entity;
import com.nangorblast.entity.player.Player;
import com.nangorblast.world.Position;

import java.util.Objects;

public class PowerUp implements Entity, PowerUpEffect {
    private final Position position;
    private final PowerUpType type;
    private boolean active = true;

    public PowerUp(Position pos, PowerUpType type) {
        this.position = Objects.requireNonNull(pos, "Position PowerUp tidak boleh null");
        this.type = Objects.requireNonNull(type, "PowerUpType tidak boleh null");
    }

    @Override
    public void applyTo(Player player) {
        if (!active || player == null) {
            return;
        }
        switch (type) {
            case EXTRA_BOMB -> player.increaseBombCapacity();
            case BLAST_RANGE -> player.increaseBlastRange();
            case SPEED_UP -> player.increaseSpeed();
            case REMOTE_DETONATOR -> player.unlockRemoteBomb();
            case EXTRA_LIFE -> player.addLife();
        }
        this.active = false;
    }

    public PowerUpType getType() {
        return type;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isActive() {
        return active;
    }
}