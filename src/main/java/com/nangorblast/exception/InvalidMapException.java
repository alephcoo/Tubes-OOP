package com.nangorblast.exception;

/**
 * Exception yang dilempar ketika file peta tidak ditemukan, format tidak sesuai,
 * jumlah spawn tidak valid, atau jalur BFS dari Player ke Boss/Exit terputus.
 */
public class InvalidMapException extends Exception {

    /**
     * Membuat InvalidMapException dengan pesan kesalahan tertentu.
     *
     * @param message deskripsi kesalahan validasi peta
     */
    public InvalidMapException(String message) {
        super(message);
    }

    /**
     * Membuat InvalidMapException dengan pesan dan penyebab (cause) asli.
     *
     * @param message deskripsi kesalahan
     * @param cause   exception penyebab asli
     */
    public InvalidMapException(String message, Throwable cause) {
        super(message, cause);
    }
}