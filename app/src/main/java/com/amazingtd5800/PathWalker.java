package com.amazingtd5800;

/**
 * Walks the waypoints of a level path: index of the current point, the step to the next one,
 * and the end-of-path flag.
 * Original: bf.
 */
final class PathWalker {

    private final Path path;
    private int index;

    PathWalker(Path path) {
        this.path = path;
    }

    /** Moves to the next waypoint. Original: bf.a(). */
    void advance() {
        if (index < path.count()) {
            index++;
        }
    }

    /** Advances and returns the next waypoint. Original: bf.a(). */
    Vec2 next() {
        advance();
        return current();
    }

    /** The waypoint the walker is standing on. Original: bf.b(). */
    Vec2 current() {
        return path.point(index);
    }

    Path path() {
        return path;
    }

    /** Starts the path over from the first waypoint. Original: bf.d(). */
    void reset() {
        index = 0;
    }

    /** Whether the enemy reached the last waypoint. Original: bf.e(). */
    boolean finished() {
        return index >= path.count() - 1;
    }
}
