package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * A level path: its waypoints, the road 18 wide (inner layer 16) and the direction arrows along the axis.
 * Waypoints are left exactly as the level made them — only the levels that call {@link #snapToGrid()} land on the
 * 8-pixel grid — and the entry point is where the first segment crosses the field edge.
 * Original: dd.
 */
final class Path {

    /** A path coordinate rounded to the nearest multiple of 8. Original: ca.a(int). */
    static int snap(int coordinate) {
        int remainder = coordinate % 8;
        if (remainder > 4) {
            remainder -= 8;
        }
        return coordinate - remainder;
    }

    private final Vec2[] points;
    private int count;
    /** Road width and its inner layer width. Original: dd.c, dd.d. */
    private final int width = 18;
    private final int inset = 4;
    private Vec2 entry;
    // This path's own paint: the shared path paint would bring in the colour of whatever drew before it.
    private final Paint paint = new Paint();

    Path(int capacity) {
        points = new Vec2[capacity];
    }

    /** Adds a waypoint. Original: dd.a(ri). */
    void add(Vec2 point) {
        points[count++] = point;
    }

    /**
     * Rounds every waypoint and snaps it to the 8-pixel grid, in place, the way the level base class does before it
     * draws a path. Levels 5 and 10 override that hook with the original's empty body, so their arc and spiral keep
     * the exact points the generator produced and only the levels that do call this ever land on the grid.
     * Original: ca.a(dd), which rounds with (int)(v + 0.5f) and then calls ca.a(int) per coordinate.
     */
    void snapToGrid() {
        for (int i = 0; i < count; i++) {
            points[i].x = snap((int) (points[i].x + 0.5f));
            points[i].y = snap((int) (points[i].y + 0.5f));
        }
    }

    /** The waypoint at an index. Original: dd.a(int). */
    Vec2 point(int index) {
        return points[index];
    }

    /** Number of waypoints. Original: dd.c(). */
    int count() {
        return count;
    }

    /** Whether a tower stands too close to the road. Original: dd.a(ri, float). */
    boolean blocks(Vec2 point, float margin) {
        float limit = inset + margin;
        for (int i = 1; i < count; i++) {
            if (Vec2.distanceToSegment(points[i - 1], points[i], point) < limit) {
                return true;
            }
        }
        return false;
    }

    /**
     * Where enemies enter the screen: the first crossing of the first segment with the field edge. The field is
     * ca.a..ca.a+ca.c on x and ca.b..ca.b+ca.d on y, so the HUD strip (HEIGHT - 58) is not part of it: on level
     * 19 the first segment ends exactly on that border and would cross no side without it.
     * Original: dd.b().
     */
    Vec2 entry() {
        if (entry == null) {
            Vec2 topLeft = new Vec2(0, 0);
            Vec2 bottomLeft = new Vec2(0, GameScreen.HEIGHT - 58);
            Vec2 topRight = new Vec2(GameScreen.WIDTH, 0);
            Vec2 bottomRight = new Vec2(GameScreen.WIDTH, GameScreen.HEIGHT - 58);
            entry = Vec2.intersect(points[0], points[1], topLeft, bottomLeft);
            if (entry == null) {
                entry = Vec2.intersect(points[0], points[1], topRight, bottomRight);
            }
            if (entry == null) {
                entry = Vec2.intersect(points[0], points[1], topLeft, topRight);
            }
            if (entry == null) {
                entry = Vec2.intersect(points[0], points[1], bottomLeft, bottomRight);
            }
        }
        return entry;
    }

    /**
     * The road is a wide layer in the road colour (cj.c) and a narrow one in the path colour. The strip is a
     * quadrilateral of two triangles; half the width is taken perpendicular to the segment rounded away from
     * zero, so the strip stays centred on the segment and its edge comes out equal on both sides. A waypoint's
     * disc fills the whole fillArc(x, y, w, h) box, so its right and bottom edges sit on x + w and y + h.
     * Original: ca.a(Graphics, dd, int, int), f.a(Graphics, int, int, int, int, int).
     */
    void draw(Canvas canvas, int road, int inner, int arrows) {
        for (int layer = 0; layer < 2; layer++) {
            paint.setColor(layer == 0 ? road : inner);
            int size = width - layer * 2;
            for (int i = 1; i < count; i++) {
                Vec2 from = points[i - 1];
                Vec2 to = points[i];
                int left = (int) to.x - width / 2 + layer;
                int top = (int) to.y - width / 2 + layer;
                canvas.drawArc(left, top, left + size, top + size, 0, 360, true, paint);
                band(canvas, paint, from, to, size);
            }
        }
        drawArrows(canvas, arrows);
    }

    /**
     * The segment strip is two triangles; the offset is perpendicular to the segment and rounded away from zero.
     * The Force Field beam draws the same quadrilateral, which is why this one is static and shared with towers.
     * Original: f.a.
     */
    static void band(Canvas canvas, Paint paint, Vec2 from, Vec2 to, int width) {
        float dx = to.x - from.x;
        float dy = to.y - from.y;
        float scale = width / ((float) Math.sqrt(dx * dx + dy * dy) * 2.0f);
        int ox = round(scale * -dy);
        int oy = round(scale * dx);
        android.graphics.Path quad = new android.graphics.Path();
        quad.moveTo(from.x + ox, from.y + oy);
        quad.lineTo(from.x - ox, from.y - oy);
        quad.lineTo(to.x - ox, to.y - oy);
        quad.lineTo(to.x + ox, to.y + oy);
        quad.close();
        canvas.drawPath(quad, paint);
    }

    /** The original's rounding: (int)(v > 0 ? v + 0.5 : v - 0.5). Original: f.a. */
    private static int round(float value) {
        return (int) (value > 0 ? value + 0.5f : value - 0.5f);
    }

    /** The same arrows in another colour — they blink while "Send now" is awaited (ca.r && ca.s). Original: ca.a(Graphics, dd). */
    void drawArrows(Canvas canvas, int color) {
        paint.setColor(color);
        arrow(canvas, paint, entry(), point(1));
        for (int i = 2; i < count; i++) {
            arrow(canvas, paint, points[i - 1], points[i]);
        }
    }

    /** Direction arrow on a segment longer than 20 pixels. Original: ca.a(Graphics, ri, ri). */
    private void arrow(Canvas canvas, Paint paint, Vec2 from, Vec2 to) {
        Vec2 direction = new Vec2(to.x - from.x, to.y - from.y);
        if (direction.length() <= 20.0f) {
            return;
        }
        direction.normalize();
        Vec2 start = direction.scaled(6).add(from);
        Vec2 tip = direction.scaled(20).add(start);
        Vec2 shaft = new Vec2(tip.x - start.x, tip.y - start.y);
        float length = shaft.length();
        shaft.normalize();
        band(canvas, paint, start, new Vec2(tip.x - round(shaft.x * 3), tip.y - round(shaft.y * 3)), 3);
        Vec2 perpendicular = new Vec2(-shaft.y, shaft.x);
        perpendicular.normalize();
        perpendicular.scale(length * 0.3f);
        Vec2 center = shaft.add(new Vec2(tip.x - shaft.x * length * 0.5f, tip.y - shaft.y * length * 0.5f));
        android.graphics.Path head = new android.graphics.Path();
        head.moveTo((int) (center.x - perpendicular.x + 0.5f), (int) (center.y - perpendicular.y + 0.5f));
        head.lineTo((int) (center.x + perpendicular.x + 0.5f), (int) (center.y + perpendicular.y + 0.5f));
        head.lineTo((int) (tip.x + 0.5f), (int) (tip.y + 0.5f));
        head.close();
        canvas.drawPath(head, paint);
    }
}
