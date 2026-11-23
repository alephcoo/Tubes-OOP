package com.nangorblast.render;

/**
 * Menampilkan layar panduan bermain (How to Play) meliputi kontrol keyboard,
 * jenis bomb, jenis blok Green Garden, Power-Up, dan mekanik Boss Draco.
 */
public class HowToPlayView {
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String BRIGHT_GREEN = "\u001B[92m";
    private static final String BRIGHT_YELLOW = "\u001B[93m";
    private static final String BRIGHT_CYAN = "\u001B[96m";
    private static final String BRIGHT_MAGENTA = "\u001B[95m";

    /**
     * Menampilkan informasi panduan permainan lengkap ke konsol.
     */
    public void display() {
        String guide = """
                
                %s%s==================== HOW TO PLAY: NANGOR BLAST ====================%s
                %s[KONTROL KEYBOARD]%s
                  W / A / S / D atau Arrow : Bergerak ke Atas / Kiri / Bawah / Kanan
                  SPACE                    : Pasang Bomb di posisi saat ini
                  1 / 2 / 3                : Pilih Normal / Pumped / Remote Bomb
                  E                        : Ledakkan Remote Bomb (setelah unlocked)
                  P                        : Pause / Resume permainan
                  Q                        : Kembali ke Menu Utama

                %s[JENIS BOMB]%s
                  1. Normal Bomb  : Fuse 3.0 detik, damage 1, menghancurkan blok D.
                  2. Pumped Bomb  : Fuse 4.5 detik, range +2 (maks 7), damage 2,
                                    SATU-SATUNYA bomb penghancur Reinforced Block (R).
                  3. Remote Bomb  : Meledak manual saat tombol E ditekan (butuh item 'r').

                %s[BLOK & LINGKUNGAN - GREEN GARDEN]%s
                  # (Indestructible) : Dinding batu permanen, tidak dapat dihancurkan.
                  D (Destructible)   : Penghalang taman, hancur oleh semua bomb (30%% drop).
                  R (Reinforced)     : Batu kuno khas Green Garden, hanya hancur oleh Pumped Bomb.
                  X (Exit Door)      : Pintu keluar, terbuka setelah seluruh musuh & Boss kalah.

                %s[POWER-UP]%s
                  + (Extra Bomb)       : +1 kapasitas bomb (maks 3)
                  f (Blast Range)      : +1 jangkauan ledakan (maks 5)
                  s (Speed Up)         : +50%% kecepatan gerak (maks +100%%)
                  r (Remote Detonator) : Membuka Remote Bomb (dijamin minimal 1 di peta)
                  h (Extra Life)       : +1 nyawa (maks 5)

                %s[BOSS DRACO]%s
                  Draco (B) menjaga area akhir Green Garden dengan 4 pola serangan:
                  - Fire Breath : Semburan api lurus terhalang tembok (perhatikan warning '!')
                  - Bite        : Serangan jarak dekat
                  - Swoop       : Menukik cepat menuju posisi pemain
                  - Wing Gust   : Hempasan angin pendorong
                  Serang Draco saat fase rentan (vulnerable), lalu masuki Exit (X) untuk MENANG!
                ===================================================================
                """.formatted(
                BOLD, BRIGHT_GREEN, RESET,
                BRIGHT_CYAN, RESET,
                BRIGHT_YELLOW, RESET,
                BRIGHT_GREEN, RESET,
                BRIGHT_CYAN, RESET,
                BRIGHT_MAGENTA, RESET
        );
        System.out.print(guide);
        System.out.flush();
    }
}