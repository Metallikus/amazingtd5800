package com.amazingtd5800;

import android.graphics.Canvas;

/**
 * The invisible 50x50 square in a corner of the level-select screen. The fourth tap in order
 * (cg, cm, ck, cn) toggles "all levels unlocked".
 * Original: ch, cf, cl, cj.
 */
final class CornerArrow extends Widget {

    private final Runnable onPressed;

    CornerArrow(Screen screen, int x, int y, Runnable onPressed) {
        super(screen, x, y, 50, 50);
        this.onPressed = onPressed;
    }

    @Override
    public void paint(Canvas canvas) {
    }

    /** The gesture step advances on tap; the original's backlight flash (be.a(100)) arrives here as a 100 ms
     * vibration pulse, which the screen performs.
     * Original: cg, cm, ck, cn. */
    @Override
    protected void onPress(float x, float y) {
        onPressed.run();
    }
}
