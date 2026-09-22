package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An enemy walking its path. The health bar under it changes colour with the health left, the shield is a
 * periodic timer (on for shieldOn ms, off for shieldOff ms), and armour takes 40% of the damage when the
 * damage type matches.
 * Original: w.
 */
final class Enemy {

    /** Health and speed multipliers coming from the difficulty. Original: w.a, w.b. */
    static float healthScale = 1.0f;
    static float speedScale = 1.0f;

    static final Random RANDOM = new Random();

    private final Sprite sprite;
    // Own paint: the health bar and the shield outline must not pick up the colour of whatever drew before.
    private final Paint paint = new Paint();
    private final int size;
    private final int half;
    private final PathWalker walker;
    private final List<Effect> effects = new ArrayList<>();
    private final Vec2 position;
    private final Vec2 velocity;
    private final int armor;
    private float speed;
    private int maxHealth;
    private int health;
    private int color = Rgb.color(56576);
    private int frameDelay;
    private int money;
    private int score;
    private int shield;
    private int shieldOn;
    private int shieldOff;
    /** The enemy has a shield at all; effect sr can switch it off. Original: w.x. */
    private boolean shieldEnabled;
    /** Hit radius; for some enemies it is below half of the frame. Original: w.c (w.b()). */
    private int hitRadius;
    private float travel;
    /** Speed-switch timers and the phase (true = slow phase). Original: gx.e, kd.e. */
    private float[] switchSpeeds;
    private int[] switchTimers;
    private int switchTimer;
    private boolean slowPhase;
    /** Killed by damage — towers stop aiming at it. Original: w.l. */
    private boolean dying;
    /** The burst finished playing, so the enemy comes off the layer. Original: w.m. */
    private boolean removed;
    private boolean visible = true;
    private DeathBurst burst;
    private EnemyListener listener;

    Enemy(android.graphics.Bitmap sheet, int size, int[] frames, PathWalker walker, int health, float speed,
          int reward, boolean hasShield, int armor, int hitRadius) {
        this.size = size;
        this.half = size >> 1;
        this.sprite = new Sprite(sheet, size, size);
        this.sprite.setFrameSequence(frames);
        this.walker = walker;
        this.position = walker.current().copy();
        this.velocity = new Vec2(0, 0);
        this.armor = armor;
        setHealth(health);
        this.speed = speed * speedScale;
        this.money = reward;
        this.score = reward;
        this.shieldEnabled = hasShield;
        this.hitRadius = hitRadius;
        sprite.setRefPixelPosition((int) position.x, (int) position.y);
        // The original calls k.a() (step to the waypoint the enemy walks to) first, and only then q().
        walker.advance();
        retarget();
    }

    /**
     * Health with the difficulty multiplier applied, plus the health-bar colour. The original returns itself so a
     * wave could tweak the enemy inline (waiting.add(spawn(...).setHealth(...))).
     * Original: w.a(int).
     */
    Enemy setHealth(int health) {
        this.maxHealth = (int) (health * healthScale);
        this.health = this.maxHealth;
        color();
        return this;
    }

    /** Bar colour from the health left; it is also the death-burst colour, hence Rgb.color. Original: w.p (w.d → nz). */
    private void color() {
        if (health < 50) {
            color = Rgb.color(56576);
        } else if (health < 100) {
            color = Rgb.color(12648227);
        } else if (health < 200) {
            color = Rgb.color(0xDDDD00);
        } else if (health < 500) {
            color = Rgb.color(0xFFAF3F);
        } else if (health < 1000) {
            color = Rgb.color(0xDD0000);
        } else if (health < 2000) {
            color = Rgb.color(16734633);
        } else {
            color = Rgb.color(13382399);
        }
    }

    /** Shield is on for shieldOn ms, then off for shieldOff ms. Original: w.a(int, int). */
    void shielded(int on, int off) {
        shieldOn = (int) (on * 0.6 + on * 0.4 * RANDOM.nextFloat());
        shieldOff = (int) (off * 0.5 + off * 0.5 * RANDOM.nextFloat());
        shield = on;
        shieldEnabled = true;
    }

    /**
     * Vector to the current waypoint and the time to reach it. The waypoint itself is advanced by the caller.
     * Original: w.q().
     */
    private void retarget() {
        Vec2 target = walker.current();
        velocity.x = target.x - position.x;
        velocity.y = target.y - position.y;
        velocity.normalize();
        velocity.scale(speed);
        travel = (int) (position.distance(target) / velocity.length());
    }

    void setListener(EnemyListener listener) {
        this.listener = listener;
    }

    /** Switches speed between slow and fast on the enemy's own timer. Original: gx.e, kd.e. */
    void switches(float[] speeds, int[] timers) {
        switchSpeeds = speeds;
        switchTimers = timers;
        switchTimer = Randoms.between(timers[0], timers[1]);
    }

    /** Moves along the path, runs the shield timer, the frame animation and the effects. Original: w.b(long). */
    void tick(long elapsed) {
        if (burst != null) {
            burst.tick(elapsed);
            if (burst.isDone()) {
                removed = true;
                burst = null;
            }
            return;
        }
        if (switchSpeeds != null) {
            switchTimer -= elapsed;
            if (switchTimer <= 0) {
                if (slowPhase) {
                    slowPhase = false;
                    setSpeed(switchSpeeds[0]);
                    switchTimer = Randoms.between(switchTimers[2], switchTimers[3]);
                } else {
                    slowPhase = true;
                    setSpeed(switchSpeeds[1]);
                    switchTimer = Randoms.between(switchTimers[4], switchTimers[5]);
                }
            }
        }
        if (shieldEnabled) {
            shield -= (int) elapsed;
            if (shield <= -shieldOff) {
                shield = shieldOn;
            }
        }
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            effect.tick(elapsed);
            if (effect.isDone()) {
                effects.remove(i);
            }
        }
        if (travel <= elapsed) {
            Vec2 target = walker.current();
            position.x = target.x;
            position.y = target.y;
            if (walker.finished()) {
                visible = false;
                if (listener != null) {
                    listener.reachedEnd(this);
                }
                burst = new DeathBurst(position, 40, color, RANDOM);
                return;
            }
            // The enemy reached the waypoint: only now k.a(), then q() towards the next one.
            walker.advance();
            retarget();
        } else {
            position.x += velocity.x * elapsed;
            position.y += velocity.y * elapsed;
            travel -= elapsed;
        }
        sprite.setRefPixelPosition((int) position.x, (int) position.y);
        // w.b(long): p = (int)(p + l2); if (p >= 50) { advance p/50 frames; p = 0; } — the remainder is dropped, not
        // kept, so an enemy that ticks 20 ms at a time advances a frame every full 50 ms and loses the rest.
        frameDelay += elapsed;
        if (frameDelay >= 50) {
            for (int i = 0; i < frameDelay / 50; i++) {
                sprite.nextFrame();
            }
            frameDelay = 0;
        }
    }

    /** Damage taken; armour absorbs 40% when the damage type matches the armour. Original: w.d(int, int). */
    void damage(int amount, int type) {
        if (armor != 0 && (type & armor) == armor) {
            amount = (int) (amount * 0.6f + 0.5f);
        }
        health -= amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
        color();
        if (health <= 0) {
            dying = true;
            visible = false;
            if (listener != null) {
                listener.killed(this);
            }
            burst = new DeathBurst(position, 26, color, RANDOM);
        }
    }

    /** A projectile hits only without a shield, or when it ignores the shield. Original: w.a(ga). */
    void hit(Projectile projectile) {
        if (!shieldActive() || projectile.ignoresShield()) {
            damage(projectile.damage(), projectile.type());
        }
    }

    /** Damage only while the shield is currently off. Original: w.c(int, int). */
    void damageIfUnshielded(int amount, int type) {
        if (!shieldActive()) {
            damage(amount, type);
        }
    }

    /** Whether the shield is active right now. Original: w.r(). */
    boolean shieldActive() {
        return shieldEnabled && shield > 0;
    }

    /** Whether the enemy has a shield, on or switched off by effect sr. Original: w.x. */
    boolean isShielded() {
        return shieldEnabled;
    }

    void setShieldEnabled(boolean enabled) {
        this.shieldEnabled = enabled;
    }

    void addEffect(Effect effect) {
        effect.apply(this);
        effects.add(effect);
    }

    /** The enemy reached the end of the path — start it again. Original: w.f(). */
    void restart() {
        walker.reset();
        position.x = walker.current().x;
        position.y = walker.current().y;
        visible = true;
        walker.advance();
        retarget();
    }

    /**
     * Health bar, shield and effects. The body carries the original's LayerManager, which does not draw an
     * invisible sprite, so an invisible enemy is only a health bar until a detector tower reveals it.
     * Original: w.a(Graphics).
     */
    void draw(Canvas canvas) {
        if (burst != null) {
            burst.draw(canvas);
            return;
        }
        if (removed) {
            return;
        }
        // The original's LayerManager skips an invisible sprite, so an invisible enemy is only the health bar.
        if (visible) {
            sprite.paint(canvas);
        }
        if (shieldActive() && visible) {
            // J2ME drawArc has no useCenter and its box ends at x + w - 1, y + h - 1, so the ring of e + 4 pixels
            // ends at x - half + size + 1. It is always an outline, never a fill.
            paint.setColor(Rgb.color(0xFFFF00));
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(position.x - half - 2, position.y - half - 2, position.x - half + size + 1,
                    position.y - half + size + 1, 0, 360, false, paint);
            paint.setStyle(Paint.Style.FILL);
        }
        // drawRect(e, 3) is the box (the two fills below cover its whole interior), fillRect(x+1+filled, y+1,
        // e-1-filled, 2) the health that is gone and fillRect(x+1, y+1, filled, 2) what is left. J2ME
        // drawRect/fillRect cover x..x+w-1 and y..y+h-1 and draw nothing at all when w is 0 (w.a(Graphics)).
        int left = barLeft();
        int top = barTop();
        int filled = Math.max(0, (int) ((size - 1) * ((float) health / maxHealth) + 0.5f));
        fill(canvas, Rgb.color(0x666666), left, top, size, 3);
        fill(canvas, Rgb.color(0), left + 1 + filled, top + 1, size - 1 - filled, 2);
        fill(canvas, color, left + 1, top + 1, filled, 2);
        for (Effect effect : effects) {
            effect.draw(canvas);
        }
    }

    /** A J2ME rect: it covers x..x+w-1 and y..y+h-1, and a width of 0 draws nothing at all. */
    private void fill(Canvas canvas, int color, float left, float top, int width, int height) {
        if (width <= 0) {
            return;
        }
        paint.setColor(color);
        canvas.drawRect(left, top, left + width - 1, top + height - 1, paint);
    }

    /** Where the enemy is — towers aim here. Original: w.a(). */
    Vec2 position() {
        return position;
    }
    /** Hit radius of the enemy. Original: w.b(). */
    int hitRadius() {
        return hitRadius;
    }

    /** Enemy frame size. Original: w.m(). */
    int size() {
        return size;
    }

    /** Velocity vector — towers read the lead from it. Original: w.g(). */
    Vec2 velocity() {
        return velocity;
    }

    /** The enemy stays invisible until a detector tower finds it. Original: w.isVisible(), w.setVisible(boolean). */
    boolean isVisible() {
        return visible;
    }

    void setVisible(boolean visible) {
        this.visible = visible;
    }

    /** Enemy speed; slow and stun effects change it. Original: w.a(float). */
    Enemy setSpeed(float speed) {
        this.speed = speed == 0.0f ? 1.0E-6f : speed;
        retarget();
        return this;
    }

    /**
     * Reward and score for the enemy. Like w.a(int) these return the enemy itself, so a wave could tweak it
     * inline (waiting.add(spawn(...).setMoney(...))).
     * Original: w.b(int), w.c(int).
     */
    Enemy setMoney(int money) {
        this.money = money;
        return this;
    }

    Enemy setScore(int score) {
        this.score = score;
        return this;
    }

    /** Left edge of the health bar. Original: w.e(). */
    int barLeft() {
        return (int) position.x - half;
    }

    /** Top edge of the health bar. Original: w.d(). */
    int barTop() {
        return (int) position.y - half - 5;
    }

    /** Killed by damage. Original: w.j(). */
    boolean isDying() {
        return dying;
    }

    /** The burst finished playing, the enemy is removed. Original: w.m(). */
    boolean isRemoved() {
        return removed;
    }

    /** Score the enemy awards. Original: w.k(). */
    int score() {
        return score;
    }

    /** Money the enemy awards. Original: w.l(). */
    int money() {
        return money;
    }

    /** Enemy speed. Original: w.n(). */
    float speed() {
        return speed;
    }

    /** Reward for the enemy. Original: w.b(int). */
    void setReward(int reward) {
        this.money = reward;
        this.score = reward;
    }

    /** Listener for death and end of path. Original: o. */
    interface EnemyListener {
        void killed(Enemy enemy);

        void reachedEnd(Enemy enemy);
    }
}
