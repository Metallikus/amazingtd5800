package com.amazingtd5800;

/**
 * Enemy types: their frame sheet and frames, health, speed, reward, shield and armour. Armour takes 40% of the
 * damage when the damage type matches the enemy's armour mask (w.d(n2, n3) with (n3 & mask) == mask). Enemies
 * with switchSpeeds switch speed on their own timer (g* x.b(long)); the others are given their speed once.
 * Original: the enemy classes of w.
 */
enum EnemyType {

    /** Original: tj. */
    SHREDDER("EnemyShredder.png", new int[]{0, 1, 2, 3}, 16, 45, 0.03f, 2, 600, 1000, 5, 8),

    /** Original: sa. */
    SHREDDER_TWO("EnemyShredder.png", new int[]{4, 5, 6, 7}, 16, 80, 0.03f, 2, 500, 1200, 5, 8),

    /** Original: fq. */
    SHREDDER_THREE("EnemyShredder.png", new int[]{8, 9, 10, 11}, 16, 200, 0.03f, 2, 400, 1100, 5, 8),

    /** Original: mx. */
    GHOST("EnemyGhost.png", new int[]{0, 0, 1, 2, 3, 2, 1}, 16, 60, 0.018f, 1, 1400, 4000, 0, 8),

    /** Original: kc. */
    GHOST_TWO("EnemyGhost.png", new int[]{4, 4, 5, 6, 7, 6, 5}, 16, 140, 0.018f, 1, 2000, 3000, 0, 8),

    /** Original: rj. */
    GHOST_THREE("EnemyGhost.png", new int[]{8, 8, 9, 10, 11, 10, 9}, 16, 220, 0.018f, 2, 3000, 2000, 0, 8),

    /** Original: io. */
    BLOB("EnemyBlob.png", new int[]{0, 0, 1, 1, 2, 2, 1, 1, 0, 1, 2, 1}, 16, 40, 0.034f, 2, 800, 1000, 5, 8),

    /** Original: tk. */
    BALL("EnemyBall.png", new int[]{0, 1, 2, 3, 4, 5, 4, 3, 2, 1}, 16, 45, 0.03f, 3, 1600, 3000, 0, 6),

    /** Original: ls. */
    BALL_THREE("EnemyBall.png", new int[]{6, 7, 8, 9, 10, 11, 10, 9, 8, 7}, 16, 30, 0.034f, 2, 600, 800, 0, 6),

    /** Original: ob. */
    BALL_TWO("EnemyBall.png", new int[]{12, 13, 14, 15, 16, 17, 16, 15, 14, 13}, 16, 160, 0.014f, 4, 2000, 2000, 0, 6),

    /** Original: mu. */
    LINE("EnemyLine.png", new int[]{0, 1, 2, 3, 4}, 16, 50, 0.024f, 2, 600, 1800, 4, 8),

    /** Original: gn. */
    LINE_TWO("EnemyLine.png", new int[]{5, 6, 7, 8, 9}, 16, 90, 0.024f, 2, 800, 800, 4, 8),

    /** Original: oj. */
    LINE_THREE("EnemyLine.png", new int[]{10, 11, 12, 13, 14}, 16, 190, 0.03f, 2, 500, 500, 4, 8),

    /** Original: fg. */
    SUN("EnemySun.png", new int[]{0, 1, 2, 3, 4, 5, 6, 7}, 16, 45, 0.034f, 1, 300, 700, 1, 8),

    /** Original: ff. */
    SUN_THREE("EnemySun.png", new int[]{8, 9, 10, 11, 12, 13, 14, 15}, 16, 80, 0.024f, 2, 1000, 600, 1, 8),

    /** Original: lp. */
    SUN_FOUR("EnemySun.png", new int[]{16, 17, 18, 19, 20, 21, 22, 23}, 16, 130, 0.024f, 2, 1000, 400, 1, 8),

    /** Original: th. */
    SUN_FIVE("EnemySun.png", new int[]{24, 25, 26, 27, 28, 29, 31, 31}, 16, 200, 0.03f, 2, 1000, 200, 1, 8),

    /** Original: ql. */
    CLOCK("EnemyClock.png", new int[]{0, 1, 2, 3, 2, 1}, 16, 400, 0.018f, 5, 3000, 2000, 2, 8),

    /** Original: cd. */
    CLOCK_TWO("EnemyClock.png", new int[]{4, 5, 6, 7, 6, 5}, 16, 700, 0.018f, 5, 3000, 1500, 2, 8),

    /** Original: sb. */
    CLOCK_THREE("EnemyClock.png", new int[]{8, 9, 10, 11, 10, 9}, 16, 1000, 0.018f, 5, 3000, 1000, 2, 8),

    /** Original: cp. */
    ELLIPSE("EnemyEllipse.png", new int[]{0, 1, 2, 3, 2, 1}, 10, 30, 0.04f, 2, 600, 1000, 0, 5),

    /** Original: qm. */
    ELLIPSE_TWO("EnemyEllipse.png", new int[]{5, 6, 7, 8, 7, 6}, 10, 90, 0.03f, 3, 200, 600, 0, 5),

    /** Original: gu. */
    ELLIPSE_THREE("EnemyEllipse.png", new int[]{10, 11, 12, 13, 12, 11}, 10, 60, 0.04f, 2, 600, 1000, 0, 5),

    /** Original: hl. */
    TRIANGLE("EnemyTriangle.png", new int[]{0, 1, 2, 3, 4, 5}, 16, 80, 0.024f, 3, 2000, 1400, 3, 5),

    /** Original: jj. */
    TRIANGLE_TWO("EnemyTriangle.png", new int[]{6, 7, 8, 9, 10, 11}, 16, 180, 0.024f, 2, 2000, 1200, 3, 5),

    /** Original: dv. */
    TRIANGLE_THREE("EnemyTriangle.png", new int[]{12, 13, 14, 15, 16, 17}, 16, 280, 0.024f, 2, 2000, 1000, 3, 5),

    /** Original: lb. */
    WORM("EnemyWorm.png", new int[]{0, 1, 2, 3, 4, 3, 2, 1, 0, 5, 6, 7, 8, 7, 6, 5}, 16, 50, 0.03f, 2, 2200, 1200, 0, 6),

    /** Original: ni. */
    WORM_TWO("EnemyWorm.png", new int[]{9, 10, 11, 12, 13, 12, 11, 10, 9, 14, 15, 16, 17, 16, 15, 14}, 16, 200, 0.03f, 2, 2200, 1200, 0, 6),

    /** Original: q. */
    CIRCLE("EnemyCircle.png", new int[]{0, 1, 2, 3, 4, 5, 6}, 16, 60, 0.03f, 3, 1200, 700, 0, 7),

    /** Original: nf. */
    CIRCLE_TWO("EnemyCircle.png", new int[]{7, 8, 9, 10, 11, 12, 13}, 16, 120, 0.03f, 3, 1200, 700, 0, 7),

    /** Original: if. */
    CIRCLE_THREE("EnemyCircle.png", new int[]{14, 15, 16, 17, 18, 19, 20}, 16, 180, 0.03f, 3, 1200, 700, 0, 7),

    /** Original: gx. */
    SPARK("EnemySpark.png", new int[]{0, 1, 2, 3, 4, 5, 2, 3, 1}, 10, 150, 0.03f, 3, 10000, 0, 0, 4,
            new float[]{0.024f, 0.04f}, new int[]{1000, 8000, 5000, 8000, 1000, 2000}),

    /** Original: kd. */
    SPARK_TWO("EnemySpark.png", new int[]{6, 7, 8, 9, 10, 11, 8, 9, 7}, 10, 250, 0.03f, 2, 10000, 0, 0, 4,
            new float[]{0.024f, 0.04f}, new int[]{1000, 6000, 4000, 6000, 1200, 2000}),

    /** Original: oa. */
    SPARK_THREE("EnemySpark.png", new int[]{12, 13, 14, 15, 16, 17, 14, 15, 13}, 10, 350, 0.03f, 5, 10000, 0, 0, 4,
            new float[]{0.024f, 0.04f}, new int[]{1000, 6000, 4000, 6000, 1200, 2000}),

    /** Original: nt. */
    VIRUS("Virus.png", new int[]{0, 1, 2, 1, 3, 1}, 8, 20, 0.04f, 1, 800, 600, 0, 4),

    /** Original: mw. */
    VIRUS_TWO("Virus.png", new int[]{8, 9, 10, 9, 11, 9}, 8, 120, 0.04f, 1, 800, 400, 0, 4),

    /** Original: eo. */
    VIRUS_THREE("Virus.png", new int[]{12, 13, 14, 13, 15, 13}, 8, 210, 0.04f, 1, 800, 300, 0, 4),

    /** Original: pc. */
    VIRUS2("Virus2.png", new int[]{0, 1, 2, 3, 1, 0, 3, 2, 2}, 10, 150, 0.024f, 2, 600, 500, 0, 6),

    /** Original: kg. */
    VIRUS_BIG("VirusBig.png", new int[]{0, 1, 2, 3}, 18, 1500, 0.014f, 50, 3000, 1000, 0, 9);

    final String sheet;
    /** Enemy frame size on the sheet; for some enemies it is below 16. Original: w(e), Sprite(image, n2, n2). */
    final int size;
    final int[] frames;
    final int health;
    final float speed;
    final int reward;
    final int shieldOn;
    final int shieldOff;
    final int armor;
    /** Hit radius: half of the frame, less for some enemies. Original: w.c. */
    final int hitRadius;
    /** Toggle speeds, [slow, fast]. Original: gx.e, kd.e. */
    final float[] switchSpeeds;
    /** Toggle timers, [start, start, slow, slow, fast, fast]. Original: gx.e, kd.e. */
    final int[] switchTimers;

    EnemyType(String sheet, int[] frames, int size, int health, float speed, int reward, int shieldOn,
              int shieldOff, int armor, int hitRadius) {
        this(sheet, frames, size, health, speed, reward, shieldOn, shieldOff, armor, hitRadius, null, null);
    }

    EnemyType(String sheet, int[] frames, int size, int health, float speed, int reward, int shieldOn,
              int shieldOff, int armor, int hitRadius, float[] switchSpeeds, int[] switchTimers) {
        this.sheet = sheet;
        this.frames = frames;
        this.size = size;
        this.health = health;
        this.speed = speed;
        this.reward = reward;
        this.shieldOn = shieldOn;
        this.shieldOff = shieldOff;
        this.armor = armor;
        this.hitRadius = hitRadius;
        this.switchSpeeds = switchSpeeds;
        this.switchTimers = switchTimers;
    }
}
