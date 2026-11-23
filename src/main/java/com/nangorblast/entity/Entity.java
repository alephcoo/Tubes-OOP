package com.nangorblast.entity;

import com.nangorblast.world.Position;

/**
 * Kontrak dasar bagi seluruh entitas yang memiliki posisi pada peta permainan.
 */
public interface Entity {
    Position getPosition();
    boolean isActive();
}