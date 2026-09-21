package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * A plate with a caption. The plate is the current difficulty colour ({@link Settings#buttonColor()}), a
 * disabled plate comes from {@link Settings#disabledColor()}, and the border is the same colour ±32 per
 * channel: light on top/left, dark on bottom/right, reversed while pressed. A panel row (gh) is the same
 * widget without a border: pressed, the caption takes the plate colour, so it appears to sink into it.
 * Original: hy, gh.
 */
public class PanelButton extends Widget {

    private final GameFont font;
    private final String label;
    private final int fillColor;
    private final int disabledColor;
    private final int pressedLabelColor;
    private final boolean boxed;
    private final boolean centered;
    private final Paint paint = new Paint();

    PanelButton(Screen screen, Settings settings, int x, int y, int width, int height, String label,
                GameFont font, OnClickListener listener) {
        this(screen, settings, x, y, width, height, label, font, true, true, listener);
    }

    PanelButton(Screen screen, Settings settings, int x, int y, int width, int height, String label, GameFont font,
                boolean boxed, OnClickListener listener) {
        this(screen, settings, x, y, width, height, label, font, boxed, false, listener);
    }

    PanelButton(Screen screen, Settings settings, int x, int y, int width, int height, String label, GameFont font,
                boolean boxed, boolean centered, OnClickListener listener) {
        super(screen, x, y, width, height);
        this.label = label;
        this.font = font;
        this.fillColor = settings.buttonColor();
        this.disabledColor = settings.disabledColor();
        this.pressedLabelColor = settings.pressedLabelColor();
        this.boxed = boxed;
        this.centered = centered;
        setOnClickListener(listener);
        paint.setAntiAlias(false);
    }

    /**
     * Darkens or lightens a colour by a delta per channel, clamped to 0..255. J2ME colours are 24-bit RGB, so
     * the alpha channel is always opaque here.
     * Original: f.a(int, int).
     */
    static int shade(int color, int delta) {
        int red = Math.min(Math.max((color >> 16 & 0xFF) + delta, 0), 255);
        int green = Math.min(Math.max((color >> 8 & 0xFF) + delta, 0), 255);
        int blue = Math.min(Math.max((color & 0xFF) + delta, 0), 255);
        return 0xFF000000 | red << 16 | green << 8 | blue;
    }

    @Override
    public void paint(Canvas canvas) {
        int fill = isEnabled() ? fillColor : disabledColor;
        int light = shade(fill, 32);
        int dark = shade(fill, -32);
        if (boxed) {
            paint.setColor(fill);
            canvas.drawRect(x, y, x + width, y + height, paint);
            paint.setColor(isPressed() ? dark : light);
            canvas.drawLine(x, y, x + width, y, paint);
            canvas.drawLine(x, y, x, y + height, paint);
            paint.setColor(isPressed() ? light : dark);
            canvas.drawLine(x + width, y, x + width, y + height, paint);
            canvas.drawLine(x, y + height, x + width, y + height, paint);
        }
        if (label == null) {
            return;
        }
        int offset = isPressed() ? 1 : 0;
        int labelX = centered ? x + width / 2 : x;
        int labelY = y + (height - font.height()) / 2;
        font.drawText(canvas, label, labelX + offset, labelY + offset,
                centered ? Anchor.TOP | Anchor.HCENTER : Anchor.TOP | Anchor.LEFT,
                isPressed() ? (boxed ? pressedLabelColor : fill) : Color.WHITE);
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
