package com.nangorblast.block;

import com.nangorblast.entity.bomb.BombType;
import com.nangorblast.world.Position;

public class IndestructibleBlock extends Block {
    public static final char SYMBOL = '#';

    public IndestructibleBlock(Position pos) {
        super(pos);
    }

    @Override
    public boolean isDestructibleBy(BombType type) {
        return false;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}