package com.nangorblast.world;

import java.util.Objects;

/**
 * Merepresentasikan koordinat 0-based (baris, kolom) pada grid peta secara immutable.
 *
 * @param row indeks baris (0 hingga rows - 1)
 * @param col indeks kolom (0 hingga cols - 1)
 */
public record Position(int row, int col) {

    /**
     * Menghasilkan posisi baru yang bergeser satu langkah sesuai arah yang diberikan.
     *
     * @param d arah pergerakan
     * @return posisi baru hasil pergeseran
     */
    public Position move(Direction d) {
        Objects.requireNonNull(d, "Direction tidak boleh null");
        return new Position(row + d.dRow(), col + d.dCol());
    }
}