package com.amazingtd5800;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Glyphs used to come from a sheet (PF_Tempesta_Seven_13px.png, MS_Sans_Serif_16px.png); they are TTFs in
 * assets/fonts now. textSize is chosen so ascent + descent give the old sheet height (13 for dt, 16 for du):
 * the original's anchors and panel layout depend on it, so it stays the same.
 * Original: dt, du.
 */
public final class TrueTypeFont implements GameFont {

    private final int ascent;
    private final int descent;
    private final int height;
    private final int lineHeight;
    private final Paint paint = new Paint();

    /** @param leading the original's line spacing: 1 for dt (13 + 1), 4 for du (16 + 4). */
    TrueTypeFont(Context context, String assetPath, int height, int leading) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), assetPath);
        paint.setTypeface(typeface);
        paint.setAntiAlias(false);
        paint.setColor(Color.WHITE);
        paint.setTextSize(fitToHeight(typeface, height));
        Paint.FontMetrics metrics = paint.getFontMetrics();
        ascent = Math.round(-metrics.ascent);
        descent = Math.round(metrics.descent);
        this.height = ascent + descent;
        this.lineHeight = this.height + leading;
    }

    /** The fitted glyph height. Original: dt.a(), du.a(). */
    @Override
    public int height() {
        return height;
    }

    /** Height plus the original's line spacing. Original: dt.b(), du.b(). */
    @Override
    public int lineHeight() {
        return lineHeight;
    }

    @Override
    public void drawText(Canvas canvas, String text, int x, int y, int anchor, int color) {
        paint.setColor(color);
        if ((anchor & Anchor.TOP) != 0) {
            y += ascent;
        } else if ((anchor & Anchor.BOTTOM) != 0) {
            y -= descent;
        }
        if ((anchor & Anchor.HCENTER) != 0) {
            x -= stringWidth(text) / 2;
        } else if ((anchor & Anchor.RIGHT) != 0) {
            x -= stringWidth(text);
        }
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public int stringWidth(String text) {
        return (int) paint.measureText(text);
    }

    /** Size at which ascent + descent equal the height of the old glyph sheet. */
    private static float fitToHeight(Typeface typeface, int height) {
        Paint probe = new Paint();
        probe.setTypeface(typeface);
        probe.setTextSize(1000f);
        Paint.FontMetrics metrics = probe.getFontMetrics();
        float size = height * 1000f / (metrics.descent - metrics.ascent);
        for (int attempt = 0; attempt < 4; attempt++) {
            probe.setTextSize(size);
            metrics = probe.getFontMetrics();
            int box = Math.round(metrics.descent) - Math.round(metrics.ascent);
            if (box == height) {
                return size;
            }
            size *= height / (float) box;
        }
        return size;
    }
}
