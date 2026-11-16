package com.nangorblast.entity.player;

import com.nangorblast.entity.Damageable;
import com.nangorblast.entity.Entity;
import com.nangorblast.entity.bomb.Bomb;
import com.nangorblast.entity.bomb.BombManager;
import com.nangorblast.entity.bomb.BombType;
import com.nangorblast.entity.enemy.Enemy;
import com.nangorblast.world.Map;
import com.nangorblast.world.grid.Direction;
import com.nangorblast.world.grid.Position;
import java.util.List;

/**
 * Atribut, pergerakan, bom, dan respawn untuk Player.
 * PIC: Obert Chandra (18225039)
 * Fitur: F01
 */
public class Player implements Entity, Damageable {
    private Position position;
    private Position spawnPosition;
    private Direction direction;
    private int lives;
    private int bombCapacity;
    private int activeBombs;
    private int blastRange;
    private double baseSpeed;
    private double speedMultiplier;
    private boolean invulnerable;
    private double invulnerableTimer;
    private boolean respawnPending;
    private double respawnTimer;
    private boolean remoteUnlocked;
    private BombType selectedBombType;
    private Position walkableBombPosition;

    private static final int INITIAL_LIVES = 3;
    private static final int MAX_LIVES = 5;
    private static final int MAX_BOMB_CAPACITY = 5;
    private static final int MAX_BLAST_RANGE = 8;
    private static final double INVULNERABLE_DURATION = 1.5;

    public Player(Position spawnPosition) {
        this.spawnPosition = spawnPosition;
        this.position = spawnPosition;
        this.direction = Direction.DOWN;
        this.lives = INITIAL_LIVES;
        this.bombCapacity = 1;
        this.activeBombs = 0;
        this.blastRange = 1;
        this.baseSpeed = 1.0;
        this.speedMultiplier = 1.0;
        this.invulnerable = false;
        this.invulnerableTimer = 0.0;
        this.respawnPending = false;
        this.respawnTimer = 0.0;
        this.remoteUnlocked = false;
        this.selectedBombType = BombType.NORMAL;
        this.walkableBombPosition = null;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public Position getSpawnPosition() {
        return spawnPosition;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getLives() {
        return lives;
    }

    public int getBombCapacity() {
        return bombCapacity;
    }

    public int getActiveBombs() {
        return activeBombs;
    }

    public int getBlastRange() {
        return blastRange;
    }

    public double getSpeed() {
        return baseSpeed * speedMultiplier;
    }

    public BombType getSelectedBombType() {
        return selectedBombType;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public boolean isRemoteUnlocked() {
        return remoteUnlocked;
    }

    public boolean isRespawnPending() {
        return respawnPending;
    }

    @Override
    public boolean isAlive() {
        return lives > 0;
    }

    public void setDirection(Direction dir) {
        if (dir != null) {
            this.direction = dir;
        }
    }

    public boolean move(Direction dir, Map map, List<Bomb> bombs, List<Enemy> enemies) {
        // TODO: Milestone 2
        return false;
    }

    private boolean canMoveTo(Position target, Map map) {
        // TODO: Milestone 2
        return false;
    }

    private boolean hasEnemyAt(Position target, List<Enemy> enemies) {
        // TODO: Milestone 2
        return false;
    }

    private boolean isBlockedByBomb(Position target, List<Bomb> bombs) {
        // TODO: Milestone 2
        return false;
    }

    public Bomb placeBomb(BombManager bombManager) {
        // TODO: Milestone 2
        return null;
    }

    public void onBombExploded() {
        if (activeBombs > 0) {
            activeBombs--;
        }
    }

    @Override
    public void takeDamage(int amount) {
        // TODO: Milestone 2
    }

    public void respawn(Map map, List<Bomb> bombs, List<Enemy> enemies) {
        // TODO: Milestone 2
    }

    public void update(double deltaTime) {
        // TODO: Milestone 2
    }

    public void reset() {
        this.position = spawnPosition;
        this.direction = Direction.DOWN;
        this.lives = INITIAL_LIVES;
        this.bombCapacity = 1;
        this.activeBombs = 0;
        this.blastRange = 1;
        this.speedMultiplier = 1.0;
        this.invulnerable = false;
        this.invulnerableTimer = 0.0;
        this.respawnPending = false;
        this.respawnTimer = 0.0;
        this.remoteUnlocked = false;
        this.selectedBombType = BombType.NORMAL;
        this.walkableBombPosition = null;
    }

    public void increaseBombCapacity() {
        if (bombCapacity < MAX_BOMB_CAPACITY) {
            bombCapacity++;
        }
    }

    public void increaseBlastRange() {
        if (blastRange < MAX_BLAST_RANGE) {
            blastRange++;
        }
    }

    public void increaseSpeed() {
        this.speedMultiplier += 0.2;
    }

    public void unlockRemoteBomb() {
        this.remoteUnlocked = true;
    }

    public void selectBombType(BombType type) {
        if (type == null) {
            return;
        }
        if (type == BombType.REMOTE && !remoteUnlocked) {
            return;
        }
        this.selectedBombType = type;
    }

    public void addLife() {
        if (lives < MAX_LIVES) {
            lives++;
        }
    }
}