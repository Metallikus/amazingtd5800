package com.amazingtd5800;

/**
 * A line between two level-select nodes. A locked line joins its node only once that node is unlocked; the
 * others are always active.
 * Original: cb.
 */
final class LevelPath {

    private final LevelNode start;
    private final LevelNode end;
    private final boolean locked;
    private boolean visible = true;
    private boolean connected;

    LevelPath(LevelNode start, LevelNode end, boolean locked) {
        this.start = start;
        this.end = end;
        this.locked = locked;
    }

    /** Reset before progress is re-read. Original: cb.b(). */
    void reset() {
        connected = false;
        visible = true;
    }

    /** Whether the line is joined, and whether the node it leads to is unlocked. Original: cb.a(boolean, boolean). */
    void connect(boolean unlocked) {
        connected = (locked && unlocked) || !locked;
        visible = connected;
        end.setEnabled(connected);
        end.setVisible(connected);
    }

    void setVisible(boolean visible) {
        this.visible = visible;
    }

    boolean isVisible() {
        return visible;
    }

    boolean isConnected() {
        return connected;
    }

    LevelNode start() {
        return start;
    }

    LevelNode end() {
        return end;
    }
}
