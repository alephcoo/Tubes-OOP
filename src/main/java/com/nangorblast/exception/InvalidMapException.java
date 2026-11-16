package com.nangorblast.exception;

/**
 * Exception yang dilempar ketika file map tidak valid.
 * PIC: Fakhri Athallah Dwi Andeo (18225029)
 * Fitur: F00.3
 */
public class InvalidMapException extends Exception {
    public InvalidMapException(String message) {
        super(message);
    }
}
