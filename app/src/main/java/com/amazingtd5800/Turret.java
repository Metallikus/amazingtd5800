package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.List;

/**
 * A tower on the field. Reload (a) counts down from the full reload (b); damage (c), projectile speed (d) and
 * lead (e) come from the subclasses, while range (g), price (i), level (j) and upgrade price (k) are shared.
 * Original: dq.
 */
abstract class Turret {

    /** Current reload timer. Original: dq.a. */
    protected int reload;
    /** Full reload time. Original: dq.b. */
    protected int reloadFull = 1200;
    /** Damage. Original: dq.c. */
    protected int damage = 4;
    /** Projectile speed. Original: dq.d. */
    protected float speed = 0.2f;
    /** Share of the target lead. Original: dq.e. */
    protected float lead = 0.3f;
    /** Tower position. Original: dq.f. */
    protected Vec2 position = new Vec2(0, 0);
    /** Range. Original: dq.g. */
    protected int range = 40;
    /** Upgrade-spot type under the tower and its bonus. Original: dq.q, dq.r. */
    private Spot spot = Spot.NONE;
    private int bonus;
    /** The level's projectile list the tower adds its shots to. Original: dq.h. */
    protected List<Projectile> projectiles;
    /** Tower price and what an upgrade adds to it. Original: dq.i. */
    protected int price;
    /** Tower level (max 7). Original: dq.j. */
    protected int level;
    /** Upgrade price. Original: dq.k. */
    protected int upgradePrice = 20;
    /** The original drew with Graphics directly and its frames are one pixel thick, so STROKE, not a fill.
     * This is the tower's own paint. */
    private final Paint frame = new Paint();
    /** The beam is drawn with its own paint so other drawers' colour cannot leak into it. */
    protected final Paint beamPaint = new Paint();
    /** Whether the range is shown. Original: dq.m. */
    protected boolean showRange;
    /** Border of a carried tower: green while the spot under it is free, red when it is not. Original: dq.n. */
    private boolean placeable;
    /** Tower sheet index in Assets; the panel uses it to show the name. */
    private int index;
    /** Reload bar under the tower. Original: dq.o, dq.p. */
    private int shown;
    private int shownFull;
    private Sprite sprite;

    Turret(android.graphics.Bitmap sheet, Vec2 position) {
        this.sprite = new Sprite(sheet, 24, 24);
        this.sprite.setFrame(0);
        reloadBar(1000);
        setPosition(position.x, position.y);
    }

    /** Reload bar under the tower. Original: dq.a(int). */
    protected void reloadBar(int full) {
        this.shownFull = full;
        this.shown = 0;
    }

    /** Tower position. Original: dq.a(ri). */
    void setPosition(float x, float y) {
        this.position = new Vec2((int) (x + 0.5f), (int) (y + 0.5f));
        sprite.setRefPixelPosition((int) (position.x + 0.5f), (int) (position.y + 0.5f));
        sprite.setFrame(level);
    }

    Vec2 position() {
        return position;
    }

    /** The tower's name comes from its sheet, so the panel needs the sheet index. Original: dq.b(). */
    int index() {
        return index;
    }

    void setIndex(int index) {
        this.index = index;
    }

    /** A touch landed on the tower. Original: dq.a(float, float). */
    boolean isAt(float x, float y) {
        return position.distance(x, y) < 20.0f;
    }

    /** The tower is still reloading. Original: dq.i(). */
    boolean reloading() {
        return shown < shownFull;
    }

    /** The level's projectile list. Original: dq.h(). */
    void setProjectiles(List<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    /** Whether the tower shows its range (dq.m): on while it is carried or selected. Original: dq.b(boolean). */
    void setRangeVisible(boolean visible) {
        this.showRange = visible;
    }

    /** Whether the tower fits under the finger (cs.b(dq)); the border colour follows it. Original: dq.a(boolean). */
    void setPlaceable(boolean placeable) {
        this.placeable = placeable;
    }

    /** Range. Original: dq.j(). */
    int range() {
        return range;
    }

    /** The tower stands on an upgrade spot: take its bonus; leaving gives it all back. Original: dq.a(on). */
    void spot(Spot type) {
        if (spot == type) {
            return;
        }
        if (spot != Spot.NONE && type == Spot.NONE) {
            if (spot == Spot.RANGE) {
                range -= bonus;
            } else if (spot == Spot.RELOAD) {
                reloadFull -= bonus;
            } else if (spot == Spot.DAMAGE) {
                damage -= bonus;
            }
            bonus = 0;
        } else if (type == Spot.RANGE) {
            bonus = (int) (range * 0.5f + 0.5f);
            range += bonus;
        } else if (type == Spot.RELOAD) {
            bonus = (int) (reloadFull * -0.3f + 0.5f);
            reloadFull += bonus;
        } else if (type == Spot.DAMAGE) {
            bonus = (int) (damage * 0.5f + 0.5f);
            damage += bonus;
        }
        spot = type;
    }

    /** Damage. Original: dq.k(). */
    int power() {
        return damage;
    }

    /** Fire rate. Original: dq.l(). */
    int rate() {
        return (int) (10000.0f / reloadFull + 0.5f);
    }

    /** Price. Original: dq.a(). */
    int price() {
        return price;
    }

    /** Upgrade price. Original: dq.d(). */
    int upgradePrice() {
        return upgradePrice;
    }

    /** What a sale returns. Original: dq.e(). */
    int sellPrice() {
        return (int) (price * 0.7f + 0.5f);
    }

    /** Tower level. Original: dq.g(). */
    int level() {
        return level;
    }

    /** Whether it can be upgraded again. Original: dq.h(). */
    boolean canUpgrade() {
        return level < 7;
    }

    /** Enough money for the upgrade. Original: dq.a(j). */
    protected boolean canAfford(Hud hud) {
        return hud.money() >= upgradePrice;
    }

    /** Charges the upgrade price and raises price, level and sprite frame. Original: dq.a(j). */
    protected void paid(Hud hud, float factor) {
        hud.spend(upgradePrice);
        price += upgradePrice;
        upgradePrice = (int) (upgradePrice * factor);
        levelUp();
    }

    /** A new tower level is a new sprite frame. Original: dq.j(int). */
    protected void levelUp() {
        level++;
        sprite.setFrame(level);
    }

    /** Accumulates reload, then subtracts it. Original: dq.b(long). */
    void tick(long elapsed) {
        if (shown < shownFull) {
            shown = (int) (shown + elapsed);
            return;
        }
        if (reload > 0) {
            reload -= (int) elapsed;
        }
    }

    /** The tower aims at an enemy that is alive and visible. Original: dq.a(w). */
    boolean canTarget(Enemy enemy) {
        return !enemy.isDying() && enemy.isVisible();
    }

    /** Finds the first target in range and fires. Original: dq.a(Vector). */
    void fire(List<Enemy> enemies) {
        if (reload > 0 || reloading()) {
            return;
        }
        for (Enemy enemy : enemies) {
            if (canTarget(enemy)) {
                Vec2 aim = position.subtract(enemy.position());
                if (aim.length() < range) {
                    shoot(aim, enemy);
                    return;
                }
            }
        }
    }

    /** Vector to the target's lead point. Original: dq.a(ri, w). */
    Vec2 aimAt(Vec2 aim, Enemy enemy) {
        float t = aim.length() / speed * lead;
        Vec2 predicted = enemy.position().add(enemy.velocity().scaled(t));
        return position.subtract(predicted);
    }

    /** Upgrades the tower; true when it succeeded. Original: dq.a(j). */
    abstract boolean upgrade(Hud hud);

    /** The subclass's shot at the target. Original: dq.a(ri, w). */
    void shoot(Vec2 aim, Enemy enemy) {
    }

    /**
     * Sprite, reload bar (dq.a: a 20x3 frame and two 2-pixel fills) and range (dq.c: an outline circle of radius
     * g while the range flag is on — drawArc without useSource).
     * Original: dq.a(Graphics).
     */
    void draw(Canvas canvas) {
        sprite.paint(canvas);
        if (shown < shownFull) {
            int filled = Math.max(0, (int) (19.0f * ((float) shown / shownFull) + 0.5f));
            border(canvas, Rgb.color(0x666666), position.x - 10, position.y - 9, 20, 3);
            fill(canvas, Rgb.color(0), position.x - 9 + filled, position.y - 8, 19 - filled, 2);
            fill(canvas, Rgb.color(65280), position.x - 9, position.y - 8, filled, 2);
        }
        if (showRange) {
            strokeOval(canvas, Rgb.color(65280), position.x - range, position.y - range, range * 2, range * 2);
        }
    }

    /** 24x24 frame around the spot a carried tower is carried over: green when the spot is free. Original: dq.b(Graphics). */
    void drawSelected(Canvas canvas) {
        border(canvas, Rgb.color(placeable ? 65280 : 0xFF0000), position.x - 12, position.y - 12, 24, 24);
    }

    /** x,y,w,h drawn as a one-pixel border (right and bottom pixels included). Original: Graphics.drawRect. */
    private void border(Canvas canvas, int color, float x, float y, int width, int height) {
        frame.setColor(color);
        frame.setStyle(Paint.Style.STROKE);
        frame.setStrokeWidth(1);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, frame);
    }

    /** An oval outline one pixel thick. Original: Graphics.drawArc(x, y, w, h, 0, 360) without useSource. */
    private void strokeOval(Canvas canvas, int color, float x, float y, int width, int height) {
        frame.setColor(color);
        frame.setStyle(Paint.Style.STROKE);
        frame.setStrokeWidth(1);
        canvas.drawOval(new RectF(x, y, x + width - 1, y + height - 1), frame);
    }

    private void fill(Canvas canvas, int color, float x, float y, int width, int height) {
        frame.setColor(color);
        frame.setStyle(Paint.Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, frame);
    }
}
