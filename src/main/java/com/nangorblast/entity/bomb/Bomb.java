package com.nangorblast.entity.bomb;

import com.nangorblast.entity.player.Player;
import com.nangorblast.world.grid.Position;

/**
 * Basis semua jenis bom.
 * PIC: Obert Chandra (18225039)
 * Fitur: F02
 */
public abstract class Bomb {
    protected Position position;
    protected BombType type;
    protected Player owner;
    protected double fuseTime;
    protected int blastRange;
    protected boolean active;

    public Bomb(Position position, BombType type, Player owner, double fuseTime, int blastRange) {
        this.position = position;
        this.type = type;
        this.owner = owner;
        this.fuseTime = fuseTime;
        this.blastRange = blastRange;
        this.active = true;
    }

    public Position getPosition() {
        return position;
    }

    public BombType getType() {
        return type;
    }

    public Player getOwner() {
        return owner;
    }

    public int getBlastRange() {
        return blastRange;
    }

    public boolean isActive() {
        return active;
    }

    public boolean update(double deltaTime) {
        // TODO: Implementasi countdown fuseTime untuk bom bertimer (Milestone 2)
        return false;
    }

    public void trigger() {
        // TODO: Implementasi pemicu ledakan instan / chain reaction (Milestone 2)
    }

    public void deactivate() {
        this.active = false;
    }

    public abstract boolean hasTimedFuse();
    public abstract boolean canDestroyReinforcedBlock();
    public abstract int getBossDamage();
}