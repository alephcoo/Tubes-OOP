package com.nangorblast.powerup;

public enum PowerUpType {
    EXTRA_BOMB('+'),
    BLAST_RANGE('f'),
    SPEED_UP('s'),
    REMOTE_DETONATOR('r'),
    EXTRA_LIFE('h');

    private final char symbol;

    PowerUpType(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}