package com.amazingtd5800;

/**
 * Widens a 24-bit RGB colour into the ARGB int Android paints with, so a bare 0xRRGGBB is not fully
 * transparent.
 * Original: javax.microedition.lcdui.Graphics.setColor(int).
 */
final class Rgb {

    static int color(int rgb) {
        return rgb | 0xFF000000;
    }

    private Rgb() {
    }
}
