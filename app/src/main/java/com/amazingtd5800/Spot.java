package com.amazingtd5800;

/**
 * Type of upgrade spot under a tower: none, reload (takes part of the reload time away), damage (extra
 * damage) or range (extra radius). Both the sheet frame and its icon come from the type.
 * Original: on (on.a = no spot, on.b/c/d); frame via fy.b, icon via fy.a.
 */
enum Spot {

    NONE, RELOAD, DAMAGE, RANGE;

    /** Frame in EnhancedSpots.png and EnhancedSpotIcons.png: RELOAD 0, DAMAGE 3, the rest 2. Original: fy.b, fy.a. */
    int frame() {
        return this == RELOAD ? 0 : (this == DAMAGE ? 3 : 2);
    }
}
