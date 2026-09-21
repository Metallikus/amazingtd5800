package com.amazingtd5800;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * The level screen: background and roads, HUD, the tower panel (ee and df), the buy buttons (db),
 * "Send now" (ef and eg) and the pause menu (n).
 * Original: amazingtd.ca and cs.
 */
public final class GameScreen extends Screen {
    /** Game area size. Original: cs.a, cs.b. */
    static final int WIDTH = 360;
    static final int HEIGHT = 640;
    /** Money and score multiplier per difficulty (1.0, 1.0, 1.2) — on easy, money is not cut. Original: amazingtd.j.a. */
    static float difficultyScale;

    private static final float[] SCALE = {1.0f, 1.0f, 1.2f};
    /**
     * Enemy health (w.a) and speed (w.b) multipliers: 0.8 and 0.9 on easy, so a level-4 Autobow one-shots the
     * first wave, while on hard both multipliers are 1.2.
     * Original: ca.a(int), which sets both. */
    private static final float[] HEALTH = {0.8f, 1.0f, 1.2f};
    private static final float[] SPEED = {0.9f, 1.0f, 1.2f};
    /** The shop panel (cs.h) is painted 0x888888, not the difficulty colour. Original: cs.c(), cs (constructor). */
    private static final int SHOP_PANEL = Color.rgb(0x88, 0x88, 0x88);
    /** Towers in the button row: the same names and the same order as in di. Original: db. */
    private static final String[] NAMES = {"Autobow", "Slow Tower", "Mortar", "Chaingun", "Force Field Tower",
            "Pulsed Laser", "Detector", "Money Tower", "Sniper Tower", "Tracking Laser", "Missile Tower"};

    /** Game counters; the screen needs them to reach be.q() (vibration) and be.j (settings). Original: amazingtd.j. */
    private final Hud hud = new Hud(game());
    private final Paint paint = new Paint();
    private final Level level;
    /** The panel shows the selected tower. Original: ca.j. */
    private Turret panelTower;
    /** ef is the "Send now" button (hy), centred on the top strip; visible only while a wave waits its turn. Original: ca.p. */
    private final PanelButton sendNow;
    /** The tower carried by finger from the panel to the field — not built yet. Original: cs.g. */
    private Turret carried;
    /** The finger carrying a tower entered the field (y <= HEIGHT - 58). Original: cs.k, cs.b(int). */
    private boolean carriedOnField;
    /** The spot is upgraded: a 24x24 frame and four arrows around the carried tower. Original: gr.h, gr.a(int,int,24,24). */
    private boolean spot;
    private boolean paused;
    /** The fail or victory panel: shown once, and after a fail the level stops ticking. Original: ca.o. */
    private TextPanel result;
    /** Tracking Laser and Missile Tower stay hidden until unlocked by levels cleared without deaths. Original: cs.j[10], cs.j[11]. */
    private boolean tracking;
    private boolean missile;

    /** Choosing a level tells the game screen Tracking Laser is already unlocked. Original: cs.a(boolean). */
    void showTrackingLaser(boolean visible) {
        tracking = visible;
    }

    /** Choosing a level tells the game screen Missile Tower is already unlocked. Original: cs.b(boolean). */
    void showMissile(boolean visible) {
        missile = visible;
    }

    GameScreen(Game game, Context context, int number) {
        super(game, context);
        int difficulty = game.settings().difficulty();
        difficultyScale = SCALE[difficulty];
        Enemy.healthScale = HEALTH[difficulty];
        Enemy.speedScale = SPEED[difficulty];
        level = Level.of(hud, number);
        add(new Field());
        add(new TowerShop());
                // ef is the "Send now" button (hy), centred on the top strip; it is visible only while a wave waits its turn
        // (ca.r), and it hides with the wave timer and on a tap (eg.a -> ca.f(ca).b(false)).
        sendNow = new PanelButton(this, game.settings(), (WIDTH - 100) / 2, 30, 100, 26, "Send now",
                Assets.get().textFont(), (px, py) -> level.sendNow());
        add(sendNow);
    }

    /** Level background, roads, enemies, towers, projectiles, HUD and buttons. Original: ca.a(Graphics). */
    @Override
    protected void paint(Canvas canvas) {
        Settings settings = game().settings();
        drawBitmap(canvas, Assets.get().levelBackground(level.background()), 0, 0, Anchor.TOP | Anchor.LEFT);
        level.drawBackground(canvas, settings);
        level.draw(canvas, settings);
        if (carried != null) {
            carried.draw(canvas);
                        // After the level, the carried tower's 24x24 frame is drawn (g.b(Graphics)).
            carried.drawSelected(canvas);
            if (spot) {
                arrows(canvas);
            }
        }
        hud.draw(canvas, WIDTH, HEIGHT, paused);
                // setColor(0); fillRect(0, b - 58, a, b): the level (ca, m.b) is drawn with a window of 0..b-58, so under
        // the button row (y = b-58) the screen's black strip shows through, not the level background.
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, HEIGHT - 58, WIDTH, HEIGHT, paint);
                // ca.a(Graphics) draws the button only while ca.b() is true, and hy.e() does not take the touch.
        sendNow.setVisible(level.sendNowVisible());
        paintWidgets(canvas);
    }

    /**
     * While the level is not failed it ticks; once lives run out (j.c()) the fail panel shows once and the level
     * stops ticking entirely; after a win the score is saved and it keeps ticking.
     * Original: ca.b(long). */
    @Override
    protected void tick(long elapsed) {
        if (paused) {
            return;
        }
        if (level.failed()) {
            if (result == null) {
                failed();
            }
            return;
        }
        level.tick(elapsed);
        if (level.cleared() && result == null) {
            cleared();
        }
    }

    /**
     * A tap on an arrow moves the carried tower; a tap on the tower itself ends the build (cs.b(dq): the 24x24 frame
     * fully inside the field and the spot free); a tap off the arrows cancels (cs.g()).
     * Original: aj.a/b/c/d and e. */
    private void tapArrows(float x, float y) {
        Vec2 at = carried.position();
        if (hit(at.x - 12, at.y - 12, 24, 24, x, y)) {
            if (inside(at) && level.placeable(carried)) {
                build();
            } else {
                cancel();
            }
            return;
        }
        if (hit(at.x - 44, at.y - 16, 32, 32, x, y)) {
            nudge(-8, 0);
        } else if (hit(at.x + 12, at.y - 16, 32, 32, x, y)) {
            nudge(8, 0);
        } else if (hit(at.x - 16, at.y - 44, 32, 32, x, y)) {
            nudge(0, -8);
        } else if (hit(at.x - 16, at.y + 12, 32, 32, x, y)) {
            nudge(0, 8);
        } else {
            cancel();
        }
    }

    /** The tower's 24x24 frame is fully inside the field. Original: cs.b(dq). */
    private boolean inside(Vec2 at) {
        return at.y - 12 >= 0 && at.y + 12 <= HEIGHT - 58 && at.x - 12 >= 0 && at.x + 12 <= WIDTH;
    }

    /** The carried tower is put away, its range goes dark, the arrows vanish and no money is charged. Original: cs.g(). */
    private void cancel() {
        if (carried != null) {
            carried.setRangeVisible(false);
            carried = null;
        }
        spot = false;
    }

    /**
     * An arrow moves the tower by 8 pixels while the new point is inside the field, and paints the frame at the new
     * point.
     * Original: aj.a/b/c/d, cs.a(cs, n2, n3), g.a(f.b().a(g)). */
    private void nudge(float dx, float dy) {
        Vec2 at = carried.position();
        if (at.y + dy <= HEIGHT - 58) {
            carried.setPosition(at.x + dx, at.y + dy);
            at = carried.position();
            carried.setPlaceable(inside(at) && level.placeable(carried));
        }
    }

    /** Arrows around the carried tower: the Arrows.png frame under each side. Original: gr.a(int, int, 24, 24). */
    private void arrows(Canvas canvas) {
        Vec2 at = carried.position();
        canvas.drawBitmap(Assets.get().arrow(64), at.x - 12 - 32, at.y - 16, bitmapPaint);
        canvas.drawBitmap(Assets.get().arrow(0), at.x + 12, at.y - 16, bitmapPaint);
        canvas.drawBitmap(Assets.get().arrow(96), at.x - 16, at.y - 12 - 32, bitmapPaint);
        canvas.drawBitmap(Assets.get().arrow(32), at.x - 16, at.y + 12, bitmapPaint);
    }

    private static boolean hit(float x, float y, float width, float height, float px, float py) {
        return px >= x && py >= y && px <= x + width && py <= y + height;
    }

    /** A finger on a button creates the tower 14 pixels above the finger and carries it. Original: cs.e(), cs.b(cs, dq). */
    private void carry(int index, float x, float y) {
        Turret tower = tower(index, new Vec2(x, y - 14));
        tower.setRangeVisible(true);
        level.place(tower, true);
        carried = tower;
        carriedOnField = false;
        spot = false;
    }

    /**
     * The carried tower follows the finger and sticks to the grid, walking around the road, until the arrows are shown;
     * there the frame is painted by whether a tower may be placed at that point.
     * Original: cs.pointerDragged, g.a(cs.b(g)). */
    private void carryTo(float x, float y) {
        if (carried == null || spot) {
            return;
        }
        carriedOnField |= y <= HEIGHT - 58;
        carried.setPosition(x, y - 14);
        level.place(carried, true);
        carried.setPlaceable(inside(carried.position()) && level.placeable(carried));
    }

    /**
     * The finger left. The tower stays only if the finger was on the field and was released on the field too; with the
     * "Tower adj" setting on the arrows show first, without it the tower is placed at once.
     * Original: cs.pointerReleased. */
    private void drop(float x, float y) {
        if (carried == null) {
            return;
        }
        if (!carriedOnField || y > HEIGHT - 58) {
            cancel();
            return;
        }
        level.place(carried, false);
        if (game().settings().towerAdjustEnabled()) {
            spot = true;
        } else if (inside(carried.position()) && level.placeable(carried)) {
            build();
        }
    }

    /** The tower is built: price charged, range dark (cs.f -> g.b(false)), arrows gone. Original: cs.f(), aj.e(). */
    private void build() {
        Turret tower = carried;
        carried = null;
        spot = false;
        tower.setRangeVisible(false);
        if (!level.canAfford(tower.price())) {
            return;
        }
        level.spend(tower.price());
        level.addTower(tower);
    }

    /**
     * A tower of this type on the grid cell under the point. The tower carries its own index — it is also the panels'
     * name and stats (dq.b() and dq.k()/j()/l()) — so it is set here rather than at the call site.
     * Original: ca.a(dq, boolean). */
    private Turret tower(int index, Vec2 position) {
        Bitmap sheet = Assets.get().towerSheet(index);
        Turret tower;
        switch (index) {
            case 0: tower = new Towers.Autobow(sheet, position); break;
            case 1: tower = new Towers.SlowTower(sheet, position); break;
            case 2: tower = new Towers.Mortar(sheet, position); break;
            case 3: tower = new Towers.Chaingun(sheet, position); break;
            case 4: tower = new Towers.ForceField(sheet, position); break;
            case 5: tower = new Towers.PulsedLaser(sheet, position); break;
            case 6: tower = new Towers.Detector(sheet, position); break;
            case 7: tower = new Towers.MoneyTower(sheet, position); break;
            case 8: tower = new Towers.Sniper(sheet, position); break;
            case 9: tower = new Towers.TrackingLaser(sheet, position); break;
            default: tower = new Towers.MissileTower(sheet, position); break;
        }
        tower.setIndex(index);
        return tower;
    }

    /** The level failed: the panel is centred in the area (ca.b(long): ca.c, 400) and the level stops ticking. Original: ay. */
    private void failed() {
        result = new ResultPanel("Level " + level.number() + " Failed!\n"
                + "Enemies killed: " + hud.kills() + "\nScore: " + hud.score(), "Retry", "Exit", true);
        showDialog(result);
    }

    /** The level is cleared: the same panel, centred, with one line more, and the score is saved. Original: ca.b(long). */
    private void cleared() {
        game().settings().setLevel(level.number(), hud.score(), hud.livesLost(), hud.livesLost() == 0);
        result = new ResultPanel("Level " + level.number()
                + (hud.livesLost() == 0 ? " - Perfect!" : " - Cleared!")
                + "\nEnemies killed: " + hud.kills() + "\nLives lost: " + hud.livesLost() + "\nScore: " + hud.score(),
                "Continue", "Exit", false);
        showDialog(result);
    }

    /**
     * The end-of-level panel: level number, kills, lives and score (0xFFFF00 lines that are not buttons) and two
     * button lines on a plate, centred (aw/dg and de/df, anchor 17).
     * Original: ay, dh.
     */
    private final class ResultPanel extends TextPanel {

        ResultPanel(String text, String first, String second, boolean failed) {
            super(GameScreen.this, game().settings(), game().settings().panelColor(), 0, 0, 0, 400, true, false);
            GameFont font = Assets.get().labelFont();
            addLines(font, text, RESULT, RESULT, false);
            addRow(font, first, true, true, () -> {
                closeDialog();
                if (!failed) {
                    game().showLevel(level.number());
                } else {
                    game().showLevelSelect();
                }
            });
            addRow(font, second, true, true, () -> game().showLevelSelect());
        }
    }

    /**
     * The selected tower's panel: its name (line 0, gh(name, true, false), so uncoloured), its stats (lines 1..3,
     * gh("Power: ?"), so colourful: the original put them as a second line in two columns in font cj.y(), here each
     * is its own line in the screen font cs.d like all the others, so they do not run into "Upgrade"), upgrade and
     * sell; it sits at x+2, y+2 from the tower, snapping to the area's right edge (ca.c/ca.d), and wider than 100
     * when the stats do not fit in 100.
     * Original: ca.j, ee.
     */
    private final class TowerPanel extends TextPanel {

        TowerPanel(Turret tower, int x, int y) {
            super(GameScreen.this, game().settings(), game().settings().panelColor(), 100, x + 2, y + 2,
                    HEIGHT - 58, false, false);
            GameFont font = Assets.get().labelFont();
            GameFont bigfont = Assets.get().titleFont();
            addLines(font, NAMES[tower.index()] + " (" + tower.level() + ")", PLAIN, PLAIN, true);
            addLines(font, "Power: " + tower.power() + "\nRate: " + tower.rate() + "\nRange: " + tower.range(),
                    COLORFUL, COLORFUL, false);
            addRow(bigfont, tower.canUpgrade() ? "Upgrade" + (tower.level() + 1) + " $" + tower.upgradePrice()
                            : "Max upgraded", false, tower.canUpgrade() && hud.money() >= tower.upgradePrice(), () -> {
                        tower.upgrade(hud);
                        closeDialog();
                    });
            addRow(bigfont, "Sell $" + tower.sellPrice(), false, true, () -> {
                level.sell(tower);
                closeDialog();
            });
        }
    }

    /**
     * The tower-type panel of a shop button: its name and four stat lines. The button that was pressed gives it its
     * coordinates (cs.i(cs).a(n2, n3)): horizontally it centres on that button's axis and snaps to the near edge
     * ca.c when it does not fit past the other, and pb.a(int, int) snaps its bottom to ca.d, which is why it lands
     * top-down under the button row.
     * Original: cs.h, al.
     */
    private final class ShopPanel extends TextPanel {

        /** Five lines on a 0x888888 plate; x is the button's axis, y the touch point on the button. Original: al(cs, be, 5, 200). */
        ShopPanel(Turret tower, int x, int y) {
            super(GameScreen.this, game().settings(), SHOP_PANEL, 0, x, y, HEIGHT - 58, false, true);
            addLines(Assets.get().labelFont(), NAMES[tower.index()] + "\nPrice: $" + tower.price()
                            + "\nPower: " + tower.power() + "\nRange: " + tower.range() + "\nRate: " + tower.rate(),
                    PLAIN, COLORFUL, true);
        }
    }

    /**
     * The pause menu: Pause/Resume, Sound, Vibration, Tower adj, Restart and Exit, in the area's bottom-right
     * corner (x = ca.c - width - 2, y = ca.d - height).
     * Original: ca.n, au, av, bb, bc, az, ba, ax.
     */
    private final class PausePanel extends TextPanel {

        PausePanel() {
            super(GameScreen.this, game().settings(), game().settings().panelColor(), 0, WIDTH - 2, HEIGHT - 58,
                    HEIGHT - 58, false, false);
            Settings settings = game().settings();
            GameFont font = Assets.get().titleFont();
            addRow(font, paused ? "Resume" : "Pause", false, true, () -> paused = !paused);
            addRow(font, "Sound: " + (settings.soundEnabled() ? "On" : "Off"), false, true, () -> {
                settings.setSoundEnabled(!settings.soundEnabled());
                Sounds.get().setEnabled(settings.soundEnabled());
            });
            addRow(font, "Vibration: " + (settings.vibrationEnabled() ? "On" : "Off"), false, true,
                    () -> settings.setVibrationEnabled(!settings.vibrationEnabled()));
            addRow(font, "Tower adj: " + (settings.towerAdjustEnabled() ? "On" : "Off"), false, true,
                    () -> settings.setTowerAdjustEnabled(!settings.towerAdjustEnabled()));
            addRow(font, "Restart", false, true, () -> {
                closeDialog();
                game().showLevel(level.number());
            });
            addRow(font, "Exit", false, true, () -> game().showLevelSelect());
        }
    }

    /** Field touches: coins, selecting a tower and the move arrows. Original: ca.a_(int, int), cs.pointerPressed. */
    private final class Field extends Widget {

        Field() {
            super(GameScreen.this, 0, 0, WIDTH, HEIGHT - 58);
        }

        @Override
        public void paint(Canvas canvas) {
        }

        /** A coin takes the touch; the first tap on a tower only selects it, the second opens the panel. Original: ca.a_(int, int). */
        @Override
        protected void onPress(float x, float y) {
            if (level.tapped(x, y) || dialogPressed()) {
                return;
            }
            if (carried != null) {
                if (spot) {
                    tapArrows(x, y);
                }
                return;
            }
            Turret tower = level.towerAt(x, y);
            if (tower != null && tower == level.selected()) {
                if (!tower.reloading()) {
                    panelTower = tower;
                    showDialog(new TowerPanel(tower, (int) x, (int) y));
                }
                return;
            }
            level.select(tower);
        }

        @Override
        protected void onDrag(float x, float y) {
            carryTo(x, y);
        }

        @Override
        protected void onRelease(float x, float y) {
            drop(x, y);
        }
    }

    /**
     * The tower shop is not a strip but separate button plates: j[1..6] in the bottom row (y = b-28-2) and
     * j[7..11] in the top one (y = b-58), each plate 48x28 on a 50 step, the Menu button being
     * ad(cs, a-58, n2, 56, 28, "Menu") in colour cj.d and the rest cj.e. A tower that cannot be afforded (ad.a_(money))
     * has a cj.f plate crossed out in red (hy.b), and ad.a(boolean) keeps it from taking the finger. The tower is
     * created under the finger and follows it onto the field.
     * Original: cs.e(), hy.
     */
    private final class TowerShop extends Widget {

        /** A tower button's plate is 48x28; the Menu button is ad(..., 56, 28, "Menu"). Original: hy.f/hy.g. */
        private static final int TILE = 48;
        private static final int MENU = 56;
        /** Button x = 2, 52, 102, 152, 202, 252: a 50 step with 48-wide plates. Original: cs.e(). */
        private static final int CELL = 50;
        private static final int ROW = 28;
        /** A button's tower price; ad.a_(money) uses it to decide whether the button is crossed out in red. Original: ad.a(). */
        private final int[] price = new int[NAMES.length];
        /** The plate under the finger is the pressed one (hy.h swaps highlight and shadow). Original: nv.a_, nv.c. */
        private float pressX = -1;
        private float pressY = -1;
        private final Paint paint = new Paint();

        TowerShop() {
            super(GameScreen.this, 2, HEIGHT - 2 * ROW - 2, WIDTH - 4, 2 * ROW);
            paint.setAntiAlias(false);
            for (int i = 0; i < price.length; i++) {
                price[i] = tower(i, new Vec2(0, 0)).price();
            }
        }

        @Override
        public void paint(Canvas canvas) {
            Settings settings = game().settings();
            for (int i = 0; i < NAMES.length; i++) {
                                // A hidden button is not drawn at all (if (!this.b) return). Original: hy.a(Graphics).
                if (!unlocked(i)) {
                    continue;
                }
                boolean paid = paid(i);
                plate(canvas, paid ? settings.buttonColor() : settings.disabledColor(), left(i), top(i), TILE,
                        pressX >= left(i) && pressX >= top(i) && pressX <= left(i) + TILE && pressY <= top(i) + ROW);
                                // super.a(graphics): the plate first, then the tower icon at its centre (anchor 3). Original: y.a(Graphics).
                drawBitmap(canvas, Assets.get().tower(i), left(i) + TILE / 2, top(i) + ROW / 2,
                        Anchor.HCENTER | Anchor.VCENTER);
                                // if (!this.a) — the button is crossed out with a red cross. Original: hy.b.
                if (!paid) {
                    cross(canvas, left(i), top(i), TILE, ROW);
                }
            }
                        // j[0] = ad(cs.a - 58, b - 28 - 2, 56, 28, "Menu"): its own cj.d plate, captioned by hy.a in font cj.A().
            GameFont font = Assets.get().labelFont();
            boolean menu = pressX >= menuX() && pressX <= menuX() + MENU && pressY >= y + ROW && pressY <= y + 2 * ROW;
            plate(canvas, settings.panelColor(), menuX(), y + ROW, MENU, menu);
            int off = menu ? 1 : 0;
            font.drawText(canvas, "Menu", menuX() + MENU / 2 + off, y + ROW + (ROW - font.height()) / 2 + off,
                    Anchor.TOP | Anchor.HCENTER, menu ? settings.pressedLabelColor() : Color.WHITE);
        }

        /**
         * f.a(..., f.a(l, 32), f.a(l, -32), true): the plate fill and a one-pixel border — highlight on top and left,
         * shadow on bottom and right (inverted when pressed). J2ME segments include both ends, so each side is drawn as
         * its own one-pixel-wide rectangle and reaches into the corner.
         * Original: hy.a(Graphics).
         */
        private void plate(Canvas canvas, int fill, int left, int top, int width, boolean pressed) {
            int light = PanelButton.shade(fill, 32);
            int dark = PanelButton.shade(fill, -32);
            paint.setColor(fill);
            canvas.drawRect(left, top, left + width, top + ROW, paint);
            paint.setColor(pressed ? dark : light);
            canvas.drawRect(left, top, left + 1, top + ROW + 1, paint);
            canvas.drawRect(left, top, left + width + 1, top + 1, paint);
            paint.setColor(pressed ? light : dark);
            canvas.drawRect(left + width, top, left + width + 1, top + ROW + 1, paint);
            canvas.drawRect(left, top + ROW, left + width + 1, top + ROW + 1, paint);
        }

        /** Two 0xFF0000 segments crossing the whole plate of a button that cannot be afforded. Original: hy.b. */
        private void cross(Canvas canvas, int left, int top, int width, int height) {
            paint.setColor(Color.rgb(0xFF, 0x00, 0x00));
            paint.setStrokeWidth(1);
            canvas.drawLine(left, top, left + width, top + height, paint);
            canvas.drawLine(left, top + height, left + width, top, paint);
            paint.setStrokeWidth(0);
        }

        /**
         * A button works only while the game is not paused. A carried tower with arrows is finished or cancelled
         * first, and only then does a button take a new one — and only if the one being carried is not already it.
         * Original: y.a(int, int).
         */
        @Override
        protected void onPress(float px, float py) {
            pressX = px;
            pressY = py;
            int index = indexAt(px, py);
            if (index < 0 || paused || !paid(index)) {
                                // The Menu button only shows the panel (n.a(!n.a())); the Pause line is what pauses the game. Original: ca.a().
                if (isMenu(px, py)) {
                    showDialog(new PausePanel());
                }
                return;
            }
            if (carried != null && spot) {
                if (inside(carried.position()) && level.placeable(carried)) {
                    build();
                } else {
                    cancel();
                }
            }
            if (carried == null) {
                carry(index, px, py);
            }
        }

        @Override
        protected void onDrag(float px, float py) {
            pressX = px;
            pressY = py;
            carryTo(px, py);
        }

        /** The finger left the plate, so it is no longer pressed. Original: nv.c. */
        @Override
        protected void onDragExit(float x, float y) {
            pressX = -1;
        }

        /**
         * The finger was released on a button: show that button's tower-type panel. Its axis is the axis of the button
         * that was pressed; the finger's point only gives y.
         * Original: z.a(int, int).
         */
        @Override
        protected void onRelease(float px, float py) {
            pressX = -1;
            int index = indexAt(px, py);
            if (index >= 0) {
                showDialog(new ShopPanel(tower(index, new Vec2(px, py)), axis(index), (int) py));
            }
            drop(px, py);
        }

        /** A tower button's axis is the button's x (2 + 50 * column) plus half its width. Original: cs.e(). */
        private int axis(int index) {
            return left(index) + TILE / 2;
        }

        /**
         * A button catches the touch only when the point is inside its plate (d..d+f on both sides). j[1..6] are towers
         * 0-5 in the bottom row, j[7..11] towers 6-10 in the top one; the Menu button is not an index.
         * Original: ad.e(int, int).
         */
        private int indexAt(float px, float py) {
            for (int i = 0; i < NAMES.length; i++) {
                if (unlocked(i) && px >= left(i) && px <= left(i) + TILE && py >= top(i) && py <= top(i) + ROW) {
                    return i;
                }
            }
            return -1;
        }

        /** Buttons that cannot be afforded are cj.f and crossed out, and ad.a(boolean) keeps them from taking the finger. Original: ad.a_(money). */
        private boolean paid(int index) {
            return hud.money() >= price[index];
        }

        /** Row buttons' x = 2, 52, 102, 152, 202, 252. Original: cs.e(). */
        private int left(int index) {
            return x + (index < 6 ? index : index - 6) * CELL;
        }

        /** n2 = b - 28 - 2 is the bottom button row, n2 - 28 = b - 58 the top one. Original: cs.e(). */
        private int top(int index) {
            return index < 6 ? y + ROW : y;
        }

        /** These two towers are hidden (j[10].b(false), j[11].b(false)) until they are unlocked. Original: cs.j[10], cs.j[11]. */
        private static final int TRACKING = 9;
        private static final int MISSILE = 10;

        /**
         * Tracking Laser unlocks after one level cleared without deaths, Missile Tower after three.
         * Original: cs.a(boolean), cs.b(boolean).
         */
        private boolean unlocked(int index) {
            return index < TRACKING || (index == TRACKING ? tracking : missile);
        }

        /** The Menu button is ad(cs.a - 58, b - 28 - 2, 56, 28, "Menu"), right of the bottom row's towers. Original: cs.e(). */
        private int menuX() {
            return WIDTH - 58;
        }

        private boolean isMenu(float px, float py) {
            return hit(menuX(), y + ROW, MENU, ROW, px, py);
        }
    }

}
