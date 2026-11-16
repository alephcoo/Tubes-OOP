package com.nangorblast.entity.bomb;

import com.nangorblast.entity.player.Player;
import com.nangorblast.world.grid.Position;

/**
 * Bom yang bisa menghancurkan ReinforcedBlock.
 * PIC: Obert Chandra (18225039)
 * Fitur: F02.2
 */
public class PumpedBomb extends Bomb {
    public static final double FUSE_TIME = 4.5;
    public static final int RANGE_BONUS = 2;
    public static final int MAX_RANGE = 7;

    public PumpedBomb(Position position, Player owner) {
        super(
            position,
            BombType.PUMPED,
            owner,
            FUSE_TIME,
            owner != null ? Math.min(MAX_RANGE, owner.getBlastRange() + RANGE_BONUS) : 1 + RANGE_BONUS
        );
    }

    @Override
    public boolean hasTimedFuse() {
        return true;
    }

    @Override
    public boolean canDestroyReinforcedBlock() {
        return true;
    }

    @Override
    public int getBossDamage() {
        return 2;
    }
}