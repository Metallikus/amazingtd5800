package com.amazingtd5800;

import java.util.List;

/**
 * A wave of enemies: the first leaves after delay ms, the rest every interval ms (spread over 50..100% of it),
 * count in total. total() is the denominator of the wave progress bar, pathIndexes() the waypoints whose arrows
 * blink while the wave waits its turn.
 * Original: bb (bb.a(), bb.b()).
 */
abstract class Wave {

    /** Enemies of the wave not spawned yet; some waves read it to change their mix. Original: bb.b. */
    protected int remaining;
    /** Interval between the wave's enemies; some waves change it on the fly. Original: bb.c. */
    protected int interval;
    private int[] pathIndexes = new int[0];
    /** Time until the wave's next enemy. Original: bb.a. */
    private int timer;

    Wave(int delay, int count, int interval) {
        this.timer = delay;
        this.remaining = count;
        this.interval = interval;
    }

    /** Spawn the next enemy once its wait is over; the interval is taken with a spread. Original: bb.a(long). */
    final boolean tick(long elapsed) {
        timer -= (int) elapsed;
        if (timer > 0) {
            return false;
        }
        if (--remaining > 0) {
            timer = (int) (interval * 0.5 + interval * 0.5 * Randoms.nextFloat());
        }
        return true;
    }

    /** How long the wave still runs, for the progress bar. Original: bb.a(). */
    final int total() {
        return remaining * interval;
    }

    /** Time until the next enemy, shown on the panel before the wave starts. Original: bb.a. */
    final int timer() {
        return timer;
    }

    /** "Send now" zeroes the wave timer. Original: ca.b(long). */
    final void setTimer(long timer) {
        this.timer = (int) timer;
    }

    /** This wave's waypoints; only their arrows blink. Original: bb.b(). */
    final int[] pathIndexes() {
        return pathIndexes;
    }

    /** The waypoints this wave walks. Original: bb.a(int[]). */
    protected final void pathIndexes(int... indexes) {
        this.pathIndexes = indexes;
    }

    /** Puts the wave's enemies onto their waypoints. Original: bb.a(dd[], Vector). */
    abstract void spawn(Path[] paths, List<Enemy> waiting);

    /** Wave enemy with the default stats of its type. Original: w. */
    protected static Enemy spawn(Path path, EnemyType type, boolean shielded) {
        return spawn(path, type, type.health, type.speed, type.reward, shielded);
    }

    /** Wave enemy whose health, speed and reward the wave overrode. Original: w. */
    protected static Enemy spawn(Path path, EnemyType type, int health, float speed, int reward,
                                 boolean shielded) {
        Enemy enemy = new Enemy(Assets.get().enemy(type.sheet), type.size, type.frames, new PathWalker(path),
                health, speed, reward, shielded, type.armor, type.hitRadius);
        if (type.switchSpeeds != null) {
            enemy.switches(type.switchSpeeds, type.switchTimers);
        }
        if (shielded) {
            enemy.shielded(type.shieldOn, type.shieldOff);
        }
        return enemy;
    }

    boolean isDone() {
        return remaining <= 0;
    }
}
