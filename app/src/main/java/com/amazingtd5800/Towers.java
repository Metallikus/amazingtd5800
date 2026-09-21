package com.amazingtd5800;

import android.graphics.Canvas;

import java.util.List;

/**
 * The eleven level towers (jo, fi, fc, fb, ki, u, nx, oc, pa, nu, kn): each has its own number (c()), price,
 * reload, damage, range and upgrade from dq.a(j).
 * Original: the tower subclasses of dq.
 */
final class Towers {

    private Towers() {
    }

    /** Autobow — the cheap basic tower; from level 3 its arrows are poisoned. Original: jo. */
    static final class Autobow extends Turret {

        private int flight;

        Autobow(android.graphics.Bitmap sheet, Vec2 position) {
            super(sheet, position);
            reload = 3100;
            reloadFull = 3100;
            damage = 10;
            range = 48;
            speed = 0.15f;
            lead = 0.6f;
            flight = (int) (range / speed);
            price = 20;
            upgradePrice = 10;
        }

        @Override
        void shoot(Vec2 aim, Enemy enemy) {
            Vec2 velocity = aimAt(aim, enemy);
            velocity.normalize();
            velocity.scale(speed);
            Projectile.Arrow arrow = new Projectile.Arrow(position().copy(), velocity, damage, flight);
            if (level >= 3 && Randoms.nextFloat() <= 0.3f) {
                arrow.effect = new Poison(2000);
            }
            projectiles.add(arrow);
            reload = reloadFull;
            Sounds.get().fireArrow();
        }

        @Override
        boolean upgrade(Hud hud) {
            if (!canAfford(hud)) {
                return false;
            }
            reloadFull -= 200;
            damage += new int[]{5, 6, 8, 10, 13, 16, 22}[level];
            lead += 0.05f;
            range = (int) (range * 1.08);
            speed += 0.007f;
            flight = (int) (range / speed);
            paid(hud, 1.5f);
            reloadBar(1000 + (1 + level) * 500);
            return true;
        }
    }

    /** Slow Tower — weak damage, its arrow may slow the enemy, and from level 6 it hits through shields. Original: fi. */
    static final class SlowTower extends Turret {

        private float chance = 0.6f;
        private long slowFor = 1000L;

        SlowTower(android.graphics.Bitmap sheet, Vec2 position) {
            super(sheet, position);
            reload = 2800;
            reloadFull = 2800;
            damage = 3;
            range = 35;
            speed = 0.12f;
            price = 25;
            upgradePrice = 12;
        }

        @Override
        void shoot(Vec2 aim, Enemy enemy) {
            Vec2 velocity = aimAt(aim, enemy);
            velocity.normalize();
            velocity.scale(speed);
            // Flight time is this.g / |velocity|, and velocity is already normalised and scaled by speed, so it
            // is range / speed, not the distance to the enemy.
            Projectile.SlowArrow arrow = new Projectile.SlowArrow(position().copy(), velocity, damage,
                    (int) (range / velocity.length()));
            if (Randoms.nextFloat() <= chance) {
                arrow.effect = new Slowing(0.5f, slowFor);
            }
            arrow.setIgnoresShield(level >= 6);
            projectiles.add(arrow);
            reload = reloadFull;
            Sounds.get().fireSlow();
        }

        @Override
        boolean upgrade(Hud hud) {
            if (!canAfford(hud)) {
                return false;
            }
            damage += 2;
            reloadFull -= 25;
            range = (int) (range * 1.1);
            speed *= 1.05f;
            chance += 0.05f;
            slowFor += 500L;
            paid(hud, 1.2f);
            reloadBar(1000 + (1 + level) * 700);
            return true;
        }
    }

    /** Mortar — the mine hits an area; from level 4 it also slows. Original: fc. */
    static final class Mortar extends Turret {

        private int splash = 18;

        Mortar(android.graphics.Bitmap sheet, Vec2 position) {
            super(sheet, position);
            reload = 5000;
            reloadFull = 5000;
            damage = 2;
            range = 36;
            speed = 0.08f;
            lead = 0.3f;
            price = 40;
            upgradePrice = 20;
        }

        @Override
        void shoot(Vec2 aim, Enemy enemy) {
            float distance = aim.length();
            Vec2 velocity = aim.copy();
            velocity.normalize();
            velocity.scale(speed);
            Projectile.Shell shell = new Projectile.Shell(position().copy(), velocity, (int) (distance / velocity.length()), damage,
                    splash);
            if (level >= 4 && Randoms.nextFloat() <= 0.5f) {
                shell.effect = new Slowing(0.9f, 300L);
            }
            projectiles.add(shell);
            reload = reloadFull;
        }

        @Override
        boolean upgrade(Hud hud) {
            if (!canAfford(hud)) {
                return false;
            }
            reloadFull -= 100 + 20 * level;
            range = (int) (range * 1.05);
            damage += new int[]{1, 1, 2, 3, 3, 4, 2}[level];
            splash += 1;
            paid(hud, 1.35f);
            reloadBar(1000 + (1 + level) * 900);
            return true;
        }
    }

    /** Chaingun — 40 bullets per reload; especially good in corners and at path crossings. Original: fb. */
    static final class Chaingun extends Turret {

        private int burst = 3000;
        private int left;

        Chaingun(android.graphics.Bitmap sheet, Vec2 position) {
            super(sheet, position);
            reload = 450;
            reloadFull = 450;
            damage = 4;
            range = 36;
            speed = 0.14f;
            lead = 0.6f;
            this.left = 40;
            price = 35;
            upgradePrice = 16;
        }

        @Override
        void fire(List<Enemy> enemies) {
            if (reload > 0 || reloading()) {
                return;
            }
            for (Enemy enemy : enemies) {
                if (!canTarget(enemy)) {
                    continue;
                }
                Vec2 velocity = position.subtract(enemy.position());
                if (velocity.length() >= range) {
                    continue;
                }
                velocity.normalize();
                velocity.scale(speed);
                projectiles.add(new Projectile.Bullet(position().copy(), velocity, damage, (int) (range / speed)));
                left--;
                if (left == 0) {
                    reload = burst;
                    left = 40;
                } else {
                    reload = reloadFull;
                }
                Sounds.get().fireBullet();
                return;
            }
        }

        @Override
        boolean upgrade(Hud hud) {
            if (!canAfford(hud)) {
                return false;
            }
            reloadFull -= 20 + 2 * level;
            burst -= 100 + level * 10;
            speed *= 1.05f;
            if (level % 2 == 0) {
                range = (int) (range * 1.07);
            }
            damage += new int[]{2, 2, 2, 3, 4, 3, 5}[level];
            paid(hud, 1.4f);
            reloadBar(1000 + (1 + level) * 600);
            return true;
        }
    }

    /** Pulsed Laser hits one target, chains at levels 2 and 4, and stuns at 6. Original: u. */
    static final class PulsedLaser extends Turret {

        private int beam;
        private final Vec2[] chain = new Vec2[3];
        private final Enemy[] targets = new Enemy[3];

        PulsedLaser(android.graphics.Bitmap sheet, Vec2 position) {
            super(sheet, position);
            reload = 4700;
            reloadFull = 4700;
            damage = 16;
            range = 60;
            price = 50;
            upgradePrice = 25;
        }

        @Override
        void tick(long elapsed) {
            super.tick(elapsed);
            if (beam > 0) {
                beam -= (int) elapsed;
            }
        }

        @Override
        void fire(List<Enemy> enemies) {
            if (reload > 0 || reloading()) {
                return;
            }
            for (int i = 0; i < chain.length; i++) {
                chain[i] = null;
                targets[i] = null;
            }
            Enemy first = closest(enemies, position(), range, null);
            targets[0] = first;
            if (first != null) {
                if (level >= 2) {
                    targets[1] = closest(enemies, first.position(), range, targets[0]);
                    if (level >= 4 && targets[1] != null) {
                        targets[2] = closest(enemies, targets[1].position(), range, targets[0], targets[1]);
                    }
                }
                reload = reloadFull;
                beam = 200;
                Sounds.get().fireLaser();
            }
            for (int i = 0; i < chain.length; i++) {
                if (targets[i] == null) {
                    break;
                }
                chain[i] = targets[i].position();
                targets[i].damageIfUnshielded(damage, 3);
                if (level >= 6) {
                    targets[i].addEffect(new Stun(300L));
                }
            }
        }

        @Override
        void draw(Canvas canvas) {
            super.draw(canvas);
            if (beam > 0 && chain[0] != null) {
                beamPaint.setColor(Rgb.color(0xCCCCFF));
                canvas.drawLine(position.x, position.y, chain[0].x, chain[0].y, beamPaint);
                if (level >= 2 && chain[1] != null) {
                    canvas.drawLine(chain[0].x, chain[0].y, chain[1].x, chain[1].y, beamPaint);
                    if (level >= 4 && chain[2] != null) {
                        canvas.drawLine(chain[1].x, chain[1].y, chain[2].x, chain[2].y, beamPaint);
                    }
                }
            }
        }

        @Override
        boolean upgrade(Hud hud) {
            if (!canAfford(hud)) {
                return false;
            }
            reloadFull -= 160 + level * 10;
            range = (int) (range * 1.05);
            damage += new int[]{4, 8, 0, 8, 4, 10, 14}[level];
            paid(hud, 1.36f);
            reloadBar(1000 + (1 + level) * 1000);
            return true;
        }
    }

    /** Detector deals no damage but makes invisible enemies visible to the other towers. Original: nx. */
    static final class Detector extends Turret {

        private long detected = 1600L;

        Detector(android.graphics.Bitmap sheet, Vec2 position) {
            super(sheet, position);
            reload = 10;
            reloadFull = 10;
            damage = 0;
            range = 30;
            speed = 0.14f;
            price = 20;
            upgradePrice = 10;
        }

        @Override
        boolean canTarget(Enemy enemy) {
            return !enemy.isDying() && !enemy.isVisible();
        }

        @Override
        void fire(List<Enemy> enemies) {
            if (reloading()) {
                return;
            }
            for (Enemy enemy : enemies) {
                if (canTarget(enemy) && position.distance(enemy.position()) < range) {
                    enemy.addEffect(new Detect(detected));
                    Sounds.get().fireDetector();
                }
            }
        }

        @Override
        boolean upgrade(Hud hud) {
            if (!canAfford(hud)) {
                return false;
            }
            range += 8;
            detected += 200L;
            paid(hud, 1.0f);
            reloadBar(1000 + (1 + level) * 400);
            return true;
        }
    }

    /** Money Tower does not shoot; it makes the enemy worth more. Original: oc. */
    static final class MoneyTower extends Turret {

        private int time = 2000;
        private int amount = 2;

        MoneyTower(android.graphics.Bitmap sheet, Vec2 position) {
            super(sheet, position);
            reload = 3500;
            reloadFull = 3500;
            damage = 0;
            range = 40;
            speed = 0.16f;
            price = 30;
            upgradePrice = 20;
        }

        @Override
        void shoot(Vec2 aim, Enemy enemy) {
            Vec2 velocity = aimAt(aim, enemy);
            velocity.normalize();
            velocity.scale(speed);
            // Like fi: flight time is range / |_scaled velocity|, i.e. range / speed. Original: oc.
            Projectile.Arrow arrow = new Projectile.Arrow(position().copy(), velocity, damage,
                    (int) (range / velocity.length()));
            if (Randoms.nextFloat() <= 0.6f) {
                arrow.effect = new Money(amount, time, false);
            }
            projectiles.add(arrow);
            reload = reloadFull;
            Sounds.get().fireMoney();
        }

        @Override
        boolean upgrade(Hud hud) {
            if (!canAfford(hud)) {
                return false;
            }
            reloadFull -= 100;
            speed *= 1.05f;
            range = (int) (range * 1.08);
            if (level == 3 || level == 6) {
                amount++;
            }
            time += 400;
            paid(hud, 1.2f);
            reloadBar(1000 + (1 + level) * 600);
            return true;
        }
    }

    /** Sniper — a strong bullet that pierces shields, and the targets it holds. Original: pa. */
    static final class Sniper extends Turret {

        private int targets = 1;

        Sniper(android.graphics.Bitmap sheet, Vec2 position) {
            super(sheet, position);
            reload = 4500;
            reloadFull = 4500;
            damage = 24;
            range = 70;
            speed = 0.2f;
            lead = 0.8f;
            price = 50;
            upgradePrice = 20;
        }

        @Override
        void fire(List<Enemy> enemies) {
            if (reload > 0 || reloading()) {
                return;
            }
            int hit = 0;
            for (Enemy enemy : enemies) {
                if (!canTarget(enemy)) {
                    continue;
                }
                Vec2 aim = position.subtract(enemy.position());
                if (aim.length() >= range) {
                    continue;
                }
                Vec2 velocity = aimAt(aim, enemy);
                velocity.normalize();
                velocity.scale(speed);
                Projectile.SniperShot shot = new Projectile.SniperShot(position().copy(), velocity, enemy.position().copy(), damage,
                        (int) (range / speed));
                shot.setIgnoresShield(true);
                projectiles.add(shot);
                reload = reloadFull;
                Sounds.get().fireSniper();
                hit++;
                if (hit == targets) {
                    break;
                }
            }
        }

        @Override
        boolean upgrade(Hud hud) {
            if (!canAfford(hud)) {
                return false;
            }
            reloadFull -= 100;
            range += 3;
            lead += 0.02f;
            damage += new int[]{10, 13, 19, 26, 36, 50, 71}[level];
            paid(hud, 1.4f);
            reloadBar(1000 + (1 + level) * 1000);
            if (level >= 7) {
                targets = 2;
            }
            return true;
        }
    }

    /** Tracking Laser — the beam holds its target while it stays in range. Original: nu. */
    static final class TrackingLaser extends Turret {

        private int targets = 1;
        private Enemy locked;

        TrackingLaser(android.graphics.Bitmap sheet, Vec2 position) {
            super(sheet, position);
            reload = 360;
            reloadFull = 360;
            damage = 3;
            range = 38;
            price = 60;
            upgradePrice = 22;
        }

        @Override
        void fire(List<Enemy> enemies) {
            if (reload > 0 || reloading()) {
                return;
            }
            if (locked != null && !locked.isDying()) {
                Vec2 aim = position.subtract(locked.position());
                if (aim.length() < range) {
                    reload = reloadFull;
                    locked.damageIfUnshielded(damage, 3);
                    Sounds.get().fireTracking();
                    return;
                }
            }
            locked = null;
            int left = targets;
            for (Enemy enemy : enemies) {
                if (!canTarget(enemy)) {
                    continue;
                }
                if (position.distance(enemy.position()) >= range) {
                    continue;
                }
                locked = enemy;
                if (--left == 0) {
                    break;
                }
            }
        }

        @Override
        void draw(Canvas canvas) {
            super.draw(canvas);
            if (locked != null && locked.isVisible() && !locked.isDying() && !reloading()) {
                beamPaint.setColor(Rgb.color(0xFFFFFF));
                canvas.drawLine(position.x, position.y, locked.position().x, locked.position().y, beamPaint);
            }
        }

        @Override
        boolean upgrade(Hud hud) {
            if (!canAfford(hud)) {
                return false;
            }
            reloadFull -= new int[]{18, 22, 26, 32, 35, 37, 38}[level];
            range = (int) (range * 1.08);
            damage++;
            paid(hud, 1.3f);
            reloadBar(1000 + (1 + level) * 1100);
            return true;
        }
    }

    /** Missile — a homing rocket with area damage; the target is picked at random. Original: kn. */
    static final class MissileTower extends Turret {

        private int flight;

        MissileTower(android.graphics.Bitmap sheet, Vec2 position) {
            super(sheet, position);
            reload = 7000;
            reloadFull = 7000;
            damage = 5;
            range = 700;
            speed = 0.14f;
            lead = 0.2f;
            this.flight = (int) (range / speed);
            price = 80;
            upgradePrice = 20;
        }

        @Override
        void fire(List<Enemy> enemies) {
            if (reload > 0 || reloading()) {
                return;
            }
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(Randoms.pick(enemies.size()));
                if (!canTarget(enemy)) {
                    continue;
                }
                // Rocket velocity is a random vector over the whole field (ri(random(ca.c), random(ca.d))). The
                // target is the enemy itself (w2.a() = w.i), not the firing point: ln.i holds the live vector the
                // enemy moves every tick, so the rocket turns onto the enemy instead of flying to the launch point.
                Vec2 velocity = new Vec2(Randoms.between(0, GameScreen.WIDTH),
                        Randoms.between(0, GameScreen.HEIGHT - 58));
                velocity.normalize();
                velocity.scale(speed);
                projectiles.add(new Projectile.Missile(position().copy(), velocity, enemy.position(), damage,
                        (int) (flight * Randoms.between(0.5f, 1.0f) + 0.5f)));
                reload = (int) (reloadFull * Randoms.between(0.7f, 1.0f) + 0.5f);
                Sounds.get().fireSniper();
                return;
            }
        }

        @Override
        boolean upgrade(Hud hud) {
            if (!canAfford(hud)) {
                return false;
            }
            reloadFull -= 100;
            lead += 0.02f;
            damage += new int[]{1, 2, 3, 3, 5, 6, 8}[level];
            paid(hud, 1.4f);
            flight = (int) (range / speed);
            reloadBar(1000 + (1 + level) * 1000);
            return true;
        }
    }

    /** Force Field Tower — the field between two towers hits enemies passing through it. Original: ki. */
    static final class ForceField extends Turret {

        /** The tower that linked to this one. The last in a chain has none, which is why the first upgrades the chain. */
        private ForceField linked;
        /** Beam end: the position of the tower that linked in. Original: ki.o. */
        private Vec2 linkedPosition;
        /** Beam thickness; while the tower is reloaded and waiting it changes every tick, so the beam pulses. Original: ki.p. */
        private int pulse = 1;
        /** Nothing has linked to this tower yet. Original: ki.q. */
        private boolean unlinked = true;
        /** How long the enemy's shield stays off. Original: ki.r. */
        private long field = 1800L;

        ForceField(android.graphics.Bitmap sheet, Vec2 position) {
            super(sheet, position);
            reload = 170;
            reloadFull = 170;
            damage = 4;
            range = 58;
            price = 40;
            upgradePrice = 50;
        }

        @Override
        void tick(long elapsed) {
            super.tick(elapsed);
            if (reload <= 0) {
                pulse = 1 + Randoms.pick(3);
            }
        }

        @Override
        void fire(List<Enemy> enemies) {
            if (reload > 0 || reloading() || linkedPosition == null || pulse <= 1) {
                return;
            }
            reload = (int) (reloadFull + reloadFull * 0.2f * Randoms.nextFloat());
            for (Enemy enemy : enemies) {
                if (canTarget(enemy)
                        && Vec2.distanceToSegment(position, linkedPosition, enemy.position())
                        <= enemy.size() >> 1) {
                    enemy.damageIfUnshielded(damage, 4);
                    if (enemy.isShielded() && !enemy.isDying()) {
                        enemy.addEffect(new ShieldDown(field));
                    }
                    Sounds.get().forceField();
                }
            }
        }

        /**
         * The beam to the linked tower: the same f.a quadrilateral at thickness pulse, which is why it pulses.
         * Original: ki.a(Graphics).
         */
        @Override
        void draw(Canvas canvas) {
            super.draw(canvas);
            if (linkedPosition == null) {
                return;
            }
            beamPaint.setColor(Rgb.color(0x11FF11));
            Path.band(canvas, beamPaint, position, linkedPosition, pulse);
        }

        /**
         * A tower links to the last tower of the same type built in range. The link is one-way: the older tower
         * keeps the reference, so it drives the beam and the upgrade, not the one that linked in.
         * Original: ki.c(Vector).
         */
        void link(List<Turret> towers) {
            if (linked != null) {
                return;
            }
            for (int i = towers.size() - 1; i >= 0; i--) {
                Turret other = towers.get(i);
                if (other instanceof ForceField && other != this && ((ForceField) other).linked != this
                        && position.distance(other.position()) <= range) {
                    ((ForceField) other).linked = this;
                    ((ForceField) other).linkedPosition = position;
                    unlinked = false;
                    return;
                }
            }
        }

        /** A tower was sold from the chain: the link breaks and the chain must be rebuilt. Original: ki.a(ki). */
        boolean unlink(ForceField sold) {
            if (linked != sold) {
                return false;
            }
            linked = null;
            linkedPosition = null;
            unlinked = true;
            return true;
        }

        /** Only a tower something has linked to can be upgraded. Original: ki.h(). */
        @Override
        boolean canUpgrade() {
            return level < 7 && linked != null && unlinked;
        }

        @Override
        boolean upgrade(Hud hud) {
            return upgrade(hud, true);
        }

        /** Upgrading the selected tower upgrades the whole chain; the others are not charged. Original: ki.a(j, boolean). */
        private boolean upgrade(Hud hud, boolean payer) {
            if (hud.money() < upgradePrice) {
                return false;
            }
            reloadFull -= 10;
            damage += 2;
            field += 100L * level;
            if (linked != null) {
                linked.upgrade(hud, false);
            }
            if (payer) {
                hud.spend(upgradePrice);
            }
            upgradePrice = (int) (upgradePrice * 1.5f);
            levelUp();
            reloadBar(1000 + (1 + level) * 1100);
            return true;
        }
    }

    /** Finds the first target in range and fires — the common case for plain towers. Original: dq.a(Vector). */
    static Enemy closest(List<Enemy> enemies, Vec2 from, float range, Enemy... skip) {
        if (skip.length == 1 && skip[0] == null) {
            return closest(enemies, from, range);
        }
        Enemy found = null;
        float best = 0;
        for (Enemy enemy : enemies) {
            if (skip != null) {
                boolean skip2 = false;
                for (Enemy s : skip) {
                    if (s == enemy) {
                        skip2 = true;
                    }
                }
                if (skip2) {
                    continue;
                }
            }
            float d = from.distance(enemy.position());
            if (d < range && (best == 0 || d < best)) {
                best = d;
                found = enemy;
            }
        }
        return found;
    }
}
