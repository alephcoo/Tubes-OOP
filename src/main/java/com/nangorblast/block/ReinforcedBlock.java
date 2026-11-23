package com.nangorblast.block;

import com.nangorblast.entity.bomb.BombType;
import com.nangorblast.world.Position;

public class ReinforcedBlock extends Block {
    public static final char SYMBOL = 'R';

    private int hitPoints = 1;

    public ReinforcedBlock(Position pos) {
        super(pos);
    }

    @Override
    public boolean isDestructibleBy(BombType type) {
        return type == BombType.PUMPED;
    }

    public boolean applyBlast(BombType type) {
        if (!active || !isDestructibleBy(type)) {
            return false;
        }
        hitPoints--;
        if (hitPoints <= 0) {
            hitPoints = 0;
            setActive(false);
            return true;
        }
        return false;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}