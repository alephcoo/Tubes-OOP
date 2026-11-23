package com.nangorblast.render;

import com.nangorblast.block.Block;
import com.nangorblast.block.DestructibleBlock;
import com.nangorblast.block.IndestructibleBlock;
import com.nangorblast.block.ReinforcedBlock;
import com.nangorblast.entity.Entity;
import com.nangorblast.entity.bomb.BombType;
import com.nangorblast.entity.player.Player;
import com.nangorblast.powerup.PowerUp;
import com.nangorblast.world.Grid;
import com.nangorblast.world.Position;
import org.jline.terminal.Terminal;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Renderer {
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String DIM_GREEN = "\u001B[2;32m";
    private static final String BRIGHT_GREEN = "\u001B[92m";
    private static final String BRIGHT_RED = "\u001B[91m";
    private static final String BRIGHT_YELLOW = "\u001B[93m";
    private static final String BRIGHT_CYAN = "\u001B[96m";
    private static final String BRIGHT_MAGENTA = "\u001B[95m";
    private static final String BRIGHT_BLUE = "\u001B[94m";
    private static final String BRIGHT_WHITE = "\u001B[97m";
    private static final String GRAY = "\u001B[90m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CLEAR_SCREEN = "\u001B[2J\u001B[H";

    private final Terminal terminal;

    public Renderer(Terminal terminal) {
        this.terminal = terminal;
    }

    public void renderGame(GameStateSnapshot state) {
        if (state == null || state.map() == null) {
            renderWarning("State permainan tidak tersedia untuk dirender.");
            return;
        }

        StringBuilder buffer = new StringBuilder();
        buffer.append(CLEAR_SCREEN);
        buffer.append(buildHUDString(
                state.lives(),
                state.score(),
                state.timeLeft(),
                state.activeBomb(),
                state.remoteUnlocked()));
        buffer.append(buildGridString(
                state.map().getGrid(),
                state.entities(),
                state.explosionTiles(),
                state.warningTiles(),
                state.map().getExitPosition()));
        buffer.append(GRAY)
                .append("Kontrol: [WASD/Arrow] Gerak | [B/SPACE] Bomb | [E/R] Pilih Bomb | [Q] Detonate | [P] Pause")
                .append(RESET).append('\n');

        writeOutput(buffer.toString());
    }

    public void render(GameStateSnapshot state) {
        renderGame(state);
    }

    public void renderGrid(Grid<Block> grid, List<Entity> entities) {
        renderGrid(grid, entities, Set.of(), Set.of());
    }

    public void renderGrid(Grid<Block> grid,
                           List<Entity> entities,
                           Set<Position> explosionTiles,
                           Set<Position> warningTiles) {
        writeOutput(buildGridString(grid, entities, explosionTiles, warningTiles, null));
    }

    public void renderHUD(int lives, int score, int timeLeft, BombType activeBomb) {
        writeOutput(buildHUDString(lives, score, timeLeft, activeBomb, false));
    }

    public void renderWarning(String message) {
        String safeMessage = (message == null) ? "" : message;
        String formatted = BOLD + BRIGHT_RED + "[!] WARNING: " + safeMessage + RESET + "\n";
        writeOutput(formatted);
    }

    private String buildHUDString(int lives, int score, int timeLeft, BombType activeBomb, boolean remoteUnlocked) {
        int clampedTime = Math.max(0, timeLeft);
        int minutes = clampedTime / 60;
        int seconds = clampedTime % 60;
        String formattedTime = String.format("%02d:%02d", minutes, seconds);

        StringBuilder hearts = new StringBuilder();
        for (int i = 0; i < Math.max(0, lives); i++) {
            hearts.append("♥");
        }
        if (hearts.isEmpty()) {
            hearts.append("-");
        }

        String bombLabel = (activeBomb == null) ? BombType.NORMAL.name() : activeBomb.name();
        String remoteBadge = remoteUnlocked
                ? BRIGHT_GREEN + " [REMOTE READY]" + RESET
                : GRAY + " [REMOTE LOCKED]" + RESET;

        return BOLD + BRIGHT_GREEN + "=== NANGOR BLAST :: GREEN GARDEN ===" + RESET + "\n"
                + BRIGHT_RED + "Nyawa: " + hearts + RESET
                + "  |  " + BRIGHT_YELLOW + "Skor: " + score + RESET
                + "  |  " + BRIGHT_CYAN + "Waktu: " + formattedTime + RESET
                + "  |  " + BRIGHT_WHITE + "Bomb: " + bombLabel + RESET
                + remoteBadge + "\n"
                + GRAY + "--------------------------------------------------------------" + RESET + "\n";
    }

    private String buildGridString(Grid<Block> grid,
                                   List<Entity> entities,
                                   Set<Position> explosionTiles,
                                   Set<Position> warningTiles,
                                   Position exitPosition) {
        if (grid == null) {
            return "";
        }

        Set<Position> safeExplosions = (explosionTiles == null) ? Set.of() : explosionTiles;
        Set<Position> safeWarnings = (warningTiles == null) ? Set.of() : warningTiles;

        java.util.Map<Position, Entity> actorLayer = new HashMap<>();
        java.util.Map<Position, Entity> bombLayer = new HashMap<>();
        java.util.Map<Position, PowerUp> powerUpLayer = new HashMap<>();

        if (entities != null) {
            for (Entity entity : entities) {
                if (entity == null || !entity.isActive() || entity.getPosition() == null) {
                    continue;
                }
                Position pos = entity.getPosition();
                if (entity instanceof Player) {
                    actorLayer.put(pos, entity);
                } else if (entity instanceof PowerUp powerUp) {
                    powerUpLayer.put(pos, powerUp);
                } else if (entity instanceof Block) {
                    // Blok dirender langsung dari grid
                } else {
                    String name = entity.getClass().getSimpleName().toLowerCase();
                    if (name.contains("bomb")) {
                        bombLayer.put(pos, entity);
                    } else {
                        actorLayer.putIfAbsent(pos, entity);
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                Position pos = new Position(row, col);
                sb.append(resolveTileVisual(pos, grid, actorLayer, bombLayer, powerUpLayer,
                        safeExplosions, safeWarnings, exitPosition));
                sb.append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private String resolveTileVisual(Position pos,
                                     Grid<Block> grid,
                                     java.util.Map<Position, Entity> actorLayer,
                                     java.util.Map<Position, Entity> bombLayer,
                                     java.util.Map<Position, PowerUp> powerUpLayer,
                                     Set<Position> explosionTiles,
                                     Set<Position> warningTiles,
                                     Position exitPosition) {
        if (explosionTiles.contains(pos)) {
            return BOLD + BRIGHT_RED + "*" + RESET;
        }
        if (warningTiles.contains(pos)) {
            return BOLD + BRIGHT_YELLOW + "!" + RESET;
        }
        Entity actor = actorLayer.get(pos);
        if (actor != null) {
            if (actor instanceof Player) {
                return BOLD + BRIGHT_CYAN + "P" + RESET;
            }
            String name = actor.getClass().getSimpleName().toLowerCase();
            if (name.contains("boss") || name.contains("draco")) {
                return BOLD + BRIGHT_MAGENTA + "B" + RESET;
            }
            return BOLD + BRIGHT_RED + "E" + RESET;
        }
        if (bombLayer.containsKey(pos)) {
            return BOLD + BRIGHT_WHITE + "o" + RESET;
        }
        PowerUp powerUp = powerUpLayer.get(pos);
        if (powerUp != null) {
            return BOLD + BRIGHT_GREEN + powerUp.getType().getSymbol() + RESET;
        }
        Block block = grid.get(pos);
        if (block != null && block.isActive()) {
            if (block instanceof IndestructibleBlock) {
                return GRAY + IndestructibleBlock.SYMBOL + RESET;
            }
            if (block instanceof DestructibleBlock) {
                return YELLOW + DestructibleBlock.SYMBOL + RESET;
            }
            if (block instanceof ReinforcedBlock) {
                return BOLD + BRIGHT_BLUE + ReinforcedBlock.SYMBOL + RESET;
            }
            return String.valueOf(block.getSymbol());
        }
        if (pos.equals(exitPosition)) {
            return BOLD + BRIGHT_GREEN + "X" + RESET;
        }
        return DIM_GREEN + "." + RESET;
    }

    private void writeOutput(String content) {
        if (terminal != null) {
            PrintWriter writer = terminal.writer();
            writer.print(content);
            writer.flush();
        } else {
            System.out.print(content);
            System.out.flush();
        }
    }
}