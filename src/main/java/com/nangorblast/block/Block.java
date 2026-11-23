package com.nangorblast.block;

import com.nangorblast.entity.Entity;
import com.nangorblast.entity.bomb.BombType;
import com.nangorblast.world.Position;

import java.util.Objects;

public abstract class Block implements Entity {
    protected Position position;
    protected boolean active = true;

    public Block(Position position) {
        this.position = Objects.requireNonNull(position, "Position blok tidak boleh null");
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract boolean isDestructibleBy(BombType type);

    public abstract char getSymbol();
}