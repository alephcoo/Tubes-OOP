package com.nangorblast.world;

/**
 * Struktur data matriks dua dimensi generik untuk menyimpan objek pada peta permainan.
 *
 * @param <T> tipe elemen yang disimpan pada setiap sel grid
 */
public class Grid<T> {
    private int rows = 10;
    private int cols = 14;
    private T[][] cells;

    /**
     * Membuat grid generik dengan ukuran yang ditentukan.
     *
     * @param rows jumlah baris (harus > 0)
     * @param cols jumlah kolom (harus > 0)
     * @throws IllegalArgumentException jika rows atau cols tidak positif
     */
    @SuppressWarnings("unchecked")
    public Grid(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Ukuran grid harus positif: " + rows + "x" + cols);
        }
        this.rows = rows;
        this.cols = cols;
        this.cells = (T[][]) new Object[rows][cols];
    }

    /**
     * Mengambil elemen yang tersimpan pada posisi tertentu.
     *
     * @param pos posisi target pada grid
     * @return elemen pada posisi tersebut, atau {@code null} jika sel kosong
     * @throws IndexOutOfBoundsException jika posisi di luar batas grid
     */
    public T get(Position pos) {
        if (!isValidPosition(pos)) {
            throw new IndexOutOfBoundsException("Posisi di luar batas grid: " + pos);
        }
        return cells[pos.row()][pos.col()];
    }

    /**
     * Menyimpan elemen pada posisi tertentu di dalam grid.
     *
     * @param pos  posisi target pada grid
     * @param item elemen yang akan disimpan (boleh {@code null})
     * @throws IndexOutOfBoundsException jika posisi di luar batas grid
     */
    public void set(Position pos, T item) {
        if (!isValidPosition(pos)) {
            throw new IndexOutOfBoundsException("Posisi di luar batas grid: " + pos);
        }
        cells[pos.row()][pos.col()] = item;
    }

    /**
     * Memeriksa apakah suatu posisi berada dalam rentang indeks grid yang valid.
     *
     * @param pos posisi yang akan diperiksa
     * @return {@code true} jika posisi tidak null dan berada di dalam batas grid
     */
    public boolean isValidPosition(Position pos) {
        return pos != null
                && pos.row() >= 0 && pos.row() < rows
                && pos.col() >= 0 && pos.col() < cols;
    }

    /**
     * Mengembalikan jumlah baris grid.
     *
     * @return jumlah baris
     */
    public int getRows() {
        return rows;
    }

    /**
     * Mengembalikan jumlah kolom grid.
     *
     * @return jumlah kolom
     */
    public int getCols() {
        return cols;
    }
}