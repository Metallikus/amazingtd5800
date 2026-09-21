package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Draws text anchored with an {@link Anchor} in a colour chosen by the caller, the way the original called
 * setColor before every drawString.
 * Original: tm.
 */
public interface GameFont {

    /** Draws white text, like the screen's default font. Original: drawString(...) without setColor. */
    default void drawText(Canvas canvas, String text, int x, int y, int anchor) {
        drawText(canvas, text, x, y, anchor, Color.WHITE);
    }

    /** Draws text in the given colour. Original: setColor(color) + drawString(text, x, y, anchor). */
    void drawText(Canvas canvas, String text, int x, int y, int anchor, int color);

    int stringWidth(String text);

    int height();

    /**
     * Line height with leading: height + 1 for the counter font, height + 4 for the text fonts.
     * Original: dt (height + 1), du and dr (height + 4).
     */
    int lineHeight();
}
