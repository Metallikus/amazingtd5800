package com.amazingtd5800;

/**
 * Vector between two path points: normalisation, scaling and segment intersection.
 * Original: ri.
 */
final class Vec2 {

    float x;
    float y;

    Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    Vec2(Vec2 other) {
        this(other.x, other.y);
    }

    /** Shortest distance from a point to a segment. Original: ri.a(ri, ri, ri). */
    static float distanceToSegment(Vec2 from, Vec2 to, Vec2 point) {
        float dx = to.x - from.x;
        float dy = to.y - from.y;
        if (dx == 0f && dy == 0f) {
            throw new IllegalArgumentException("p1 and p2 cannot be the same point");
        }
        float t = ((point.x - from.x) * dx + (point.y - from.y) * dy) / (dx * dx + dy * dy);
        Vec2 closest = from;
        if (t >= 0.0f) {
            closest = t > 1.0f ? to : new Vec2(from.x + t * dx, from.y + t * dy);
        }
        return closest.distance(point);
    }

    /** Intersection point of two segments, or null when they do not cross. Original: ri.a(ri, ri, ri, ri). */
    static Vec2 intersect(Vec2 a1, Vec2 a2, Vec2 b1, Vec2 b2) {
        float dx1 = a2.x - a1.x;
        float dx2 = b2.x - b1.x;
        float dy1 = a2.y - a1.y;
        float dy2 = b2.y - b1.y;
        float dx = a1.x - b1.x;
        float dy = a1.y - b1.y;
        float length1 = (float) Math.sqrt(dx1 * dx1 + dy1 * dy1);
        float length2 = (float) Math.sqrt(dx2 * dx2 + dy2 * dy2);
        float dot = dx1 * dx2 + dy1 * dy2;
        if (Math.abs(dot /= length1 * length2) == 1.0f) {
            return null;
        }
        dot = dy2 * dx1 - dx2 * dy1;
        float t = (dx2 * dy - dy2 * dx) / dot;
        Vec2 point = new Vec2(a1.x + t * dx1, a1.y + t * dy1);
        return closeEnough(a1, a2, point, length1) && closeEnough(b1, b2, point, length2) ? point : null;
    }

    /** Checks the intersection point lies on both segments. Original: ri.a(...). */
    private static boolean closeEnough(Vec2 from, Vec2 to, Vec2 point, float length) {
        float first = (float) (Math.sqrt(square(point.x - from.x) + square(point.y - from.y))
                + Math.sqrt(square(point.x - to.x) + square(point.y - to.y)));
        return Math.abs(length - first) <= 0.01f;
    }

    private static float square(float value) {
        return value * value;
    }

    void scale(float factor) {
        x *= factor;
        y *= factor;
    }

    void normalize() {
        scale(1.0f / length());
    }

    Vec2 add(Vec2 other) {
        return new Vec2(x + other.x, y + other.y);
    }

    Vec2 subtract(Vec2 other) {
        return new Vec2(x - other.x, y - other.y);
    }

    Vec2 scaled(float factor) {
        return new Vec2(x * factor, y * factor);
    }

    float distance(Vec2 other) {
        return (float) Math.sqrt(square(x - other.x) + square(y - other.y));
    }

    float distance(float x, float y) {
        return (float) Math.sqrt(square(this.x - x) + square(this.y - y));
    }

    float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    Vec2 copy() {
        return new Vec2(this);
    }

    /** Whether the point lies within radius of another one. Original: ri.b(ri, float). */
    boolean within(Vec2 other, float radius) {
        Vec2 delta = subtract(other);
        return delta.x * delta.x + delta.y * delta.y <= radius * radius;
    }

    /** Whether the point lands on that pixel once rounded to integers. Original: ri.a(int, int). */
    boolean isAt(int x, int y) {
        return (int) (this.x + 0.5f) - x == 0 && (int) (this.y + 0.5f) - y == 0;
    }
}
