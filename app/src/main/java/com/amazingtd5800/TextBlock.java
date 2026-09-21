package com.amazingtd5800;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

/** Word wrap and line-by-line drawing, from the original's text-block statics. Original: f. */
public final class TextBlock {

    /** The original wraps on the system line separator (cj.g = System.getProperty("line.separator")). */
    private static final String SEPARATOR = System.lineSeparator();

    private TextBlock() {
    }

    /** Wraps on spaces and on newlines. Original: f.a(String, tm, int). */
    public static List<String> wrap(String text, GameFont font, int width) {
        List<String> lines = new ArrayList<>();
        if (text == null) {
            return lines;
        }
        int index = 0;
        String line = "";
        boolean done = false;
        while (!done) {
            int space = text.indexOf(' ', index);
            int separator = text.indexOf(SEPARATOR, index);
            boolean separatorFirst = space > separator && separator != -1;
            int cut = separatorFirst ? separator : space;
            String chunk;
            if (cut == -1) {
                chunk = separator == -1 ? text.substring(index) : text.substring(index, separator);
                done = true;
            } else {
                chunk = text.substring(index, cut);
            }
            if (font.stringWidth(line) + font.stringWidth(chunk) + 1 < width) {
                line += line.isEmpty() ? "" : " ";
                line += chunk;
                if (separatorFirst) {
                    lines.add(line);
                    line = "";
                }
            } else {
                lines.add(line);
                line = chunk;
            }
            if (done) {
                lines.add(line);
            }
            index = separatorFirst ? separator + SEPARATOR.length() : cut + 1;
        }
        return lines;
    }

    /** Lines are drawn top to bottom, stepping by font.lineHeight(). Original: f.a(Graphics, Vector, tm, int, int). */
    public static void draw(Canvas canvas, List<String> lines, GameFont font, int x, int y, int color) {
        for (String line : lines) {
            font.drawText(canvas, line, x, y, Anchor.TOP | Anchor.LEFT, color);
            y += font.lineHeight();
        }
    }
}
