package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Burst of short strokes at the point where an enemy dies or spawns. Each stroke starts 1 pixel from that
 * point, flies away with acceleration (its speed grows by tr.a) and fades out after 400-800 ms.
 * Original: nz, tr.
 */
final class DeathBurst {

    // Own paint: the burst strokes must not pick up the colour of whatever drew before them.
    private final Paint paint = new Paint();

    /**
     * One particle: a stroke from (position - trail) to position. The trail is fixed at 3 pixels, it is the
     * position that flies.
     * Original: tr.
     */
    private static final class Particle {
        /** Position is the integral of the velocity, which is why particles spread out with acceleration. Original: tr.e, lz.e. */
        private final Vec2 position;
        /** Velocity: in nz a unit vector times 0.022, growing by accel*elapsed. Original: tr.f. */
        private Vec2 velocity;
        /** Spread acceleration: a unit vector times the speed. Original: tr.a. */
        private final Vec2 accel;
        /** The stroke itself, constant: a stroke length, not travelled distance. Original: tr.b. */
        private final Vec2 trail;
        private int life;
        private final int color;
        private boolean done;

        Particle(Vec2 position, Vec2 direction, float speed, int life, int color) {
            this.position = position;
            this.velocity = direction.scaled(0.022f);
            this.accel = direction.scaled(speed);
            this.trail = direction.scaled(3.0f);
            this.life = life;
            this.color = color;
        }

        /** Velocity by acceleration first, then position by velocity; an expired particle goes out. Original: tr.b(long). */
        void tick(long elapsed) {
            if (done) {
                return;
            }
            velocity.x += accel.x * elapsed;
            velocity.y += accel.y * elapsed;
            position.x += velocity.x * elapsed;
            position.y += velocity.y * elapsed;
            life -= (int) elapsed;
            if (life <= 0) {
                done = true;
            }
        }

        void draw(Canvas canvas, Paint paint) {
            if (done) {
                return;
            }
            paint.setColor(color);
            canvas.drawLine(position.x - trail.x, position.y - trail.y, position.x, position.y, paint);
        }
    }

    private final Particle[] particles;
    private int life = 800;
    private boolean done;

    DeathBurst(Vec2 position, int count, int color, Random random) {
        particles = new Particle[count];
        float angle = 0.0f;
        for (int i = 0; i < count; i++) {
            angle += random.nextFloat();
            Vec2 start = new Vec2(position.x + (float) Math.sin(angle), position.y + (float) Math.cos(angle));
            Vec2 direction = start.subtract(position);
            direction.normalize();
            particles[i] = new Particle(start, direction, 2.4E-4f + 4.0E-4f * random.nextFloat(),
                    400 + random.nextInt(400), color);
        }
    }

    void tick(long elapsed) {
        if (life <= 0) {
            return;
        }
        life -= (int) elapsed;
        if (life <= 0) {
            done = true;
        }
        for (Particle particle : particles) {
            particle.tick(elapsed);
        }
    }

    boolean isDone() {
        return done;
    }

    void draw(Canvas canvas) {
        for (Particle particle : particles) {
            particle.draw(canvas, paint);
        }
    }
}
