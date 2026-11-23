package com.nangorblast.world;

import com.nangorblast.block.Block;
import com.nangorblast.block.DestructibleBlock;
import com.nangorblast.block.ReinforcedBlock;
import com.nangorblast.entity.bomb.BombType;
import com.nangorblast.powerup.PowerUp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Menyimpan seluruh state spasial peta permainan, titik spawn entitas,
 * pengelolaan penghancuran blok, serta pelacakan Power-Up yang muncul.
 */
public class Map {
    private final Grid<Block> grid;
    private final Position playerSpawn;
    private final List<Position> enemySpawns;
    private final Position bossSpawn;
    private final Position exitPosition;

    private final List<PowerUp> droppedPowerUps = new ArrayList<>();
    private final Set<Position> hazardTiles = new HashSet<>();
    private final List<Consumer<Position>> blockDestroyedListeners = new ArrayList<>();

    public Map(Grid<Block> grid,
               Position playerSpawn,
               List<Position> enemySpawns,
               Position bossSpawn,
               Position exitPosition) {
        this.grid = Objects.requireNonNull(grid, "Grid tidak boleh null");
        this.playerSpawn = playerSpawn;
        this.enemySpawns = enemySpawns == null
                ? List.of()
                : Collections.unmodifiableList(new ArrayList<>(enemySpawns));
        this.bossSpawn = bossSpawn;
        this.exitPosition = exitPosition;
    }

    public boolean isWalkable(Position pos) {
        if (!grid.isValidPosition(pos)) {
            return false;
        }
        Block block = grid.get(pos);
        return block == null || !block.isActive();
    }

    public boolean isDestructible(Position pos) {
        Block block = getBlockAt(pos);
        if (block == null) {
            return false;
        }
        return block.isDestructibleBy(BombType.NORMAL)
                || block.isDestructibleBy(BombType.PUMPED)
                || block.isDestructibleBy(BombType.REMOTE);
    }

    public boolean isDestructible(Position pos, BombType type) {
        Block block = getBlockAt(pos);
        return block != null && block.isDestructibleBy(type);
    }

    public Block getBlockAt(Position pos) {
        if (!grid.isValidPosition(pos)) {
            return null;
        }
        Block block = grid.get(pos);
        return (block != null && block.isActive()) ? block : null;
    }

    public void destroyBlock(Position pos) {
        Block block = getBlockAt(pos);
        if (block == null || !isDestructible(pos)) {
            return;
        }
        deactivateAndDrop(pos, block);
    }

    public Optional<PowerUp> destroyBlock(Position pos, BombType bombType) {
        Block block = getBlockAt(pos);
        if (block == null || !block.isDestructibleBy(bombType)) {
            return Optional.empty();
        }
        if (block instanceof ReinforcedBlock reinforced) {
            if (!reinforced.applyBlast(bombType)) {
                return Optional.empty();
            }
            grid.set(pos, null);
            notifyBlockDestroyed(pos);
            return Optional.empty();
        }
        return deactivateAndDrop(pos, block);
    }

    private Optional<PowerUp> deactivateAndDrop(Position pos, Block block) {
        block.setActive(false);
        grid.set(pos, null);

        Optional<PowerUp> dropped = Optional.empty();
        if (block instanceof DestructibleBlock destructible) {
            dropped = destructible.dropPowerUp();
            dropped.ifPresent(droppedPowerUps::add);
        }

        notifyBlockDestroyed(pos);
        return dropped;
    }

    private void notifyBlockDestroyed(Position pos) {
        for (Consumer<Position> listener : blockDestroyedListeners) {
            listener.accept(pos);
        }
    }

    public boolean isHazardFreeTile(Position pos) {
        return isWalkable(pos) && !hazardTiles.contains(pos);
    }

    public void updateHazardTiles(Set<Position> hazards) {
        hazardTiles.clear();
        if (hazards != null) {
            hazardTiles.addAll(hazards);
        }
    }

    public void addBlockDestroyedListener(Consumer<Position> listener) {
        if (listener != null) {
            blockDestroyedListeners.add(listener);
        }
    }

    public List<PowerUp> consumeDroppedPowerUps() {
        List<PowerUp> result = new ArrayList<>(droppedPowerUps);
        droppedPowerUps.clear();
        return result;
    }

    public List<PowerUp> getDroppedPowerUps() {
        return Collections.unmodifiableList(droppedPowerUps);
    }

    public Grid<Block> getGrid() {
        return grid;
    }

    public Position getPlayerSpawn() {
        return playerSpawn;
    }

    public List<Position> getEnemySpawns() {
        return enemySpawns;
    }

    public Position getBossSpawn() {
        return bossSpawn;
    }

    public Position getExitPosition() {
        return exitPosition;
    }
}