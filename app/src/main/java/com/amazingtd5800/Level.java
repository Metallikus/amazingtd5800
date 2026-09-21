package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * A level: path points, waves, enemies, towers, projectiles and floating texts. The enemy entry point and the road
 * layers are drawn here too.
 * Original: ca (ca.a(Graphics, dd, int, int)).
 */
abstract class Level implements Enemy.EnemyListener {

    /** A coordinate snapped to the 8-pixel grid. Original: ca.a(int). */
    static int snap(int coordinate) {
        return Path.snap(coordinate);
    }

    private final List<Wave> waves = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    /** Enemies the wave already created but that have not entered the path yet. Original: ca.u. */
    private final List<Enemy> waiting = new ArrayList<>();
    private final List<Turret> towers = new ArrayList<>();
    private final List<Projectile> projectiles = new ArrayList<>();
    /** Coins the player has to tap. Original: ca.o. */
    private final Coin[] coins = new Coin[3];
    private final List<Path> paths = new ArrayList<>();
    /** Upgrade spots: a 24×24 square with its own type; a tower exactly at its centre gets the bonus. Original: fy.f. */
    private final List<Enhanced> spots = new ArrayList<>();
    /** Rectangles a tower may not be placed on. Original: fy.e. */
    private final List<int[]> blockedRects = new ArrayList<>();
    private final Hud hud;
    /** Level number (level 1 is LevMoon). Original: ca.d. */
    private final int number;
    /** Level background; from level two on it no longer matches the level number. Original: cj.a(int). */
    private final int background;
    /** The current wave. Original: ca.l. */
    private Wave wave;
    /** Wave number. The HUD shows "Wave: 1/N" from the start (j.a(int,int) calls a(1)), but a wave only gets its
     * number when the wave itself starts, so this counts from zero, not from one. Original: ca.m. */
    private int waveNumber;
    /** "Send now" is visible while a wave is running (ca.r), and the road arrows blink on ca.s. Original: ca.r, ca.s, ca.t. */
    private boolean sendNowVisible;
    /** Blink phase of the active wave's arrows; the original started it at true. Original: ca.s. */
    private boolean blink = true;
    private int blinkTimer = 400;
    /** The wave-bonus text. Original: ca.n. */
    private FloatingText bonus;
    /** The selected tower. Original: ca.k. */
    private Turret selected;
    /** Own paint: drawBitmap multiplies pixels by the paint colour, so it is white and unfiltered, like Screen.bitmapPaint. */
    private final Paint sprite = new Paint();

    {
        sprite.setFilterBitmap(false);
        sprite.setColor(android.graphics.Color.WHITE);
    }

    Level(Hud hud, int number, int background) {
        this.hud = hud;
        this.number = number;
        this.background = background;
    }

    /** An upgrade spot: a 24×24 square with its own type; a tower exactly at its centre gets the bonus. Original: fy.f. */
    void spot(int left, int top, Spot type) {
        spots.add(new Enhanced(left, top, left + 24, top + 24, type));
    }

    /** A rectangle a tower may not be placed on. Original: fy.e. */
    void blocked(int left, int top, int right, int bottom) {
        blockedRects.add(new int[]{left, top, right, bottom});
    }

    /** Level number. Original: ca.d. */
    int number() {
        return number;
    }

    /**
     * The level for its own number: gb, fv, fw, fx, fz, fp, fn, fu, fs, qn, qj, qk, qh, qi, qe, qf, qc, qd, qb.
     * Original: ca and its subclasses.
     */
    static Level of(Hud hud, int number) {
        switch (number) {
            case 1:
                return new LevelOne(hud, number);
            case 2:
                return new LevelTwo(hud, number);
            case 3:
                return new LevelThree(hud, number);
            case 4:
                return new LevelFour(hud, number);
            case 5:
                return new LevelFive(hud, number);
            case 6:
                return new LevelSix(hud, number);
            case 7:
                return new LevelSeven(hud, number);
            case 8:
                return new LevelEight(hud, number);
            case 9:
                return new LevelNine(hud, number);
            case 10:
                return new LevelTen(hud, number);
            case 11:
                return new LevelEleven(hud, number);
            case 12:
                return new LevelTwelve(hud, number);
            case 13:
                return new LevelThirteen(hud, number);
            case 14:
                return new LevelFourteen(hud, number);
            case 15:
                return new LevelFifteen(hud, number);
            case 16:
                return new LevelSixteen(hud, number);
            case 17:
                return new LevelSeventeen(hud, number);
            case 18:
                return new LevelEighteen(hud, number);
            case 19:
                return new LevelNineteen(hud, number);
            default:
                throw new IllegalArgumentException("there is no level " + number);
        }
    }

    /** Level background; from the first level on it does not equal the level number. Original: cj.a(int). */
    int background() {
        return background;
    }

    /**
     * 33 points of a radius-60 arc centred on the field's axis; of the original's seven arguments only the span in
     * degrees (the step is a third of it out of 32) and the Y-axis centre survive. Level 5, the only level that
     * calls this, is one of the two that leave their waypoints off the grid, so nothing here snaps.
     * Original: ca.a(dd, n2, n3, n4, n5, n6, n7).
     */
    static void arc(Path path, int spanDegrees, int centerY) {
        double step = spanDegrees / 32.0 * Math.PI / 180.0;
        double angle = 0.0;
        for (int i = 0; i < 33; i++) {
            path.add(new Vec2(180 + (int) (Math.sin(angle) * 60.0 + 0.5),
                    centerY - (int) (Math.cos(angle) * 60.0 + 0.5)));
            angle += step;
        }
    }

    /** Number of waves. Original: ca.a. */
    int waves() {
        return waves.size();
    }

    /** Adds a wave and returns the time to its last enemy. Original: ca.a(bb). */
    protected final long addWave(Wave wave) {
        waves.add(wave);
        return wave.timer() + wave.total();
    }

    /**
     * A path of the level; {@link #snapPoints(Path)} gets it before the road is drawn, as the original hands it to
     * its own hook there. Original: ca.b(dd).
     */
    protected final void addPath(Path path) {
        paths.add(path);
    }

    /**
     * What the road does to a path before it draws it: put the waypoints on the 8-pixel grid. Levels 5 and 10
     * override this with the original's empty body, because an arc point and a spiral point that lose their last
     * 3 pixels to the grid stop being a curve at all. Called every frame, like the original, and idempotent.
     * Original: ca.a(dd).
     */
    protected void snapPoints(Path path) {
        path.snapToGrid();
    }

    /** One game step: waves, enemies, towers, projectiles and texts. Original: ca.b(long). */
    void tick(long elapsed) {
        if (sendNowVisible) {
            blinkTimer -= (int) elapsed;
            if (blinkTimer <= 0) {
                blink = !blink;
                blinkTimer = 600;
            }
        }
        for (Coin coin : coins) {
            if (coin != null) {
                coin.tick(elapsed);
            }
        }
        if (wave == null) {
            if (enemies.isEmpty() && !waves.isEmpty()) {
                sendNowVisible = true;
                wave = waves.remove(0);
                hud.nextWaveIn(wave.timer());
                // The bonus is paid only for a wave already cleared (ca.m > 0) and is named after its number, so
                // before the first wave there is neither a bonus nor a text.
                if (waveNumber > 0) {
                    int bonus = 20 + (int) (hud.money() * 0.05f + 0.5f);
                    this.bonus = new FloatingText("Wave " + waveNumber + " bonus: $" + bonus,
                            GameScreen.WIDTH / 2, GameScreen.HEIGHT / 3);
                    hud.earn(bonus);
                    hud.addScore(bonus);
                }
                spawn(wave);
                ++waveNumber;
                hud.setWave(waveNumber);
            }
        } else if (wave.tick(elapsed)) {
            activate();
            waiting.clear();
            sendNowVisible = false;
            if (wave.isDone()) {
                wave = null;
                checkCleared();
            } else {
                spawn(wave);
            }
        }
        hud.tick(elapsed);
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isRemoved()) {
                enemies.remove(i);
                i--;
                checkCleared();
            } else {
                enemy.tick(elapsed);
            }
        }
        for (Turret tower : towers) {
            tower.tick(elapsed);
            tower.fire(enemies);
        }
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            projectile.tick(elapsed);
            if (projectile.isDone()) {
                projectiles.remove(i);
                i--;
                continue;
            }
            for (Enemy enemy : enemies) {
                // The projectile itself lands the hit, and it alone puts its own effect on the enemy. Original: ca: ga2.a(w3).
                if (!enemy.isDying() && projectile.hits(enemy)) {
                    projectile.hit(enemy);
                    if (!projectile.pierces()) {
                        projectiles.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }
        if (bonus != null) {
            bonus.tick(elapsed);
            if (bonus.isDone()) {
                bonus = null;
            }
        }
    }

    /** The wave's enemies move onto their path points. Original: ca.f(). */
    private void spawn(Wave wave) {
        wave.spawn(paths.toArray(new Path[0]), waiting);
    }

    /** The wave's enemies become active and get their listener. Original: ca.a(Vector). */
    private void activate() {
        for (Enemy enemy : waiting) {
            enemy.setListener(this);
            enemies.add(enemy);
        }
    }

    /** All waves are done and no enemies are left: money becomes score and the level is cleared. Original: ca.i(). */
    private void checkCleared() {
        if (!hud.cleared() && waves.isEmpty() && enemies.isEmpty()) {
            hud.addScore(hud.money());
            hud.clear();
        }
    }

    /** An enemy died: score, reward and, one time in five, a coin (AMazingTDMidlet.a(5) == 1). Original: ca.a(w). */
    @Override
    public void killed(Enemy enemy) {
        hud.enemyKilled(enemy);
        if (Randoms.pick(5) == 1) {
            for (int i = 0; i < coins.length; i++) {
                if (coins[i] == null) {
                    coins[i] = new Coin(enemy.position(), 5);
                    return;
                }
                if (coins[i].isDone()) {
                    coins[i].restart(enemy.position(), 5);
                    return;
                }
            }
        }
    }

    /** Tapping a coin gives money. Original: ca.a_(int, int). */
    boolean tapped(float x, float y) {
        for (Coin coin : coins) {
            if (coin != null && coin.tapped(x, y)) {
                return true;
            }
        }
        return false;
    }

    /** An enemy reached the end of the path: a life is lost (j.b(int)). Original: ca.e(). */
    @Override
    public void reachedEnd(Enemy enemy) {
        hud.enemyReachedEnd();
    }

    /** Upgrade spots under the roads, the level background and the roads themselves: the wide layer in the road
     * colour, the narrow one in the path colour, arrows along the axis.
     * Original: fy.b(Graphics). */
    void drawBackground(Canvas canvas, Settings settings) {
        for (Enhanced spot : spots) {
            canvas.drawBitmap(Assets.get().enhancedSpot(spot.type.frame() * 24), spot.left, spot.top, sprite);
        }
        for (int[] rect : blockedRects) {
            // drawRGB(..., true) took alpha from the top byte, so the area under the upgrade spots stays
            // see-through and the spot squares are drawn over it in their own colour.
            sprite.setColor(0x55663333);
            canvas.drawRect(rect[0], rect[1], rect[2], rect[3], sprite);
            sprite.setColor(android.graphics.Color.WHITE);
        }
        for (int i = 0; i < paths.size(); i++) {
            Path path = paths.get(i);
            snapPoints(path);
            path.draw(canvas, settings.roadColor(), settings.pathAccent(i), settings.arrowColor(i));
        }
    }

    /** The upgrade-spot icon is drawn until a tower occupies the spot. Original: fy.a(Graphics). */
    void drawSpots(Canvas canvas) {
        for (Enhanced spot : spots) {
            if (!spot.taken) {
                canvas.drawBitmap(Assets.get().enhancedSpotIcon(spot.type.frame() * 24), spot.left, spot.top,
                        sprite);
            }
        }
    }

    /** The active wave's arrows blink only on its own path points (ca.l.b()) and only in their blink phase
     * (ca.r && ca.s) while "Send now" is awaited.
     * Original: ca.a(Graphics). */
    void drawArrows(Canvas canvas, Settings settings) {
        if (arrowsBlink()) {
            for (int index : wave.pathIndexes()) {
                paths.get(index).drawArrows(canvas, settings.roadColor());
            }
        }
    }

    /** Reserves, towers, enemies, projectiles, coins and texts. Original: ca.a(Graphics). */
    void draw(Canvas canvas, Settings settings) {
        drawSpots(canvas);
        drawArrows(canvas, settings);
        for (Enemy enemy : waiting) {
            enemy.draw(canvas);
        }
        for (Turret tower : towers) {
            tower.draw(canvas);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
        }
        for (Projectile projectile : projectiles) {
            projectile.draw(canvas);
        }
        for (Coin coin : coins) {
            if (coin != null) {
                coin.draw(canvas);
            }
        }
        if (bonus != null) {
            bonus.draw(canvas, settings.buttonColor());
        }
    }

    /**
     * Places a tower, sliding it along the grid when the spot does not fit. The slide chain probes with the same
     * full condition as ca.a(dq) with fy.a(dq) — tower, road, upgrade spots and rectangles — and with the same point
     * the tower carries, so the tower reaches the centre of the upgrade spot instead of stopping beside it.
     * Original: ca.a(dq, boolean).
     */
    boolean place(Turret tower, boolean adjust) {
        Vec2 position = snap(tower.position());
        position.x += 4.0f;
        position.y += 4.0f;
        if (adjust && !placeable(tower)) {
            position.y += 8.0f;
            if (!placeable(tower)) {
                position.y -= 16.0f;
                if (!placeable(tower)) {
                    position.y += 8.0f;
                    position.x += 8.0f;
                    if (!placeable(tower)) {
                        position.x -= 16.0f;
                        if (!placeable(tower)) {
                            position.y += 8.0f;
                            if (!placeable(tower)) {
                                position.y -= 16.0f;
                                if (!placeable(tower)) {
                                    position.x += 16.0f;
                                    if (!placeable(tower)) {
                                        position.y += 16.0f;
                                        if (!placeable(tower)) {
                                            position.x -= 8.0f;
                                            position.y -= 8.0f;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        tower.setPosition(position.x, position.y);
        tower.spot(Spot.NONE);
        for (Enhanced spot : spots) {
            if (spot.hits(tower)) {
                tower.spot(spot.type);
                return true;
            }
            if (spot.blocks(tower)) {
                return false;
            }
        }
        for (int[] rect : blockedRects) {
            if (Enhanced.blocks(rect, tower)) {
                return false;
            }
        }
        return true;
    }

    /** A tower already stands here, or this is too close to the road. Original: ca.a(float, float), ca.a(dq). */
    private boolean blocked(Turret tower) {
        Vec2 position = tower.position();
        for (Turret other : towers) {
            if (other.isAt(position.x, position.y)) {
                return true;
            }
        }
        for (Path path : paths) {
            if (path.blocks(position, 16.0f)) {
                return true;
            }
        }
        return false;
    }

    /**
     * An upgrade spot or a rectangle also blocks the tower. The upgrade spot wins: fy.a(dq) leaves its loop at the
     * first spot the tower covers and never reaches the rectangles, so inside a shaded area the tower lands exactly
     * at its centre.
     * Original: fy.a(dq).
     */
    private boolean blockedBy(Turret tower) {
        for (Enhanced spot : spots) {
            if (spot.hits(tower)) {
                return false;
            }
            if (spot.blocks(tower)) {
                return true;
            }
        }
        for (int[] rect : blockedRects) {
            if (Enhanced.blocks(rect, tower)) {
                return true;
            }
        }
        return false;
    }

    /** A tower can be placed at this point: nothing is in the way, the road is not near, nor is an upgrade spot or
     * a rectangle. Original: ca.a(dq), fy.a(dq). */
    boolean placeable(Turret tower) {
        return !blocked(tower) && !blockedBy(tower);
    }

    /** The tower is bought and shoots at the level's enemies. Original: ca.b(dq). */
    void addTower(Turret tower) {
        tower.setProjectiles(projectiles);
        towers.add(tower);
        for (Enhanced spot : spots) {
            spot.taken = spot.hits(tower);
        }
        if (tower instanceof Towers.ForceField) {
            ((Towers.ForceField) tower).link(towers);
        }
    }

    /** The tower is removed, money is refunded, the upgrade spot is freed and the force-field chain is rebuilt.
     * Original: ca.c(dq), fy.c(dq). */
    void sell(Turret tower) {
        towers.remove(tower);
        for (Enhanced spot : spots) {
            if (spot.hits(tower)) {
                spot.taken = false;
            }
        }
        if (tower instanceof Towers.ForceField) {
            for (Turret other : towers) {
                if (other instanceof Towers.ForceField
                        && ((Towers.ForceField) other).unlink((Towers.ForceField) tower)) {
                    ((Towers.ForceField) other).link(towers);
                }
            }
        }
        hud.earn(tower.sellPrice());
        deselect();
    }

    /** The tower under the touch point. Original: ca.a(float, float). */
    Turret towerAt(float x, float y) {
        for (Turret tower : towers) {
            if (tower.isAt(x, y)) {
                return tower;
            }
        }
        return null;
    }

    /** Whether there is enough money. Original: ca.b(int). */
    boolean canAfford(int price) {
        return hud.money() >= price;
    }

    /** Spends money. Original: ca.c(int). */
    void spend(int amount) {
        hud.spend(amount);
    }

    /** The selected tower — the original called dq.b(true) on it, i.e. showed its range. Original: ca.k. */
    void select(Turret tower) {
        deselect();
        selected = tower;
        if (tower != null) {
            tower.setRangeVisible(true);
        }
    }

    /** The tower is deselected and its range goes dark. Original: ca.h(). */
    void deselect() {
        if (selected != null) {
            selected.setRangeVisible(false);
            selected = null;
        }
    }

    Turret selected() {
        return selected;
    }

    List<Turret> towers() {
        return towers;
    }

    List<Enemy> enemies() {
        return enemies;
    }

    /** Tapping the button zeroes the wave timer and hides the button. Original: ca.e(ca).a = 0 and ca.f(ca).b(false) (eg.a). */
    void sendNow() {
        if (wave != null) {
            wave.setTimer(0);
            hud.nextWaveIn(0);
            sendNowVisible = false;
        }
    }

    /** "Send now" is visible and takes a touch only while a wave is waiting its turn. Original: ca.p.b(ca.r), hy.b. */
    boolean sendNowVisible() {
        return sendNowVisible;
    }

    /** if (this.r && this.s) — the active wave's arrows blink only in their own blink phase. Original: ca.a(Graphics). */
    boolean arrowsBlink() {
        return sendNowVisible && blink;
    }

    /** Out of lives: the screen shows the fail panel and the level stops ticking. Original: j.c(). */
    boolean failed() {
        return hud.livesCount() <= 0;
    }

    /** Flag g — the level is cleared. Original: j.d(). */
    boolean cleared() {
        return hud.cleared();
    }

    Hud hud() {
        return hud;
    }

    List<Path> paths() {
        return paths;
    }

    /**
     * A tower's coordinates snap to the grid: the point is fixed in place and that same point is returned, and
     * since ca.a(ri, boolean) moves exactly that point, the slide chain probes an already-snapped point and the
     * tower reaches the centre of the upgrade spot.
     * Original: ca.a(ri).
     */
    private Vec2 snap(Vec2 position) {
        position.x = snap((int) (position.x + 0.5f));
        position.y = snap((int) (position.y + 0.5f));
        return position;
    }

    /** The wave-bonus text; lives 5 seconds. Original: og. */
    private final class FloatingText {

        private final String text;
        private final int x;
        private final int y;
        private final GameFont font = Assets.get().textFont();
        /** Own paint: a shared Paint would carry the colour of whoever drew before the text. */
        private final Paint plate = new Paint();
        private int life = 5000;

        FloatingText(String text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }

        void tick(long elapsed) {
            if (life > 0) {
                life -= (int) elapsed;
            }
        }

        boolean isDone() {
            return life <= 0;
        }

        void draw(Canvas canvas, int buttonColor) {
            if (isDone()) {
                return;
            }
            int height = font.height() + 4;
            int width = font.stringWidth(text) + 8;
            plate.setColor(buttonColor);
            canvas.drawRect(x - width / 2, y - 2, x - width / 2 + width, y - 2 + height, plate);
            font.drawText(canvas, text, x, y, Anchor.TOP | Anchor.HCENTER, android.graphics.Color.WHITE);
        }
    }

    /** A 24×24 square with its own upgrade type. A tower exactly at its centre (ol.a(ri)) gets the bonus; a tower
     * that only grazed it (ol.a(int, int, int, int)) cannot be placed here.
     * Original: ol. */
    private static final class Enhanced {

        private final int left;
        private final int top;
        private final int right;
        private final int bottom;
        private final Spot type;
        /** The spot is taken by a tower, so its icon is no longer drawn. Original: ol.f. */
        private boolean taken;

        Enhanced(int left, int top, int right, int bottom, Spot type) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.type = type;
        }

        /** A tower exactly at the spot's centre — (b + 12, c + 12). Original: ol.a(ri). */
        boolean hits(Turret tower) {
            Vec2 position = tower.position();
            return (int) (position.x + 0.5f) == left + 12 && (int) (position.y + 0.5f) == top + 12;
        }

        /** The 20×20 square under the finger crossed the spot. Original: ol.a(int, int, int, int). */
        boolean blocks(Turret tower) {
            return blocks(new int[]{left, top, right, bottom}, tower);
        }

        /** The 20×20 square under the finger crossed the rectangle. Original: cb.a(int, int, int, int). */
        static boolean blocks(int[] rect, Turret tower) {
            return overlaps(rect[0], rect[1], rect[2], rect[3], tower.position());
        }

        private static boolean overlaps(int left, int top, int right, int bottom, Vec2 position) {
            int x = (int) (position.x - 10.0f + 0.5f);
            int y = (int) (position.y - 10.0f + 0.5f);
            return y + 20 > top && y < bottom && x + 20 > left && x < right;
        }
    }

    /** A coin that drifts away and pays out when hit. Original: mz. */
    private final class Coin {

        private Vec2 position;
        private Vec2 velocity = new Vec2(0, 0);
        private int amount;
        private int life = 3500;
        /** Own paint: drawBitmap multiplies pixels by the paint colour, so it is white and unfiltered, like Screen.bitmapPaint. */
        private final Paint sprite = new Paint();

        Coin(Vec2 position, int amount) {
            sprite.setFilterBitmap(false);
            sprite.setColor(android.graphics.Color.WHITE);
            restart(position, amount);
        }

        void restart(Vec2 position, int amount) {
            this.amount = amount;
            this.position = position.copy();
            velocity = new Vec2(Randoms.between(-100, 100), Randoms.between(-100, 100));
            velocity.normalize();
            velocity.scale(0.02f);
            life = 3500;
        }

        void tick(long elapsed) {
            if (life <= 0) {
                return;
            }
            position.x += velocity.x * elapsed;
            position.y += velocity.y * elapsed;
            life -= (int) elapsed;
            if (life <= 0) {
                life = 0;
            }
        }

        boolean isDone() {
            return life <= 0;
        }

        /** The coin is only the image cj.a().x(), anchored at 3 (HCENTER|VCENTER) on its point. Original: mz.a(Graphics). */
        void draw(Canvas canvas) {
            if (isDone()) {
                return;
            }
            canvas.drawBitmap(Assets.get().coin(), (int) (position.x + 0.5f) - Assets.get().coin().getWidth() / 2,
                    (int) (position.y + 0.5f) - Assets.get().coin().getHeight() / 2, sprite);
        }

        boolean tapped(float x, float y) {
            if (isDone()) {
                return false;
            }
            if (new Vec2(position.x - x, position.y - y).length() <= 15.0f) {
                hud.earn(amount);
                life = 0;
                // The coin was caught: ru.a().n() and be.q().
                Sounds.get().coinBonus();
                hud.game().vibrate();
                return true;
            }
            return false;
        }
    }
}
