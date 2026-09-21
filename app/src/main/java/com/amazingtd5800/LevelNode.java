package com.amazingtd5800;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * A level-select node: the level icon (plain or star), the number above it and the unlocked/locked state. Plain
 * nodes use Level_*_32 with captions offset 16/2 and 15/1, star nodes Star-*b with offsets 18/4 and 17/3.
 * Original: bs, bd.
 */
final class LevelNode extends Widget {

    private final Bitmap pressedIcon;
    private final Bitmap unlockedIcon;
    private final Bitmap enabledIcon;
    private final Bitmap disabledIcon;
    private final int shadowX;
    private final int shadowY;
    private final int labelX;
    private final int labelY;
    private final String label;
    /** The node number is drawn with graphics.drawString, i.e. the screen's default font (MEDIUM). Original: bs.a, bd.a. */
    private final GameFont font = Assets.get().titleFont();
    private boolean unlocked;

    LevelNode(Screen screen, Bitmap pressedIcon, Bitmap unlockedIcon, Bitmap enabledIcon, Bitmap disabledIcon,
              int x, int y, int number, int shadowX, int shadowY, int labelX, int labelY,
              Widget.OnClickListener listener) {
        super(screen, x, y, 30, 30);
        this.pressedIcon = pressedIcon;
        this.unlockedIcon = unlockedIcon;
        this.enabledIcon = enabledIcon;
        this.disabledIcon = disabledIcon;
        this.shadowX = shadowX;
        this.shadowY = shadowY;
        this.labelX = labelX;
        this.labelY = labelY;
        this.label = Integer.toString(number);
        setOnClickListener(listener);
    }

    /** The node is locked and visible again. Original: bs.a(). */
    void reset() {
        unlocked = false;
        setVisible(true);
    }

    /** The level was cleared without losses — the "perfect" icon. Original: bs.c(boolean). */
    void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    /** Whether the node is already unlocked — a newly cleared level adds no extra tower. Original: bs.c(). */
    boolean unlocked() {
        return unlocked;
    }

    @Override
    public void paint(Canvas canvas) {
        screen.drawBitmap(canvas, icon(), x, y, Anchor.TOP | Anchor.LEFT);
        font.drawText(canvas, label, x + shadowX, y + 4 + shadowY, Anchor.TOP | Anchor.HCENTER, Color.BLACK);
        font.drawText(canvas, label, x + labelX, y + 4 + labelY, Anchor.TOP | Anchor.HCENTER,
                isPressed() ? screen.game().settings().pressedLabelColor() : Color.WHITE);
    }

    private Bitmap icon() {
        if (isPressed()) {
            return pressedIcon;
        }
        if (unlocked) {
            return unlockedIcon;
        }
        return isEnabled() ? enabledIcon : disabledIcon;
    }

    @Override
    protected void onPress(float x, float y) {
        screen.invalidate();
    }

    @Override
    protected void onRelease(float x, float y) {
        screen.invalidate();
    }
}
