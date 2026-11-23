package com.nangorblast.world;

import com.nangorblast.block.Block;
import com.nangorblast.block.BlockFactory;
import com.nangorblast.block.DestructibleBlock;
import com.nangorblast.exception.InvalidMapException;
import com.nangorblast.powerup.PowerUpType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MapLoader {
    public static final int DEFAULT_ROWS = 10;
    public static final int DEFAULT_COLS = 14;

    private MapLoader() {
    }

    public static Map loadMap(String filePath) throws InvalidMapException {
        if (filePath == null || filePath.isBlank()) {
            throw new InvalidMapException("Path file peta tidak boleh null atau kosong.");
        }

        List<String> rawLines;
        try {
            rawLines = Files.readAllLines(Path.of(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new InvalidMapException("Gagal membaca file peta: " + filePath + " (" + e.getMessage() + ")");
        }

        List<String> lines = new ArrayList<>();
        for (String line : rawLines) {
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }

        if (lines.size() != DEFAULT_ROWS) {
            throw new InvalidMapException(
                    "Jumlah baris peta harus " + DEFAULT_ROWS + ", ditemukan: " + lines.size());
        }

        Grid<Block> grid = new Grid<>(DEFAULT_ROWS, DEFAULT_COLS);
        Position playerSpawn = null;
        Position bossSpawn = null;
        Position exitPosition = null;
        List<Position> enemySpawns = new ArrayList<>();
        List<DestructibleBlock> destructibleBlocks = new ArrayList<>();

        for (int row = 0; row < DEFAULT_ROWS; row++) {
            String line = lines.get(row);
            if (line.length() != DEFAULT_COLS) {
                throw new InvalidMapException(
                        "Panjang baris ke-" + (row + 1) + " harus " + DEFAULT_COLS
                                + " karakter, ditemukan: " + line.length());
            }

            for (int col = 0; col < DEFAULT_COLS; col++) {
                char symbol = line.charAt(col);
                Position pos = new Position(row, col);

                switch (symbol) {
                    case '#', 'D', 'R' -> {
                        Block block = BlockFactory.createBlock(symbol, pos);
                        grid.set(pos, block);
                        if (block instanceof DestructibleBlock destructible) {
                            destructibleBlocks.add(destructible);
                        }
                    }
                    case '.' -> grid.set(pos, null);
                    case 'P' -> {
                        if (playerSpawn != null) {
                            throw new InvalidMapException("Ditemukan lebih dari satu Player Spawn ('P').");
                        }
                        playerSpawn = pos;
                    }
                    case 'E' -> enemySpawns.add(pos);
                    case 'B' -> {
                        if (bossSpawn != null) {
                            throw new InvalidMapException("Ditemukan lebih dari satu Boss Spawn ('B').");
                        }
                        bossSpawn = pos;
                    }
                    case 'X' -> {
                        if (exitPosition != null) {
                            throw new InvalidMapException("Ditemukan lebih dari satu Exit Door ('X').");
                        }
                        exitPosition = pos;
                    }
                    default -> throw new InvalidMapException(
                            "Simbol tidak dikenali '" + symbol + "' pada baris " + (row + 1) + ", kolom " + (col + 1));
                }
            }
        }

        if (destructibleBlocks.isEmpty()) {
            throw new InvalidMapException(
                    "Peta harus memiliki minimal 1 DestructibleBlock ('D') untuk menjamin Remote Detonator.");
        }

        Map map = new Map(grid, playerSpawn, enemySpawns, bossSpawn, exitPosition);
        MapValidator.validate(map);
        assignGuaranteedRemote(destructibleBlocks);
        return map;
    }

    private static void assignGuaranteedRemote(List<DestructibleBlock> blocks) {
        if (blocks == null || blocks.isEmpty()) {
            return;
        }
        int chosenIndex = ThreadLocalRandom.current().nextInt(blocks.size());
        blocks.get(chosenIndex).setGuaranteedPowerUp(PowerUpType.REMOTE_DETONATOR);
    }
}