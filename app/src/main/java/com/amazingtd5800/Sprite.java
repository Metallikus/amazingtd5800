package com.amazingtd5800;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Frame sheet with the reference pixel at the centre of a frame: a frame is taken from the sheet as
 * (frame % cols, frame / cols), and a position is already the reference pixel shifted by half a frame, so
 * coordinates match the original here.
 * Original: javax.microedition.lcdui.game.Sprite (microemulator).
 */
final class Sprite {

    private final Bitmap sheet;
    private final int frameWidth;
    private final int frameHeight;
    private final int columns;
    private final int refX;
    private final int refY;
    private final Rect source = new Rect();
    private final Rect target = new Rect();
    private int[] sequence;
    private int frame;
    private int x;
    private int y;

    Sprite(Bitmap sheet, int frameWidth, int frameHeight) {
        this.sheet = sheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.columns = sheet.getWidth() / frameWidth;
        this.refX = frameWidth / 2;
        this.refY = frameHeight / 2;
    }

    /** Positions the frame by its centre. Original: setRefPixelPosition. */
    void setRefPixelPosition(int x, int y) {
        this.x = x - refX;
        this.y = y - refY;
    }

    void setFrameSequence(int[] sequence) {
        this.sequence = sequence;
        this.frame = 0;
    }

    /** Selects the frame by index. Original: setFrame. */
    void setFrame(int frame) {
        if (frame < 0 || frame >= frames()) {
            throw new IndexOutOfBoundsException();
        }
        this.frame = frame;
    }

    private int frames() {
        return sequence == null ? columns * (sheet.getHeight() / frameHeight) : sequence.length;
    }

    /** Advances the frame, wrapping back to zero at the end of the sequence. Original: nextFrame. */
    void nextFrame() {
        if (frame == frames() - 1) {
            frame = 0;
        } else {
            frame++;
        }
    }

    void paint(Canvas canvas) {
        int frame = sequence == null ? this.frame : sequence[this.frame];
        source.set(frameWidth * (frame % columns), frameHeight * (frame / columns),
                frameWidth * (frame % columns) + frameWidth, frameHeight * (frame / columns) + frameHeight);
        target.set(x, y, x + frameWidth, y + frameHeight);
        canvas.drawBitmap(sheet, source, target, null);
    }
}
