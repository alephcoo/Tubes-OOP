package com.nangorblast.entity.bomb;

import com.nangorblast.entity.Damageable;
import com.nangorblast.entity.enemy.Enemy;
import com.nangorblast.entity.player.Player;
import com.nangorblast.world.Map;
import com.nangorblast.world.grid.Position;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Efek ledakan bom beserta jangkauan dan damage ledakan.
 * PIC: Obert Chandra (18225039)
 * Fitur: F03
 */
public class Explosion {
    private Position position;
    private int blastRange;
    private double duration;
    private Player owner;
    private BombType sourceType;
    private int bossDamage;
    private boolean active;
    private Set<Position> affectedTiles;
    private Set<Damageable> hitEntities;

    private static final double DURATION = 0.5;

    public Explosion(Bomb sourceBomb, Map map) {
        this.position = sourceBomb.getPosition();
        this.blastRange = sourceBomb.getBlastRange();
        this.duration = DURATION;
        this.owner = sourceBomb.getOwner();
        this.sourceType = sourceBomb.getType();
        this.bossDamage = sourceBomb.getBossDamage();
        this.active = true;
        this.affectedTiles = new HashSet<>();
        this.hitEntities = new HashSet<>();
        calculateSpread(map);
    }

    public Position getPosition() {
        return position;
    }

    public Player getOwner() {
        return owner;
    }

    public Set<Position> getAffectedTiles() {
        return affectedTiles;
    }

    public boolean isActive() {
        return active;
    }

    private void calculateSpread(Map map) {
        // TODO: Lanjut Milestone 2
        if (position != null) {
            affectedTiles.add(position);
        }
    }

    public void applyEffects(Map map, Player player, List<Enemy> enemies) {
        // TODO: Lanjut Milestone 2
    }

    public boolean update(double deltaTime) {
        // TODO: Lanjut Milestone 2
        return active;
    }

    public boolean containsTile(Position pos) {
        return affectedTiles.contains(pos);
    }

    private boolean tryDamage(Damageable target) {
        if (target == null || hitEntities.contains(target)) {
            return false;
        }
        hitEntities.add(target);
        return true;
    }
}