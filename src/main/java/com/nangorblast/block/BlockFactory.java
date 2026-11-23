package com.nangorblast.block;

import com.nangorblast.world.Position;

public class BlockFactory {
    private BlockFactory() {
    }

    public static Block createBlock(char symbol, Position pos) {
        return switch (symbol) {
            case IndestructibleBlock.SYMBOL -> new IndestructibleBlock(pos);
            case DestructibleBlock.SYMBOL -> new DestructibleBlock(pos);
            case ReinforcedBlock.SYMBOL -> new ReinforcedBlock(pos);
            case '.', 'P', 'E', 'B', 'X' -> null;
            default -> throw new IllegalArgumentException("Simbol blok tidak dikenali: '" + symbol + "'");
        };
    }
}