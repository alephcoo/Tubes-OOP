package com.nangorblast.render;

import com.nangorblast.entity.Entity;
import com.nangorblast.entity.bomb.BombType;
import com.nangorblast.world.Map;
import com.nangorblast.world.Position;

import java.util.List;
import java.util.Set;

public record GameStateSnapshot(
        Map map,
        List<Entity> entities,
        Set<Position> explosionTiles,
        Set<Position> warningTiles,
        int lives,
        int score,
        int timeLeft,
        BombType activeBomb,
        boolean remoteUnlocked
) {
    public GameStateSnapshot {
        entities = (entities == null) ? List.of() : List.copyOf(entities);
        explosionTiles = (explosionTiles == null) ? Set.of() : Set.copyOf(explosionTiles);
        warningTiles = (warningTiles == null) ? Set.of() : Set.copyOf(warningTiles);
        activeBomb = (activeBomb == null) ? BombType.NORMAL : activeBomb;
    }
}