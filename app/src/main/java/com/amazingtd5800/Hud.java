package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * The game counters: lives, money, score, wave number and the next-wave timer. Money and score go through the
 * difficulty multiplier (amazingtd.j.a), so they are ints rather than floats.
 * Original: amazingtd.j, amazingtd.ce.
 */
final class Hud {

    private GameFont font = Assets.get().pixelFont();
    private String lives = "";
    private String moneyText = "";
    private String waveText = "";
    private String scoreText = "";
    private String nextWave = "";
    /** The level is cleared — the screen shows the victory panel. Original: amazingtd.j.d(). */
    private boolean cleared;
    private int livesCount;
    private int livesLost;
    private int money;
    private int kills;
    private int score;
    private int totalWaves;
    private long waveTimer;
    private long counted;
    /** A life lost: j.b(int) calls be.q(), and the vibration lives in be (Game). Original: amazingtd.j. */
    private final Game game;

    Hud(Game game) {
        this.game = game;
    }

    /** HUD needs the game screen only for be.q() — the vibration. Original: amazingtd.j. */
    Game game() {
        return game;
    }

    /** Level start: money with the difficulty multiplier, lives and score from zero. Original: amazingtd.j.a(int, int). */
    void start(int money, int waves) {
        this.totalWaves = waves;
        setWave(1);
        setScore(0);
        setLives(10);
        setMoney((int) (money * GameScreen.difficultyScale));
    }

    /** Wave number and the wave count. Original: amazingtd.j.a(int). */
    void setWave(int wave) {
        waveText = "Wave: " + wave + "/" + totalWaves;
    }

    /** Lives. Original: amazingtd.j.f(int). */
    void setLives(int lives) {
        this.livesCount = lives;
        this.lives = "Lives: " + lives;
    }

    /** Money. Original: amazingtd.j.g(int). */
    void setMoney(int money) {
        this.money = money;
        this.moneyText = "$: " + money;
    }

    /** Score. Original: amazingtd.j.e(int). */
    void setScore(int score) {
        this.score = score;
        this.scoreText = "Score: " + score;
    }

    /** Adds money. Original: amazingtd.j.c(int). */
    void earn(int amount) {
        setMoney(money + amount);
    }

    /** Spends money. Original: amazingtd.j.d(int). */
    void spend(int amount) {
        setMoney(money - amount);
    }

    /** Adds the score of a killed enemy. Original: amazingtd.j.e(int). */
    void addScore(int amount) {
        setScore(score + amount);
    }

    /** Enemy killed — its score and reward. Original: amazingtd.j.a(w). */
    void enemyKilled(Enemy enemy) {
        kills++;
        addScore(enemy.score());
        earn(enemy.money());
    }

    /** An enemy reached the end of the path: a life is lost and be.q() buzzes the phone briefly. Original: amazingtd.j.b(int). */
    void enemyReachedEnd() {
        setLives(livesCount - 1);
        livesLost++;
        game.vibrate();
    }

    int money() {
        return money;
    }

    int score() {
        return score;
    }

    int kills() {
        return kills;
    }

    int livesLost() {
        return livesLost;
    }

    int livesCount() {
        return livesCount;
    }

    /** Next-wave timer. Original: amazingtd.j.a(long). */
    void nextWaveIn(long millis) {
        this.waveTimer = millis;
        this.nextWave = "Next wave in: " + millis / 1000L;
    }

    /** Subtracts a second from the next-wave timer once a second. Original: amazingtd.j.b(long). */
    void tick(long elapsed) {
        counted += elapsed;
        if (counted >= 1000L) {
            waveTimer -= counted;
            nextWave = waveTimer < 0 ? "" : "Next wave in: " + (1 + waveTimer / 1000L);
            counted = 0;
        }
    }

    /** Lives and money on the left, score and wave on the right, "Game Paused!" centred. Original: amazingtd.j.a(Graphics). */
    void draw(Canvas canvas, int width, int height, boolean paused) {
        int second = font.height() + 2;
        font.drawText(canvas, lives, 2, 0, Anchor.TOP | Anchor.LEFT, Color.WHITE);
        font.drawText(canvas, moneyText, 2, second, Anchor.TOP | Anchor.LEFT, Color.WHITE);
        if (!nextWave.isEmpty()) {
            font.drawText(canvas, nextWave, 130, second, Anchor.TOP | Anchor.LEFT, Color.WHITE);
        }
        font.drawText(canvas, scoreText, width - 2, 0, Anchor.TOP | Anchor.RIGHT, Color.WHITE);
        font.drawText(canvas, waveText, width - 2, second, Anchor.TOP | Anchor.RIGHT, Color.WHITE);
        if (paused) {
            font.drawText(canvas, "Game Paused!", 180, 160, Anchor.TOP | Anchor.HCENTER, Color.WHITE);
        }
    }

    /** Flag g is up: the level is cleared and the screen shows the victory panel. Original: amazingtd.j.e(). */
    void clear() {
        this.cleared = true;
    }

    /** Whether flag g (level cleared) is up. Original: amazingtd.j.d(). */
    boolean cleared() {
        return cleared;
    }
}
