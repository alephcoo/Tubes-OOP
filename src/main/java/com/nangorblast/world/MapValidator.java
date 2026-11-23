package com.nangorblast.world;

import com.nangorblast.block.Block;
import com.nangorblast.block.IndestructibleBlock;
import com.nangorblast.exception.InvalidMapException;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class MapValidator {
    public static final int REQUIRED_ENEMIES = 5;

    private MapValidator() {
    }

    public static void validate(Grid<Block> grid, Position player, Position boss, Position exit)
            throws InvalidMapException {
        if (grid == null) {
            throw new InvalidMapException("Grid peta tidak boleh null.");
        }
        if (grid.getRows() != MapLoader.DEFAULT_ROWS || grid.getCols() != MapLoader.DEFAULT_COLS) {
            throw new InvalidMapException(
                    "Ukuran peta harus tepat " + MapLoader.DEFAULT_ROWS + "x" + MapLoader.DEFAULT_COLS
                            + ", ditemukan: " + grid.getRows() + "x" + grid.getCols());
        }
        if (player == null) {
            throw new InvalidMapException("Titik spawn Player ('P') tidak ditemukan pada peta.");
        }
        if (boss == null) {
            throw new InvalidMapException("Titik spawn Boss ('B') tidak ditemukan pada peta.");
        }
        if (exit == null) {
            throw new InvalidMapException("Pintu keluar Exit ('X') tidak ditemukan pada peta.");
        }

        validateWalkableSpawn(grid, player, "Player");
        validateWalkableSpawn(grid, boss, "Boss");
        validateWalkableSpawn(grid, exit, "Exit");

        if (player.equals(boss) || player.equals(exit) || boss.equals(exit)) {
            throw new InvalidMapException("Titik spawn Player, Boss, dan Exit tidak boleh saling bertumpuk.");
        }

        if (!isReachableBFS(grid, player, boss)) {
            throw new InvalidMapException("Boss pada " + boss + " tidak dapat dijangkau dari Player pada " + player);
        }
        if (!isReachableBFS(grid, player, exit)) {
            throw new InvalidMapException("Exit pada " + exit + " tidak dapat dijangkau dari Player pada " + player);
        }
    }

    public static void validate(Map map) throws InvalidMapException {
        if (map == null) {
            throw new InvalidMapException("Objek Map tidak boleh null.");
        }

        Grid<Block> grid = map.getGrid();
        validate(grid, map.getPlayerSpawn(), map.getBossSpawn(), map.getExitPosition());

        List<Position> enemySpawns = map.getEnemySpawns();
        if (enemySpawns == null || enemySpawns.size() != REQUIRED_ENEMIES) {
            int found = (enemySpawns == null) ? 0 : enemySpawns.size();
            throw new InvalidMapException(
                    "Jumlah spawn Normal Enemy harus tepat " + REQUIRED_ENEMIES + ", ditemukan: " + found);
        }

        Set<Position> occupiedSpawns = new HashSet<>();
        occupiedSpawns.add(map.getPlayerSpawn());
        occupiedSpawns.add(map.getBossSpawn());
        occupiedSpawns.add(map.getExitPosition());

        for (Position enemyPos : enemySpawns) {
            validateWalkableSpawn(grid, enemyPos, "Normal Enemy");
            if (!occupiedSpawns.add(enemyPos)) {
                throw new InvalidMapException("Titik spawn bertumpuk pada posisi: " + enemyPos);
            }
        }
    }

    private static void validateWalkableSpawn(Grid<Block> grid, Position pos, String label)
            throws InvalidMapException {
        if (!grid.isValidPosition(pos)) {
            throw new InvalidMapException("Posisi " + label + " di luar batas grid: " + pos);
        }
        Block block = grid.get(pos);
        if (block != null && block.isActive()) {
            throw new InvalidMapException("Posisi " + label + " pada " + pos + " terhalang oleh blok.");
        }
    }

    private static boolean isReachableBFS(Grid<Block> grid, Position start, Position target) {
        if (start.equals(target)) {
            return true;
        }

        Queue<Position> queue = new ArrayDeque<>();
        Set<Position> visited = new HashSet<>();
        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            for (Direction dir : Direction.values()) {
                Position next = current.move(dir);
                if (!grid.isValidPosition(next) || visited.contains(next)) {
                    continue;
                }

                Block block = grid.get(next);
                if (block instanceof IndestructibleBlock && block.isActive()) {
                    continue;
                }

                if (next.equals(target)) {
                    return true;
                }

                visited.add(next);
                queue.offer(next);
            }
        }
        return false;
    }
}