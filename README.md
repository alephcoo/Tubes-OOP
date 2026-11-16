# Nangor Blast

Permainan berbasis CLI bertema Bomberman, dikembangkan menggunakan Java sebagai Tugas Besar IF2010 Pemrograman Berorientasi Objek 2026.

## Kelompok

| NIM       | Nama                      | Domain                        |
|-----------|---------------------------|-------------------------------|
| 18224105  | Kyan Saktya Diraya        | Game Core, Input, Build       |
| 18225029  | Fakhri Athallah Dwi Andeo | Map, Block, Power-Up, Renderer|
| 18225039  | Obert Chandra             | Player, Bomb, Explosion       |
| 18225113  | Naufal Rasya              | Enemy, Boss Draco             |

## Map

Kelompok ini mengerjakan **Map Type A: Green Garden**.

## Prerequisite

- Java 17 atau lebih tinggi (cek dengan `java -version`)
- Maven 3.8+

## Cara Kompilasi dan Menjalankan

### Menggunakan Make

```bash
# Kompilasi
make build

# Jalankan game
make run

# Bersihkan hasil build
make clean
```

### Menggunakan Maven langsung

```bash
# Kompilasi
mvn compile

# Jalankan
mvn exec:java -Dexec.mainClass="com.nangorblast.Main"

# Build JAR
mvn package
```

## Struktur Proyek

```
Tubes-OOP/
├── src/main/java/com/nangorblast/
│   ├── core/        # GameEngine, GameState, GameLoop
│   ├── input/       # InputHandler, Command
│   ├── score/       # ScoreManager
│   ├── world/
│   │   ├── grid/    # Grid<T>, Position, Direction
│   │   ├── block/   # Block hierarchy
│   │   ├── powerup/ # PowerUp, PowerUpType
│   │   └── map/     # MapLoader, MapValidator
│   ├── renderer/    # CLIRenderer, HUD
│   ├── entity/
│   │   ├── player/  # Player
│   │   ├── bomb/    # Bomb, NormalBomb, PumpedBomb, RemoteBomb
│   │   ├── explosion/ # Explosion
│   │   └── enemy/   # Enemy, PatrolEnemy, ChaseEnemy, BossEnemy, Draco
│   └── exception/   # InvalidMapException, dll.
├── config/          # File layout map
├── doc/             # Dokumen (class diagram, pembagian tugas)
├── Makefile
├── pom.xml
└── README.md
```

## Kontrol

| Tombol        | Aksi                      |
|---------------|---------------------------|
| `W` / `↑`     | Gerak ke atas             |
| `A` / `←`     | Gerak ke kiri             |
| `S` / `↓`     | Gerak ke bawah            |
| `D` / `→`     | Gerak ke kanan            |
| `Space`       | Pasang bom                |
| `1`           | Pilih Normal Bomb         |
| `2`           | Pilih Pumped Bomb         |
| `3`           | Pilih Remote Bomb         |
| `F`           | Detonate Remote Bomb      |
| `P`           | Pause / Resume            |
| `R`           | Restart                   |
| `Esc`         | Kembali ke menu           |
