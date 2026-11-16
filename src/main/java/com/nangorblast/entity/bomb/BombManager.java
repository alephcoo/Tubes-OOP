package com.nangorblast.entity.bomb;

import com.nangorblast.entity.enemy.Enemy;
import com.nangorblast.entity.player.Player;
import com.nangorblast.world.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Pengelola seluruh Bomb aktif, Explosion aktif, remote detonate, dan chain reaction.
 * PIC: Obert Chandra (18225039)
 * Fitur: F02, F03
 */
public class BombManager {
    private List<Bomb> bombs;
    private List<Explosion> explosions;
    private int maxChainDepth;

    public BombManager() {
        this.bombs = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.maxChainDepth = 10;
    }

    public void addBomb(Bomb bomb) {
        if (bomb != null) {
            bombs.add(bomb);
        }
    }

    public void update(double deltaTime, Map map, Player player, List<Enemy> enemies) {
        // TODO: Lanjut Milestone 2
    }

    private void triggerChain(Explosion explosion, int depth) {
        // TODO: Lanjut Milestone 2
    }

    public void detonateRemoteBombs(Player owner, Map map) {
        // TODO: Lanjut Milestone 2
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public List<Explosion> getExplosions() {
        return explosions;
    }

    public void clear() {
        bombs.clear();
        explosions.clear();
    }
}