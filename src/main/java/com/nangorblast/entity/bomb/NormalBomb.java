package com.nangorblast.entity.bomb;

import com.nangorblast.entity.player.Player; /*Tunggu nama akhir file Player dari anggota lain*/
import com.nangorblast.world.grid.Position; /*Tunggu nama akhri file Position dari anggota lain */

/**
 * Bom standar timer 3 detik.
 * PIC: Obert Chandra (18225039)
 * Fitur: F02.1
 */
public class NormalBomb extends Bomb {
    public static final double FUSE_TIME = 3.0;

    public NormalBomb(Position position, Player owner) {
        super(
            position,
            BombType.NORMAL,
            owner,
            FUSE_TIME,
            owner != null ? owner.getBlastRange() : 1
        );
    }

    @Override
    public boolean hasTimedFuse() {
        return true;
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