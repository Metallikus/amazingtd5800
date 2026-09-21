package com.amazingtd5800;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * The original's Canvas: a screen is drawn in 360x640 design coordinates and then scaled whole, with margins, onto
 * the device screen.
 * Original: Canvas.
 */
public abstract class Screen extends View {
    /** One game tick is one frame of the original loop, which slept wait(50 - l6) and handed the game exactly one
     * tick(l2 % 100) per 50 ms. Everything counted "once per tick" (explosion damage, blinking arrows, reloading)
     * comes out as it did on the phone at 20 fps and does not depend on the device refresh rate.
     * Original: o.run(). */
    private static final long TICK = 50; // 1000 / 20
    private static final int DESIGN_WIDTH = 360;
    private static final int DESIGN_HEIGHT = 640;

    private final Game game;
    private final List<Widget> widgets = new ArrayList<>();
    /** The panel drawn over the widgets, catching touches inside itself. Original: the screens' f field. */
    private TextPanel dialog;
    /** A touch the panel's lines caught reaches those same lines again on release. Original: jm. */
    private boolean dialogPressed;
    /** This screen's bitmap paint: white and unfiltered, as Coin.sprite and TextButton had it — bitmaps are not tinted. */
    protected final Paint bitmapPaint = new Paint();
    private float scale = 1f;
    private float offsetX;
    private float offsetY;
    /** Time of the original loop's last frame. Original: o.c. */
    private long lastFrame;
    /** Time accumulated since the last game tick. Original: o.run(). */
    private long pending;

    protected Screen(Game game, Context context) {
        super(context);
        this.game = game;
        bitmapPaint.setFilterBitmap(false);
        bitmapPaint.setColor(android.graphics.Color.WHITE);
    }

    protected final Game game() {
        return game;
    }

    protected final int width() {
        return DESIGN_WIDTH;
    }

    protected final int height() {
        return DESIGN_HEIGHT;
    }

    protected final void add(Widget widget) {
        widgets.add(widget);
    }

    /** The same anchor coordinate path as microemulator's. Original: Graphics.drawImage. */
    protected final void drawBitmap(Canvas canvas, Bitmap bitmap, int x, int y, int anchor) {
        if ((anchor & Anchor.RIGHT) != 0) {
            x -= bitmap.getWidth();
        } else if ((anchor & Anchor.HCENTER) != 0) {
            x -= bitmap.getWidth() / 2;
        }
        if ((anchor & Anchor.BOTTOM) != 0) {
            y -= bitmap.getHeight();
        } else if ((anchor & Anchor.VCENTER) != 0) {
            y -= bitmap.getHeight() / 2;
        }
        canvas.drawBitmap(bitmap, x, y, bitmapPaint);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        scale = Math.min(width / (float) DESIGN_WIDTH, height / (float) DESIGN_HEIGHT);
        offsetX = (width - DESIGN_WIDTH * scale) / 2f;
        offsetY = (height - DESIGN_HEIGHT * scale) / 2f;
    }

    /**
     * The original loop slept wait(50 - l6) between iterations and gave the game one tick each time. Explosion
     * damage is counted once per tick, so the tick here is fixed and independent of the refresh rate: at 60 Hz and
     * 144 Hz enemies take the same damage as at the original's frame rate.
     * Original: o.run().
     */
    @Override
    protected final void onDraw(Canvas canvas) {
        long now = System.nanoTime();
        long elapsed = lastFrame == 0 ? 0 : (now - lastFrame) / 1_000_000L;
        lastFrame = now;
        pending += elapsed;
        while (pending >= TICK) {
            tick(TICK);
            pending -= TICK;
        }
        int saved = canvas.save();
        canvas.translate(offsetX, offsetY);
        canvas.scale(scale, scale);
        paint(canvas);
        if (dialog != null) {
            dialog.paint(canvas);
        }
        canvas.restoreToCount(saved);
        postInvalidateOnAnimation();
    }

    /** One game step of TICK ms; the frame is drawn only after it. Original: o.b(long). */
    protected void tick(long elapsed) {
    }

    protected abstract void paint(Canvas canvas);

    /** Open a panel over the screen; from then on touches go only to it. Original: co.a(f). */
    protected final void showDialog(TextPanel dialog) {
        this.dialog = dialog;
    }

    protected final void closeDialog() {
        this.dialog = null;
    }

    /** The original screens' a(Graphics): invisible widgets (hy.b / ex.l) are not drawn. */
    protected final void paintWidgets(Canvas canvas) {
        for (Widget widget : widgets) {
            if (widget.isVisible()) {
                widget.paint(canvas);
            }
        }
    }

    /** A touch always goes to the widgets; the panel only highlights its own line and hides when the touch misses it. Original: jm.a/b/c(c). */
    protected final boolean dialogPressed() {
        return dialogPressed;
    }

    /**
     * The list is walked from the end and the first widget the touch lands in takes it. The panel always returns
     * false, so widgets under it see the touch too; when the touch misses a line the panel hides itself (pb.a_).
     * Original: jm.a/b/c(c).
     */
    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        float x = (event.getX() - offsetX) / scale;
        float y = (event.getY() - offsetY) / scale;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dialogPressed = dialog != null && dialog.pointerPressed(x, y);
                if (!dialogPressed) {
                    closeDialog();
                }
                for (int i = widgets.size() - 1; i >= 0; i--) {
                    if (widgets.get(i).pointerPressed(x, y)) {
                        break;
                    }
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (dialogPressed) {
                    dialog.pointerDragged(x, y);
                }
                for (int i = widgets.size() - 1; i >= 0; i--) {
                    if (widgets.get(i).pointerDragged(x, y)) {
                        break;
                    }
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (dialogPressed) {
                    dialog.pointerReleased(x, y);
                    closeDialog();
                }
                for (int i = widgets.size() - 1; i >= 0; i--) {
                    if (widgets.get(i).pointerReleased(x, y)) {
                        break;
                    }
                }
                dialogPressed = false;
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
