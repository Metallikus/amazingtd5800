package com.amazingtd5800;

/**
 * A saved level: number (b), score (c), cleared without losses (e) and lives lost (d).
 * Original: bt.
 */
public final class LevelRecord {

    public final int number;
    public final int score;
    public final boolean cleared;
    public final int livesLost;

    LevelRecord(int number, int score, boolean cleared, int livesLost) {
        this.number = number;
        this.score = score;
        this.cleared = cleared;
        this.livesLost = livesLost;
    }
}
