package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Level select: 19 nodes, the lines between them and the four-corner gesture that unlocks every level at once.
 * A line is 4 pixels wide and runs from node centre to node centre.
 * Original: an.
 */
public final class LevelSelectScreen extends Screen {

    /** Number of levels. Original: ca.e. */
    private static final int LEVEL_COUNT = 19;
    /** Nodes visible only in "all levels unlocked" mode (bs → bd). */
    private static final int[] STAR_NODES = {7, 9, 12, 18};
    /** Lines that join a node only once it is unlocked (e[5], e[9], e[12], e[18]). */
    private static final int[] LOCKED_PATHS = {5, 9, 12, 18};
    private static final int[][] NODES = {
            {40, 70, 1}, {150, 90, 2}, {120, 180, 3}, {260, 190, 4}, {290, 260, 5}, {80, 300, 6},
            {190, 400, 7}, {230, 310, 8}, {23, 362, 9}, {32, 217, 10}, {252, 77, 11}, {76, 389, 12},
            {35, 404, 13}, {84, 462, 14}, {280, 346, 15}, {300, 390, 16}, {255, 442, 17}, {128, 430, 18},
            {30, 480, 19},
    };
    /** Lines between nodes; their indexes match the nodes they join. Original: cb. */
    private static final int[][] PATHS = {
            {0, 1}, {0, 2}, {1, 3}, {2, 3}, {3, 4}, {4, 7}, {3, 5}, {5, 6}, {5, 8}, {2, 9},
            {1, 10}, {8, 11}, {8, 12}, {11, 13}, {6, 14}, {6, 15}, {6, 16}, {11, 17}, {13, 18},
    };

    private final Game game;
    private final Settings settings;
    private final LevelNode[] nodes = new LevelNode[LEVEL_COUNT];
    private final LevelPath[] paths = new LevelPath[LEVEL_COUNT];
    private final Paint linePaint = new Paint();
    /** Corner-tap counter: 0→1→2→3, and the fourth tap unlocks everything. Original: cn. */
    private int gestureStep;
    private boolean unlockAll;
    /** Levels cleared without deaths: one unlocks Tracking Laser, three unlock Missile Tower. Original: an.k. */
    private int perfect;

    public LevelSelectScreen(Game game) {
        super(game, game.context());
        this.game = game;
        this.settings = game.settings();
        Assets assets = Assets.get();
        GameFont labels = assets.labelFont();
        linePaint.setAntiAlias(false);
        linePaint.setStrokeWidth(4);
        linePaint.setStrokeCap(Paint.Cap.BUTT);
        add(new CornerArrow(this, 0, 0, () -> gesture(1)));
        add(new CornerArrow(this, width() - 50, 0, () -> gesture(2)));
        add(new CornerArrow(this, 0, height() - 50, () -> gesture(3)));
        add(new CornerArrow(this, width() - 50, height() - 50, this::unlockAllTapped));
        add(new TextButton(this, (width() - assets.redButtonShort().getWidth()) / 2, height() - 40,
                assets.redButtonShort(), 2, "Menu", labels, (x, y) -> game.showMainMenu()));
        for (int i = 0; i < LEVEL_COUNT; i++) {
            int[] node = NODES[i];
            boolean star = isStar(i);
            nodes[i] = new LevelNode(this,
                    star ? assets.star4b() : assets.levelSelected(),
                    star ? assets.star1b() : assets.levelPerfect(),
                    star ? assets.star3b() : assets.levelUnlocked(),
                    star ? assets.star2b() : assets.levelDisabled(),
                    node[0], node[1], node[2], star ? 18 : 16, star ? 4 : 2, star ? 17 : 15, star ? 3 : 1,
                    (x, y) -> game.showLevel(node[2]));
            add(nodes[i]);
        }
        for (int i = 0; i < PATHS.length; i++) {
            paths[i] = new LevelPath(nodes[PATHS[i][0]], nodes[PATHS[i][1]], isLocked(i));
        }
        refresh();
    }

    /** Reset, unlock state and re-read progress. Original: an.b(). */
    public void refresh() {
        resetNodes();
        applyState(!unlockAll);
        reload();
    }

    /** Locks every node and line again. Original: an.e(). */
    private void resetNodes() {
        for (LevelPath path : paths) {
            path.reset();
        }
        for (LevelNode node : nodes) {
            node.reset();
        }
    }

    /** Shows or hides the star nodes and their lines depending on "all levels unlocked". Original: an.a(boolean). */
    private void applyState(boolean all) {
        for (int index : STAR_NODES) {
            nodes[index].setVisible(!all);
        }
        for (int index : LOCKED_PATHS) {
            paths[index].setVisible(!all);
        }
        nodes[0].setEnabled(true);
        for (int i = 1; i < nodes.length; i++) {
            nodes[i].setEnabled(!all);
        }
    }

    /** Re-reads cleared levels from settings; the tower-unlock counter starts at zero. Original: an.d(). */
    private void reload() {
        perfect = 0;
        for (LevelRecord record : settings.levels()) {
            if (record.score > 0) {
                unlock(record.number, record.cleared);
            }
        }
    }

    /**
     * The towers unlocked by levels cleared without deaths — Tracking Laser after one, Missile Tower after
     * three. The game screen may not exist yet (it is created later), so there may be nothing to unlock yet.
     * Original: an.c().
     */
    void showTowers() {
        GameScreen screen = game.screen();
        if (screen != null) {
            screen.showTrackingLaser(perfect > 0 || unlockAll);
            screen.showMissile(perfect >= 3 || unlockAll);
        }
    }

    /** Unlocks a node from a level cleared without deaths and unlocks the towers it awards. Original: an.a(int, boolean). */
    private void unlock(int number, boolean cleared) {
        if (number - 1 >= nodes.length) {
            return;
        }
        LevelNode node = nodes[number - 1];
        if (cleared && !node.unlocked()) {
            perfect++;
        }
        node.setUnlocked(cleared);
        for (LevelPath path : paths) {
            if (path.start() == node) {
                path.connect(cleared || unlockAll);
            }
        }
        showTowers();
    }

    /**
     * The right corner advances the gesture one step and flashes the backlight for 100 ms (be.a(100)); a wrong
     * corner resets the gesture to 0.
     * Original: cg, cm, ck.
     */
    private void gesture(int next) {
        if (gestureStep != next - 1) {
            gestureStep = 0;
            return;
        }
        game.vibrate(100);
        gestureStep = next;
    }

    /** The fourth gesture tap toggles "all levels unlocked". Original: cn. */
    private void unlockAllTapped() {
        if (gestureStep == 3) {
            game.vibrate(100);
            unlockAll = !unlockAll;
            applyState(!unlockAll);
            if (!unlockAll) {
                reload();
            }
            showTowers();
            invalidate();
        }
        gestureStep = 0;
    }

    private boolean isStar(int index) {
        for (int star : STAR_NODES) {
            if (star == index) {
                return true;
            }
        }
        return false;
    }

    private boolean isLocked(int index) {
        for (int locked : LOCKED_PATHS) {
            if (locked == index) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void paint(Canvas canvas) {
        Assets assets = Assets.get();
        drawBitmap(canvas, assets.menuBackground(), 0, 0, Anchor.TOP | Anchor.LEFT);
        GameFont title = assets.titleFont();
        GameFont text = assets.textFont();
        title.drawText(canvas, "Select level", width() / 2, 20, Anchor.TOP | Anchor.HCENTER, Color.WHITE);
        text.drawText(canvas, "Difficulty", 10, 10, Anchor.TOP | Anchor.LEFT, Color.WHITE);
        text.drawText(canvas, settings.difficultyName(), 10, 10 + text.height() + 2,
                Anchor.TOP | Anchor.LEFT, Color.WHITE);
        for (LevelPath path : paths) {
            if (!path.isVisible()) {
                continue;
            }
            LevelNode start = path.start();
            LevelNode end = path.end();
            linePaint.setColor(path.isConnected() ? Color.rgb(0x22, 0xFF, 0x22) : settings.pathColor());
            canvas.drawLine(end.x() + end.width() / 2, end.y() + end.height() / 2,
                    start.x() + start.width() / 2, start.y() + start.height() / 2, linePaint);
        }
        paintWidgets(canvas);
    }
}
