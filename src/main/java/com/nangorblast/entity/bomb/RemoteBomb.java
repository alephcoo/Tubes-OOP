package com.nangorblast.entity.bomb;

import com.nangorblast.entity.player.Player;
import com.nangorblast.world.grid.Position;

/**
 * Bom jarak jauh yang diledakkan oleh Player.
 * PIC: Obert Chandra (18225039)
 * Fitur: F02.3
 */
public class RemoteBomb extends Bomb {
    public RemoteBomb(Position position, Player owner) {
        super(
            position,
            BombType.REMOTE,
            owner,
            -1.0,
            owner != null ? owner.getBlastRange() : 1
        );
    }

    @Override
    public boolean hasTimedFuse() {
        return false;
    }

    @Override
    public boolean canDestroyReinforcedBlock() {
        return false;
    }

    @Override
    public int getBossDamage() {
        return 1;
    }
}