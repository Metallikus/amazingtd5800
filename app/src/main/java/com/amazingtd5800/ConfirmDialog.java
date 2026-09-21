package com.amazingtd5800;

import android.graphics.Color;

/** Confirmation panel: a question line and Yes/No buttons, each on its own line. Original: c. */
final class ConfirmDialog extends TextPanel {

    ConfirmDialog(Screen screen, Settings settings, String question, Runnable onYes, Runnable onClose) {
        super(screen, settings, settings.panelColor(), 0, 0, 200, screen.height(), true, false);
        GameFont font = Assets.get().titleFont();
        addLines(font, question, Color.WHITE, Color.WHITE, true);
        addRow(font, "Yes", true, true, () -> {
            onYes.run();
            onClose.run();
        });
        addRow(font, "No", true, true, onClose);
    }
}
