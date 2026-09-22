package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * A temporary effect on an enemy. While its timer runs the effect holds what it took from the enemy (speed,
 * shield, visibility, money) and gives it back when the timer expires.
 * Original: fa, ez.
 */
interface Effect {

    /** The effect attached to an enemy and took effect at once. Original: fa.a(w). */
    void apply(Enemy enemy);

    /** Ticks the timer and gives back what it took once it expires. Original: fa.b(long). */
    void tick(long elapsed);

    /** The timer has expired. Original: fa.d(). */
    boolean isDone();

    /** A copy of the effect for a projectile that hits several enemies. Original: fa.a(). */
    Effect copy();

    /** The effect label above the enemy's health bar. Original: fa.a(Graphics). */
    void draw(Canvas canvas);
}

/** The effect timer and its enemy reference. Original: ez. */
abstract class TimedEffect implements Effect {

    // Own paint: the effect label must not pick up the colour of whatever drew before it.
    protected final Paint paint = new Paint();

    long time;
    Enemy enemy;

    @Override
    public final void apply(Enemy enemy) {
        this.enemy = enemy;
        activate();
    }

    @Override
    public void tick(long elapsed) {
        time -= elapsed;
        if (time <= 0 && enemy != null) {
            expire();
            enemy = null;
        }
    }

    @Override
    public final boolean isDone() {
        return time <= 0;
    }

    /** The effect has started. Original: ez.c(). */
    protected abstract void activate();

    /** The timer expired; give the enemy back what was taken. Original: ez.b(). */
    protected abstract void expire();

    @Override
    public void draw(Canvas canvas) {
    }
}

/** Poison: 1 damage of type 1024 every 200 ms. Original: nw. */
final class Poison extends TimedEffect {

    private int tick = 200;

    Poison(long duration) {
        this.time = duration;
    }

    @Override
    protected void activate() {
        tick = 200;
    }

    @Override
    protected void expire() {
    }

    @Override
    public void tick(long elapsed) {
        tick -= (int) elapsed;
        if (tick <= 0 && enemy != null) {
            enemy.damageIfUnshielded(1, 1024);
            tick = 200;
        }
        super.tick(elapsed);
    }

    @Override
    public Effect copy() {
        return new Poison(time);
    }

    @Override
    public void draw(Canvas canvas) {
        if (enemy != null && !enemy.isDying()) {
            paint.setColor(Rgb.color(0xFF0000));
            canvas.drawRect(enemy.barLeft() + 4, enemy.barTop() - 4, enemy.barLeft() + 6, enemy.barTop() - 2,
                    paint);
        }
    }
}

/** Slow: takes part of the enemy's speed and gives it back when it expires. Original: ot. */
final class Slowing extends TimedEffect {

    private final float factor;
    private float stolen;

    Slowing(float factor, long duration) {
        this.factor = factor;
        this.time = duration;
    }

    @Override
    protected void activate() {
        stolen = enemy.speed() * factor;
        enemy.setSpeed(enemy.speed() - stolen);
    }

    @Override
    protected void expire() {
        enemy.setSpeed(enemy.speed() + stolen);
    }

    @Override
    public Effect copy() {
        return new Slowing(factor, time);
    }

    @Override
    public void draw(Canvas canvas) {
        if (enemy != null && !enemy.isDying()) {
            paint.setColor(Rgb.color(3725311));
            canvas.drawRect(enemy.barLeft(), enemy.barTop() - 4, enemy.barLeft() + 2, enemy.barTop() - 2, paint);
        }
    }
}

/** Money bonus: temporarily raises the enemy's reward and score. Original: aa. */
final class Money extends TimedEffect {

    private final int amount;
    private final boolean permanent;

    Money(int amount, long duration, boolean permanent) {
        this.amount = amount;
        this.permanent = permanent;
        this.time = duration;
    }

    @Override
    protected void activate() {
        int value = enemy.money() + amount;
        enemy.setMoney(value);
        enemy.setScore(value);
    }

    @Override
    protected void expire() {
        if (permanent) {
            return;
        }
        int value = enemy.money() - amount;
        enemy.setMoney(value);
        enemy.setScore(value);
    }

    @Override
    public Effect copy() {
        return new Money(amount, time, permanent);
    }

    @Override
    public void draw(Canvas canvas) {
        if (enemy != null && !enemy.isDying()) {
            paint.setColor(Rgb.color(0xFFFF00));
            canvas.drawRect(enemy.barLeft() + 8, enemy.barTop() - 4, enemy.barLeft() + 10, enemy.barTop() - 2,
                    paint);
        }
    }
}

/** Detector: makes an invisible enemy visible for a while. Original: st. */
final class Detect extends TimedEffect {

    private boolean wasVisible;

    Detect(long duration) {
        this.time = duration;
    }

    @Override
    protected void activate() {
        wasVisible = enemy.isVisible();
        if (wasVisible) {
            time = 0;
            return;
        }
        enemy.setVisible(true);
    }

    @Override
    protected void expire() {
        enemy.setVisible(wasVisible);
    }

    @Override
    public Effect copy() {
        return new Detect(time);
    }
}

/** Shield break: switches the enemy's shield off for a while. Original: sr. */
final class ShieldDown extends TimedEffect {

    private boolean wasShielded;

    ShieldDown(long duration) {
        this.time = duration;
    }

    @Override
    protected void activate() {
        wasShielded = enemy.isShielded();
        if (wasShielded) {
            enemy.setShieldEnabled(false);
            return;
        }
        time = 0;
    }

    @Override
    protected void expire() {
        enemy.setShieldEnabled(wasShielded);
    }

    @Override
    public Effect copy() {
        return new ShieldDown(time);
    }
}

/** Heal: -1 health of type 1 every 300 ms until the timer expires. Original: dh. */
final class Heal extends TimedEffect {

    /** Period, its timer and how much it heals. Original: dh.c, dh.d, dh.e. */
    private int tick = 300;

    Heal() {
        this(300000);
    }

    Heal(long duration) {
        this.time = duration;
    }

    @Override
    protected void activate() {
        tick = 300;
    }

    @Override
    protected void expire() {
    }

    @Override
    public void tick(long elapsed) {
        tick -= (int) elapsed;
        if (tick <= 0 && enemy != null) {
            enemy.damage(-1, 1);
            tick = 300;
        }
        super.tick(elapsed);
    }

    @Override
    public Effect copy() {
        return new Heal(time);
    }

    @Override
    public void draw(Canvas canvas) {
        if (enemy != null && !enemy.isDying() && tick < 100) {
            paint.setColor(Rgb.color(0x00FF00));
            canvas.drawRect(enemy.barLeft(), enemy.barTop(), enemy.barLeft() + enemy.size() - 1, enemy.barTop() + 2,
                    paint);
        }
    }
}

/** Stun: zeroes the enemy's speed for a while. Original: nr. */
final class Stun extends TimedEffect {

    private float stolen;

    Stun(long duration) {
        this.time = duration;
    }

    @Override
    protected void activate() {
        stolen = enemy.speed();
        enemy.setSpeed(0.0f);
    }

    @Override
    protected void expire() {
        enemy.setSpeed(enemy.speed() + stolen);
    }

    @Override
    public Effect copy() {
        return new Stun(time);
    }

    @Override
    public void draw(Canvas canvas) {
        if (enemy != null && !enemy.isDying()) {
            paint.setColor(Rgb.color(0xFF44F8));
            canvas.drawRect(enemy.barLeft() + 12, enemy.barTop() - 4, enemy.barLeft() + 14, enemy.barTop() - 2,
                    paint);
        }
    }
}
