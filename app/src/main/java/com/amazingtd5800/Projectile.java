package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * A projectile flies from the tower's point to the target's lead point, flies for its whole lifetime and hits
 * everything it touches. type is the enemy's armour mask (w.d), ignoresShield means the shot breaks shields,
 * pierces means it survives a hit and hits an area (mortar and rocket shrapnel).
 * Original: ga, lz.
 */
abstract class Projectile {

    // This projectile's own paint: its stroke must not pick up the colour of whatever drew before it.
    protected final Paint paint = new Paint();

    /** Velocity vector; the position is reduced by it every tick. Original: ga.f. */
    final Vec2 position;
    Vec2 velocity;
    /** Damage. Original: ga.g. */
    private final int damage;
    /** Damage type (the enemy's armour mask). Original: ga.a, ga.f(). */
    private final int type;
    /** Flight time; for shrapnel and rockets, the explosion time after arrival. Original: ga.c. */
    int timeLeft;
    /** The effect the shot puts on the enemy. Original: ga.d. */
    Effect effect;
    /** Hit radius. Original: ga.b. */
    int radius = 1;
    /** The shot ignores the enemy's shield. Original: ga.h, ga.b(boolean). */
    private boolean ignoresShield;
    /** The shot survives a hit. Original: ga.i, ga.d(). */
    private boolean pierces;
    /** The projectile arrived and is removed. Original: lz.a. */
    private boolean done;

    Projectile(Vec2 position, Vec2 velocity, int damage, int type, int timeLeft) {
        this.position = position;
        this.velocity = velocity;
        this.damage = damage;
        this.type = type;
        this.timeLeft = timeLeft;
    }

    /** Flies toward the target until its time is up. Original: ga.b(long). */
    void tick(long elapsed) {
        if (timeLeft <= 0) {
            done = true;
            return;
        }
        timeLeft -= (int) elapsed;
        position.x -= velocity.x * elapsed;
        position.y -= velocity.y * elapsed;
    }

    /** Its flight time is up. Original: lz.g(). */
    boolean isDone() {
        return done;
    }

    /** The projectile arrived and is removed. Original: lz.h(). */
    void markDone() {
        this.done = true;
    }

    /** The shot grazed the enemy when it hit the enemy's radius together with its own. Original: ga.a(o). */
    boolean hits(Enemy enemy) {
        return position.within(enemy.position(), radius + enemy.hitRadius());
    }

    /** Damage to the enemy plus the shot's effect. Original: ga.a(w). */
    void hit(Enemy enemy) {
        enemy.hit(this);
        if (effect != null) {
            enemy.addEffect(effect);
        }
    }

    int damage() {
        return damage;
    }

    int type() {
        return type;
    }

    /** Whether this shot ignores shields. Original: ga.e(). */
    boolean ignoresShield() {
        return ignoresShield;
    }

    /** Sets whether the shot ignores shields. Original: ga.b(boolean). */
    void setIgnoresShield(boolean ignoresShield) {
        this.ignoresShield = ignoresShield;
    }

    /** The shot does not vanish on a hit. Original: ga.a(boolean). */
    void markPiercing() {
        this.pierces = true;
    }

    boolean pierces() {
        return pierces;
    }

    void draw(Canvas canvas) {
    }

    /** Autobow and money tower arrow: a stroke 4 long along the velocity, damage type 1. Original: j. */
    static final class Arrow extends Projectile {

        private final Vec2 trail;
        private final int color;

        Arrow(Vec2 position, Vec2 velocity, int damage, int timeLeft) {
            this(position, velocity, damage, 1, timeLeft, 4, Rgb.color(0xFFFF00));
        }

        Arrow(Vec2 position, Vec2 velocity, int damage, int type, int timeLeft, int trailLength, int color) {
            super(position, velocity, damage, type, timeLeft);
            this.trail = velocity.copy();
            this.trail.normalize();
            this.trail.scale(trailLength);
            this.color = color;
        }

        @Override
        void draw(Canvas canvas) {
            if (timeLeft <= 0) {
                return;
            }
            paint.setColor(color);
            canvas.drawLine(position.x - trail.x, position.y - trail.y, position.x, position.y, paint);
        }
    }

    /** Chain gun bullet: the same stroke, shorter and another colour, damage type 5. Original: cc. */
    static final class Bullet extends Projectile {

        private final Vec2 trail;

        Bullet(Vec2 position, Vec2 velocity, int damage, int timeLeft) {
            super(position, velocity, damage, 5, timeLeft);
            this.trail = velocity.copy();
            this.trail.normalize();
            this.trail.scale(2);
        }

        @Override
        void draw(Canvas canvas) {
            if (timeLeft <= 0) {
                return;
            }
            paint.setColor(Rgb.color(3725311));
            canvas.drawLine(position.x - trail.x, position.y - trail.y, position.x, position.y, paint);
        }
    }

    /** Slow tower's arrow: a Flares2 sprite projectile, damage type 0 (does not bypass armour). Original: fd. */
    static final class SlowArrow extends Projectile {

        private final Sprite sprite = new Sprite(Assets.get().flare(), 6, 6);

        SlowArrow(Vec2 position, Vec2 velocity, int damage, int timeLeft) {
            super(position, velocity, damage, 0, timeLeft);
        }

        @Override
        void draw(Canvas canvas) {
            if (timeLeft <= 0) {
                return;
            }
            sprite.setRefPixelPosition((int) (position.x + 0.5f), (int) (position.y + 0.5f));
            sprite.paint(canvas);
        }
    }

    /** Mortar mine: flies to the target, then plays an explosion for 318 ms and hits an area. Original: sf. */
    static final class Shell extends Projectile {

        private final Sprite explosion = new Sprite(Assets.get().explosion(), 64, 64);
        private final int splash;
        private boolean exploded;
        private boolean hit;

        Shell(Vec2 position, Vec2 velocity, int timeLeft, int damage, int splash) {
            super(position, velocity, damage, 2, timeLeft);
            this.splash = splash;
            this.radius = 0;
            markPiercing();
        }

        @Override
        void tick(long elapsed) {
            if (exploded) {
                if (timeLeft <= 0) {
                    markDone();
                    return;
                }
                explosion.setRefPixelPosition((int) (position.x + 0.5f), (int) (position.y + 0.5f));
                explosion.setFrame(6 - timeLeft / 53);
                if (hit) {
                    effect = null;
                }
            } else if (timeLeft <= 0) {
                exploded = true;
                timeLeft = 318;
                radius = splash;
                Sounds.get().bombExplosion();
                explosion.setFrame(0);
                explosion.setRefPixelPosition((int) (position.x + 0.5f), (int) (position.y + 0.5f));
            } else {
                position.x -= velocity.x * elapsed;
                position.y -= velocity.y * elapsed;
            }
            timeLeft -= (int) elapsed;
        }

        @Override
        boolean hits(Enemy enemy) {
            return exploded && super.hits(enemy);
        }

        @Override
        void hit(Enemy enemy) {
            enemy.hit(this);
            if (effect != null) {
                enemy.addEffect(effect.copy());
                hit = true;
            }
        }

        @Override
        void draw(Canvas canvas) {
            if (timeLeft <= 0) {
                return;
            }
            if (exploded) {
                explosion.paint(canvas);
                return;
            }
            paint.setColor(Rgb.color(0xFFFF00));
            canvas.drawCircle(position.x, position.y, 3, paint);
        }
    }

    /** Sniper bullet (type 7): homes on its target and drags a 4-long stroke behind it. Original: rm. */
    static final class SniperShot extends Projectile {

        private final Vec2 target;
        private final float speed;
        private Vec2 trail;

        SniperShot(Vec2 position, Vec2 velocity, Vec2 target, int damage, int timeLeft) {
            super(position, velocity, damage, 7, timeLeft);
            this.target = target;
            this.speed = velocity.length();
            this.trail = velocity.copy();
            this.trail.normalize();
            this.trail.scale(4);
        }

        @Override
        void tick(long elapsed) {
            if (timeLeft <= 0) {
                markDone();
                return;
            }
            Vec2 steer = target.subtract(position);
            steer.normalize();
            steer.scale(speed * 0.1f);
            velocity = velocity.subtract(steer);
            velocity.normalize();
            velocity.scale(speed);
            timeLeft -= (int) elapsed;
            position.x -= velocity.x * elapsed;
            position.y -= velocity.y * elapsed;
            trail = velocity.copy();
            trail.normalize();
            trail.scale(4);
        }

        @Override
        void draw(Canvas canvas) {
            paint.setColor(Rgb.color(0xFFFF00));
            canvas.drawLine(position.x - trail.x, position.y - trail.y, position.x, position.y, paint);
        }
    }

    /**
     * Rocket: homes in with a spread and explodes over an area of 40, damage type 6. It does not aim at the
     * firing point — target is the enemy itself (w.a() = w.i), a live vector the enemy moves every tick, so the
     * rocket turns its course onto the target's current position each tick (ln.b: ri2 = this.i.c(this.e)).
     * Original: ln.
     */
    static final class Missile extends Projectile {

        private final Sprite explosion = new Sprite(Assets.get().explosion(), 64, 64);
        /** The rocket's target is the enemy itself, not where it was when the shot was fired. Original: ln.i. */
        private final Vec2 target;
        private final float speed;
        private Vec2 trail;
        private boolean exploded;

        Missile(Vec2 position, Vec2 velocity, Vec2 target, int damage, int timeLeft) {
            super(position, velocity, damage, 6, timeLeft);
            this.target = target;
            this.speed = velocity.length();
            this.trail = velocity.copy();
            this.trail.normalize();
            this.trail.scale(6);
            this.radius = 0;
            markPiercing();
        }

        @Override
        void tick(long elapsed) {
            if (exploded) {
                if (timeLeft <= 0) {
                    markDone();
                    return;
                }
                explosion.setFrame(6 - timeLeft / 53);
            } else if (timeLeft <= 0 || position.within(target, 12.0f)) {
                exploded = true;
                timeLeft = 318;
                radius = 40;
                Sounds.get().bombExplosion();
                explosion.setFrame(0);
                explosion.setRefPixelPosition((int) (position.x + 0.5f), (int) (position.y + 0.5f));
            } else {
                Vec2 steer = target.subtract(position);
                steer.normalize();
                steer.scale(speed * Randoms.between(0.1f, 0.3f));
                velocity = velocity.subtract(steer);
                velocity.normalize();
                velocity.scale(speed);
                position.x -= velocity.x * elapsed;
                position.y -= velocity.y * elapsed;
                trail = velocity.copy();
                trail.normalize();
                trail.scale(6);
            }
            timeLeft -= (int) elapsed;
        }

        @Override
        boolean hits(Enemy enemy) {
            return exploded && super.hits(enemy);
        }

        @Override
        void draw(Canvas canvas) {
            if (timeLeft <= 0) {
                return;
            }
            if (exploded) {
                explosion.paint(canvas);
                return;
            }
            paint.setColor(Rgb.color(0xFFFF00));
            canvas.drawLine(position.x - trail.x, position.y - trail.y, position.x, position.y, paint);
        }
    }
}
