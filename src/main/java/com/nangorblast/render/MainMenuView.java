package com.nangorblast.render;

import java.util.Scanner;

/**
 * Menampilkan antarmuka menu utama CLI untuk Nangor Blast dan menyimpan opsi terpilih.
 */
public class MainMenuView {
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String BRIGHT_GREEN = "\u001B[92m";
    private static final String BRIGHT_CYAN = "\u001B[96m";
    private static final String BRIGHT_YELLOW = "\u001B[93m";

    private int selectedOption = 1;

    /**
     * Membuat MainMenuView dengan opsi default 1 (Start Game).
     */
    public MainMenuView() {
        this.selectedOption = 1;
    }

    /**
     * Menampilkan tampilan banner dan daftar pilihan menu utama ke CLI.
     */
    public void display() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        sb.append(BOLD).append(BRIGHT_GREEN)
                .append("===========================================\n")
                .append("      NANGOR BLAST - GREEN GARDEN CLI      \n")
                .append("===========================================\n")
                .append(RESET);
        sb.append(formatOption(1, "Start Game (Map Type A: Green Garden)"));
        sb.append(formatOption(2, "How to Play"));
        sb.append(formatOption(3, "Exit"));
        sb.append(BRIGHT_YELLOW).append("-------------------------------------------\n").append(RESET);
        sb.append("Pilih menu [1-3]: ");
        System.out.print(sb);
        System.out.flush();
    }

    /**
     * Membaca pilihan menu dari input pengguna secara aman dengan validasi rentang 1..3.
     *
     * @param scanner scanner input standar
     * @return nomor menu yang dipilih (1, 2, atau 3)
     */
    public int readSelection(Scanner scanner) {
        if (scanner != null && scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            try {
                int parsed = Integer.parseInt(line);
                if (parsed >= 1 && parsed <= 3) {
                    this.selectedOption = parsed;
                }
            } catch (NumberFormatException ignored) {
                // Pertahankan selectedOption sebelumnya bila input bukan angka
            }
        }
        return selectedOption;
    }

    /**
     * Mengatur pilihan menu secara programatik (misalnya dari navigasi panah JLine).
     *
     * @param option nomor pilihan (1..3)
     */
    public void setSelectedOption(int option) {
        if (option >= 1 && option <= 3) {
            this.selectedOption = option;
        }
    }

    /**
     * Mengembalikan nomor opsi menu yang sedang dipilih.
     *
     * @return nomor opsi (1 = Start, 2 = How to Play, 3 = Exit)
     */
    public int getSelectedOption() {
        return selectedOption;
    }

    private String formatOption(int number, String label) {
        if (selectedOption == number) {
            return BOLD + BRIGHT_CYAN + "  > [" + number + "] " + label + RESET + "\n";
        }
        return "    [" + number + "] " + label + "\n";
    }
}