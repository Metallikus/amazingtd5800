package com.amazingtd5800;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * A button made of a frame sheet (top frame normal, bottom pressed) with a caption. Original: ex.
 */
public class TextButton extends Widget {

    private final Bitmap frames;
    private final int frameCount;
    private final GameFont font;
    private String label;
    private final int labelX;
    private final int labelY;
    private final Rect source = new Rect();
    private final Rect destination = new Rect();
    private final Paint bitmapPaint = new Paint();

    public TextButton(Screen screen, int x, int y, Bitmap frames, int frameCount,
                      String label, GameFont font, Widget.OnClickListener listener) {
        super(screen, x, y, frames.getWidth(), frames.getHeight() / frameCount);
        this.frames = frames;
        this.frameCount = frameCount;
        this.font = font;
        this.label = label;
        this.labelX = x + width / 2;
        this.labelY = y + (height - font.height()) / 2;
        setOnClickListener(listener);
        bitmapPaint.setFilterBitmap(false);
        bitmapPaint.setColor(Color.WHITE);
    }

    @Override
    public final void paint(Canvas canvas) {
        if (frameCount > 1 && isPressed()) {
            source.set(0, height, width, height * 2);
        } else {
            source.set(0, 0, width, height);
        }
        destination.set(x, y, x + width, y + height);
        canvas.drawBitmap(frames, source, destination, bitmapPaint);
        if (label != null) {
            int offset = isPressed() ? 2 : 0;
            font.drawText(canvas, label, labelX + offset, labelY + offset,
                    Anchor.TOP | Anchor.HCENTER, Color.WHITE);
        }
    }

    /** The button caption changes in place (Sound: On/Off, difficulty names). Original: ex.a(String). */
    public void setLabel(String label) {
        this.label = label;
    }

    /** The button has a single flag: it hides the button and stops it taking touches. Original: ex.a(boolean). */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setVisible(enabled);
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
