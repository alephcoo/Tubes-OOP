package com.nangorblast.entity.bomb;

import com.nangorblast.entity.player.Player;
import com.nangorblast.world.grid.Position;

/**
 * Pembentukan jenis Bomb sesuai BombType.
 * PIC: Obert Chandra (18225039)
 * Fitur: F02
 */
public class BombFactory {
    public static Bomb createBomb(BombType type, Position position, Player owner) {
        if (type == null) {
            return new NormalBomb(position, owner);
        }
        switch (type) {
            case PUMPED:
                return new PumpedBomb(position, owner);
            case REMOTE:
                return new RemoteBomb(position, owner);
            case NORMAL:
            default:
                return new NormalBomb(position, owner);
        }
    }
}