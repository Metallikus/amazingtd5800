package com.amazingtd5800;

import android.graphics.Canvas;

/**
 * The widget rectangle in screen coordinates, its pressed state and its press handling.
 * Original: nv.
 */
public abstract class Widget {

    public interface OnClickListener {
        void onClick(float x, float y);
    }

    public abstract void paint(Canvas canvas);

    protected final Screen screen;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    /** The panel learns its position and size only once its lines are known. Original: pb.a(int, int). */
    final void setRect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    private boolean pressed;
    private OnClickListener listener;

    /** An invisible widget is neither drawn nor tappable. Original: hy.b, ex.l. */
    private boolean visible = true;
    /** A disabled widget does not take touches. Original: hy.a. */
    private boolean enabled = true;

    protected Widget(Screen screen, int x, int y, int width, int height) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public final boolean isVisible() {
        return visible;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final int x() {
        return x;
    }

    public final int y() {
        return y;
    }

    public final int width() {
        return width;
    }

    public final int height() {
        return height;
    }

    public final boolean contains(float px, float py) {
        return visible && enabled && px >= x && py >= y && px <= x + width && py <= y + height;
    }

    public final boolean pointerPressed(float px, float py) {
        boolean wasPressed = pressed;
        pressed = contains(px, py);
        if (!wasPressed && pressed) {
            screen.game().vibrate();
            onPress(px, py);
        }
        return pressed;
    }

    public final boolean pointerReleased(float px, float py) {
        boolean hit = contains(px, py);
        if (hit && listener != null) {
            listener.onClick(px, py);
        }
        if (pressed) {
            onRelease(px, py);
        }
        pressed = false;
        return hit;
    }

    /**
     * Dragging a finger over the widget: entering presses it, leaving only clears the pressed border. A real
     * release happens only when the finger lifts (pointerReleased), otherwise a carried tower would slip out of
     * the finger as soon as the finger left the button.
     * Original: nv.c.
     */
    public final boolean pointerDragged(float px, float py) {
        boolean wasPressed = pressed;
        pressed = contains(px, py);
        if (!wasPressed && pressed) {
            screen.game().vibrate();
            onPress(px, py);
        } else if (wasPressed && !pressed) {
            onDragExit(px, py);
        }
        if (pressed) {
            onDrag(px, py);
        }
        return pressed;
    }

    protected void onPress(float x, float y) {
    }

    /** Leaving the widget mid-drag clears only the pressed border. Original: nv.b. */
    protected void onDragExit(float x, float y) {
    }

    protected void onRelease(float x, float y) {
    }

    protected void onDrag(float x, float y) {
    }

    protected final boolean isPressed() {
        return pressed;
    }
}
