package com.amazingtd5800;

/**
 * Anchor flags for drawing bitmaps and text. Values match the ones the original passed to
 * drawImage/drawString (17 = TOP|HCENTER, 20 = TOP|LEFT, 36 = BOTTOM|LEFT, 40 = BOTTOM|RIGHT).
 * Original: javax.microedition.lcdui.Graphics (microemulator).
 */
public final class Anchor {

    public static final int HCENTER = 1;
    public static final int VCENTER = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int TOP = 16;
    public static final int BOTTOM = 32;

    private Anchor() {
    }
}
