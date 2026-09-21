package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * A panel of text lines: width is the widest line plus 8, height is 4 + lines * line height, both measured with the
 * game screen's label font (cs.d, Size.SMALL). Uncentred (pb.a(int,int)) the left edge snaps to the area's right edge
 * (ca.c) and the bottom to its bottom edge (ca.d); centred (pb.a(int,int,int,int)) the panel sits in the middle of
 * its area, and on one axis it centres on the axis passed in, snapping to the near edge when it overflows the other.
 * Each line draws its shadow a pixel to the right and itself a pixel above (gh.a: x+1, y and x, y-1); a separator is
 * drawn only over a button line (gh.d()), and only a colourful line (gh.c()) highlights, in cj.c, turning white again
 * once the highlight is gone (pb.b(gh)).
 * Original: pb.
 */
public abstract class TextPanel {

    /** Colour of an uncoloured line (gh.c() == false). Original: gh.a(Graphics). */
    static final int PLAIN = Color.rgb(68, 68, 68);
    /** Colour of a colourful line that is not highlighted. Original: pb.b(gh). */
    static final int COLORFUL = Color.WHITE;
    /** Lines of the fail and victory panels. Original: ay.a(n).a(0xFFFF00). */
    static final int RESULT = Color.rgb(255, 255, 0);
    /** Line shadow colour. Original: gh.a(Graphics). */
    private static final int SHADOW = Color.BLACK;

    /**
     * One line of the lines[] array: a caption in its own font, its colour, whether it is a button and what a tap
     * does. In the original df appended extra captions offset by 70 pixels (two columns in the tower panel); here
     * every caption is its own line, so a panel has a single column.
     * Original: gh.
     */
    private final class Line {

        /** Line anchor 20 (TOP|LEFT); button lines (aw/dg) use 17 (TOP|HCENTER). Original: gh(String, int, boolean, boolean). */
        private final int anchor;
        private final GameFont font;
        private final String text;
        /** A button line gets a separator above it and can be highlighted and pressed. Original: gh.d(). */
        private final boolean button;
        /** An uncoloured line (TowerName, stat lines) neither highlights nor presses. Original: gh.c(). */
        private final boolean colorful;
        /** A boxed button line draws a plate under itself (f.a(..., 25, cj.d, ...)). Original: aw/dg. */
        private final boolean boxed;
        private final Runnable onClick;
        private int color;
        private boolean highlighted;

        Line(GameFont font, String text, int color, boolean button, boolean colorful, boolean boxed,
             Runnable onClick) {
            this.font = font;
            this.text = text;
            this.anchor = Anchor.TOP | (boxed ? Anchor.HCENTER : Anchor.LEFT);
            this.color = color;
            this.button = button;
            this.colorful = colorful;
            this.boxed = boxed;
            this.onClick = onClick;
        }

        /** The widest line gives the panel its width. Original: pb.d(). */
        int width() {
            return font.stringWidth(text);
        }

        /** Anchor 17 (TOP|HCENTER) shifts a line by half its width. Original: gh.a(Graphics, int, int, int, int). */
        private boolean centered() {
            return (anchor & Anchor.HCENTER) != 0;
        }

        /** The line left the finger: colour back to white, highlight removed. Original: pb.b(gh). */
        void unhighlight() {
            if (button && colorful) {
                highlighted = false;
                color = COLORFUL;
            }
        }

        /** Finger released on a line: the line fires and the panel hides (pb.a(false)). Original: pb.d(int, int), pb.c(int). */
        void release() {
            unhighlight();
            if (button && colorful && onClick != null) {
                onClick.run();
            }
        }

        /** Line under the finger highlights: colour cj.c, shifted a pixel. Original: pb.a(gh), pb.c(int). */
        void press() {
            if (button && colorful) {
                highlighted = true;
            }
        }

        /** Shadow a pixel to the right of the line, the line itself a pixel above. Original: gh.a(Graphics, int, int, int, int). */
        void draw(Canvas canvas, int x, int y, int width, int fill) {
            if (boxed) {
                // The button line's plate sits at (n2-4+2, n3-4+2) and is one line tall; in the original that was
                // the same 25 pixels as a panel line (cs.c + 4), which is why it fitted. Here the line height comes
                // from the panel's own font metrics.
                box(canvas, fill, x - 4 + 2, y - 4 + 2, width - 4, metrics.lineHeight());
            }
            int shift = highlighted ? 1 : 0;
            // An uncoloured line (gh.c() == false) is 0x444444 and highlighting does not recolour it, so
            // "Upgrade2 $30" stays grey while money is short.
            int main = colorful ? (highlighted ? settings.pressedLabelColor() : color) : PLAIN;
            int left = centered() ? x + (width - 8) / 2 : x;
            draw(canvas, left + shift + 1, y + shift, SHADOW);
            draw(canvas, left + shift, y + shift - 1, main);
        }

        private void draw(Canvas canvas, int x, int y, int color) {
            font.drawText(canvas, text, x, y, anchor, color);
        }

        /** The button line's plate, its border inverted while highlighted. Original: f.a(Graphics, int, int, int, int, int, boolean). */
        private void box(Canvas canvas, int fill, int x, int y, int width, int height) {
            paint.setColor(fill);
            canvas.drawRect(x, y, x + width, y + height, paint);
            int light = PanelButton.shade(fill, 32);
            int dark = PanelButton.shade(fill, -32);
            paint.setColor(highlighted ? dark : light);
            canvas.drawLine(x, y, x + width, y, paint);
            canvas.drawLine(x, y, x, y + height, paint);
            paint.setColor(highlighted ? light : dark);
            canvas.drawLine(x + width, y, x + width, y + height, paint);
            canvas.drawLine(x, y + height, x + width, y + height, paint);
        }
    }

    /** Both line width and line height come from the game screen's label font (cs.d, Size.SMALL). Original: pb.j, pb.d(). */
    private final GameFont metrics = Assets.get().labelFont();
    private final Screen screen;
    private final Settings settings;
    /** Plate colour — cj.d (al paints 0x888888) — also used, darkened, for the border and separators. Original: pb.b(int). */
    private final int fill;
    /** The width its creator asked for (200 for al, 100 for ee); lines may widen it. Original: pb(int, int). */
    private final int minWidth;
    private final List<Line> lines = new ArrayList<>();
    private final Paint paint = new Paint();
    private final int left;
    private final int top;
    private final int bottom;
    private final boolean centered;
    /** For a button panel (cs.i(cs).a(n2, n3)): the passed x is the button's axis, and centring is around it. Original: pb.a(int, int). */
    private final boolean centerX;
    /** Line under the finger (-1 = none). Original: pb.l. */
    private int pressed = -1;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean laidOut;

    /**
     * Width is the widest line plus 8 but never below the width the creator asked for (200 for al, 100 for ee);
     * height is 4 + lines * line height, computed in layout() once the lines exist. Uncentred the panel snaps to the
     * area's right and bottom edges; centred it sits in the middle of its area, and on one axis it centres on the
     * axis passed in, snapping to the near edge when it overflows the other.
     * Original: pb(int, int), pb.a(int, int), pb.a(int, int, int, int).
     */
    TextPanel(Screen screen, Settings settings, int fill, int minWidth, int x, int y, int bottom, boolean centered,
              boolean centerX) {
        this.screen = screen;
        this.settings = settings;
        this.fill = fill;
        this.minWidth = minWidth;
        this.left = x;
        this.top = y;
        this.bottom = bottom;
        this.centered = centered;
        this.centerX = centerX;
        this.paint.setAntiAlias(false);
    }

    /**
     * Panel captions in the screen font, colourful or not depending on their colour: an uncoloured line (0x444444)
     * neither highlights in cj.c nor presses.
     * Original: pb.a(int, gh), gh(String, boolean, boolean).
     */
    final void addLines(GameFont font, String text, int firstColor, int color, boolean button) {
        String[] parts = text.split("\n");
        for (int i = 0; i < parts.length; i++) {
            int line = i == 0 ? firstColor : color;
            lines.add(new Line(font, parts[i], line, button, line != PLAIN, false, null));
        }
    }

    /** A gh that has an action (av, ba, bb, bc, az, ax, aw, de, df): a button line. */
    final void addRow(GameFont font, String text, boolean boxed, boolean enabled, Runnable onClick) {
        lines.add(new Line(font, text, COLORFUL, true, enabled, boxed, onClick));
    }

    /**
     * Width is the widest line plus 8 but never below the width the creator asked for; height is 4 + lines * line
     * height.
     * Original: pb.d().
     */
    private void layout() {
        if (laidOut) {
            return;
        }
        int widest = 0;
        for (Line line : lines) {
            widest = Math.max(widest, line.width());
        }
        width = Math.max(minWidth, widest + 8);
        height = 4 + lines.size() * metrics.lineHeight();
        y = centered ? (400 - height) >> 1 : top + height > bottom ? bottom - height : top;
        if (centered) {
            x = (screen.width() - width) >> 1;
        } else if (centerX) {
            x = Math.max(0, Math.min(left - width / 2, screen.width() - width));
        } else {
            x = left + width > screen.width() ? screen.width() - width : left;
        }
        laidOut = true;
    }

    /** Plate (b(cj.d)), lines with separators, and the button lines' plates. Original: pb.a(Graphics). */
    public void paint(Canvas canvas) {
        layout();
        paint.setColor(fill);
        canvas.drawRect(x, y, x + width, y + height, paint);
        paint.setColor(PanelButton.shade(fill, 32));
        canvas.drawLine(x, y, x + width, y, paint);
        canvas.drawLine(x, y, x, y + height, paint);
        paint.setColor(PanelButton.shade(fill, -32));
        canvas.drawLine(x + width, y, x + width, y + height, paint);
        canvas.drawLine(x, y + height, x + width, y + height, paint);
        int top = y + 4;
        for (Line line : lines) {
            if (line.button && line != lines.get(0)) {
                separator(canvas, fill, top - 4);
            }
            line.draw(canvas, x + 4, top, width, fill);
            top += metrics.lineHeight();
        }
    }

    /** Over a button line n>0: a dark line at y = g + j*n and a light one a pixel below. Original: pb.a(Graphics). */
    private void separator(Canvas canvas, int fill, int y) {
        paint.setColor(PanelButton.shade(fill, -32));
        canvas.drawLine(x + 4, y, x + 4 + width - 8, y, paint);
        paint.setColor(PanelButton.shade(fill, 32));
        canvas.drawLine(x + 4, y + 1, x + 4 + width - 8, y + 1, paint);
    }

    /** A touch inside the plate takes the line at (n3 - g) / j; outside it the panel is hidden. Original: pb.e(int, int), pb.a_(int, int). */
    public boolean pointerPressed(float x, float y) {
        return press(x, y);
    }

    /** Finger drags across the panel: the line under it highlights, the one left behind dims. Original: pb.c(int, int). */
    public boolean pointerDragged(float x, float y) {
        return press(x, y);
    }

    /** Finger released on a line — the line fires. Original: pb.d(int, int). */
    public boolean pointerReleased(float x, float y) {
        if (!inside(x, y)) {
            return false;
        }
        lines.get(rowAt(y)).release();
        pressed = -1;
        return true;
    }

    private boolean inside(float x, float y) {
        layout();
        return x >= this.x && y >= this.y && x <= this.x + width && y <= this.y + height;
    }

    /** The line under the finger: (n3 - g) / j. Original: pb.c(int). */
    private int rowAt(float y) {
        return Math.min(((int) y - this.y) / metrics.lineHeight(), lines.size() - 1);
    }

    private boolean press(float x, float y) {
        if (!inside(x, y)) {
            pressed = -1;
            return false;
        }
        int row = rowAt(y);
        if (row != pressed) {
            if (pressed >= 0 && pressed < lines.size()) {
                lines.get(pressed).unhighlight();
            }
            lines.get(row).press();
            pressed = row;
        }
        return true;
    }
}
