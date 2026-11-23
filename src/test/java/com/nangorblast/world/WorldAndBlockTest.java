package com.nangorblast.world;

import com.nangorblast.block.Block;
import com.nangorblast.block.BlockFactory;
import com.nangorblast.block.DestructibleBlock;
import com.nangorblast.block.IndestructibleBlock;
import com.nangorblast.block.ReinforcedBlock;
import com.nangorblast.entity.bomb.BombType;
import com.nangorblast.exception.InvalidMapException;
import com.nangorblast.powerup.PowerUp;
import com.nangorblast.powerup.PowerUpFactory;
import com.nangorblast.powerup.PowerUpType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test JUnit 5 untuk domain Orang 2 (World, Block, dan PowerUp).
 */
class WorldAndBlockTest {

    private static final String VALID_MAP_A = """
            ##############
            #P..D...R...X#
            #.#D#.#.#.#E.#
            #.E.R..D..R..#
            ###.###.###..#
            #D..R...D.E..#
            #.###.###.##.#
            #.###.#...B..#
            #E....D.....E#
            ##############
            """;

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("F00.3 & F07: MapLoader memuat peta 10x14 valid dan menjamin 1 Remote Detonator")
    void testMapLoaderValidMapAndGuaranteedRemote() throws Exception {
        Path mapFile = tempDir.resolve("map_a.txt");
        Files.writeString(mapFile, VALID_MAP_A);

        Map map = MapLoader.loadMap(mapFile.toString());

        assertNotNull(map);
        assertEquals(10, map.getGrid().getRows());
        assertEquals(14, map.getGrid().getCols());
        assertEquals(new Position(1, 1), map.getPlayerSpawn());
        assertEquals(new Position(7, 10), map.getBossSpawn());
        assertEquals(new Position(1, 12), map.getExitPosition());
        assertEquals(MapValidator.REQUIRED_ENEMIES, map.getEnemySpawns().size());

        int guaranteedRemoteCount = 0;
        for (int r = 0; r < map.getGrid().getRows(); r++) {
            for (int c = 0; c < map.getGrid().getCols(); c++) {
                Block block = map.getGrid().get(new Position(r, c));
                if (block instanceof DestructibleBlock db
                        && db.getGuaranteedPowerUp() == PowerUpType.REMOTE_DETONATOR) {
                    guaranteedRemoteCount++;
                }
            }
        }
        assertEquals(1, guaranteedRemoteCount);
    }

    @Test
    @DisplayName("F00.3: MapValidator melempar InvalidMapException saat jalur BFS ke Boss terblokir '#'")
    void testMapValidatorFailsWhenBossUnreachableBFS() throws IOException {
        String blockedBossMap = """
                ##############
                #P..D...R...X#
                #.#D#.#.#.#E.#
                #.E.R..D..R..#
                ###.###.###..#
                #D..R...D.E..#
                #.###.###.####
                #.###.#..#B#.#
                #E....D..###E#
                ##############
                """;
        Path mapFile = tempDir.resolve("blocked_boss.txt");
        Files.writeString(mapFile, blockedBossMap);

        InvalidMapException ex = assertThrows(
                InvalidMapException.class,
                () -> MapLoader.loadMap(mapFile.toString())
        );
        assertTrue(ex.getMessage().toLowerCase().contains("boss"));
    }

    @Test
    @DisplayName("F00.3: MapValidator melempar InvalidMapException saat jalur BFS ke Exit terblokir '#'")
    void testMapValidatorFailsWhenExitUnreachableBFS() {
        Grid<Block> grid = new Grid<>(10, 14);
        Position player = new Position(1, 1);
        Position boss = new Position(1, 3);
        Position exit = new Position(8, 12);

        for (Direction d : Direction.values()) {
            Position wallPos = exit.move(d);
            if (grid.isValidPosition(wallPos)) {
                grid.set(wallPos, new IndestructibleBlock(wallPos));
            }
        }

        InvalidMapException ex = assertThrows(
                InvalidMapException.class,
                () -> MapValidator.validate(grid, player, boss, exit)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("exit"));
    }

    @Test
    @DisplayName("F04: BlockFactory memetakan '#', 'D', 'R', dan tile kosong/spawn dengan tepat")
    void testBlockFactoryCreation() {
        Position pos = new Position(2, 2);

        assertInstanceOf(IndestructibleBlock.class, BlockFactory.createBlock('#', pos));
        assertInstanceOf(DestructibleBlock.class, BlockFactory.createBlock('D', pos));
        assertInstanceOf(ReinforcedBlock.class, BlockFactory.createBlock('R', pos));

        assertNull(BlockFactory.createBlock('.', pos));
        assertNull(BlockFactory.createBlock('P', pos));
        assertNull(BlockFactory.createBlock('E', pos));
        assertNull(BlockFactory.createBlock('B', pos));
        assertNull(BlockFactory.createBlock('X', pos));

        assertThrows(IllegalArgumentException.class, () -> BlockFactory.createBlock('Z', pos));
    }

    @Test
    @DisplayName("F04.2.3: ReinforcedBlock hanya hancur oleh BombType.PUMPED")
    void testReinforcedBlockOnlyDestructibleByPumpedBomb() {
        Position pos = new Position(3, 4);
        ReinforcedBlock block = new ReinforcedBlock(pos);

        assertFalse(block.isDestructibleBy(BombType.NORMAL));
        assertFalse(block.isDestructibleBy(BombType.REMOTE));
        assertTrue(block.isDestructibleBy(BombType.PUMPED));

        assertFalse(block.applyBlast(BombType.NORMAL));
        assertTrue(block.isActive());

        assertTrue(block.applyBlast(BombType.PUMPED));
        assertFalse(block.isActive());
    }

    @Test
    @DisplayName("F06: DestructibleBlock dengan jaminan REMOTE_DETONATOR pasti menjatuhkan item 'r'")
    void testGuaranteedRemoteDetonatorDrop() {
        Position blockPos = new Position(1, 4);
        DestructibleBlock block = new DestructibleBlock(blockPos);
        block.setGuaranteedPowerUp(PowerUpType.REMOTE_DETONATOR);

        Optional<PowerUp> dropped = block.dropPowerUp();
        assertTrue(dropped.isPresent());
        PowerUp powerUp = dropped.get();
        assertEquals(PowerUpType.REMOTE_DETONATOR, powerUp.getType());
        assertEquals('r', powerUp.getType().getSymbol());
        assertEquals(blockPos, powerUp.getPosition());
        assertTrue(powerUp.isActive());

        assertNotNull(PowerUpFactory.createRandomPowerUp(blockPos));
    }

    @Test
    @DisplayName("F07: Map.destroyBlock menonaktifkan blok, memicu listener, dan membuka tile")
    void testMapDestroyBlockTriggersListenerAndWalkable() {
        Grid<Block> grid = new Grid<>(10, 14);
        Position targetPos = new Position(2, 3);
        DestructibleBlock db = new DestructibleBlock(targetPos);
        db.setGuaranteedPowerUp(PowerUpType.EXTRA_BOMB);
        grid.set(targetPos, db);

        Map map = new Map(
                grid,
                new Position(1, 1),
                List.of(new Position(1, 2), new Position(1, 3), new Position(1, 4),
                        new Position(1, 5), new Position(1, 6)),
                new Position(7, 10),
                new Position(1, 12)
        );

        List<Position> destroyedEvents = new ArrayList<>();
        map.addBlockDestroyedListener(destroyedEvents::add);

        assertFalse(map.isWalkable(targetPos));
        map.destroyBlock(targetPos);

        assertTrue(map.isWalkable(targetPos));
        assertNull(map.getBlockAt(targetPos));
        assertEquals(1, destroyedEvents.size());
        assertEquals(targetPos, destroyedEvents.get(0));
    }
}