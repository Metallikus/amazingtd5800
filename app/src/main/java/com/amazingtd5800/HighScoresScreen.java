package com.amazingtd5800;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

/** High scores screen: the current difficulty and 19 levels in two columns. Original: cp. */
public final class HighScoresScreen extends Screen {

    /** Number of levels. Original: ca.e. */
    private static final int LEVEL_COUNT = 19;

    private final Settings settings;

    HighScoresScreen(Game game) {
        super(game, game.context());
        this.settings = game.settings();
        Assets assets = Assets.get();
        add(new TextButton(this, (width() - assets.redButtonShort().getWidth()) / 2, height() - 40,
                assets.redButtonShort(), 2, "Menu", assets.labelFont(), (x, y) -> game.showMainMenu()));
    }

    @Override
    protected void paint(Canvas canvas) {
        Assets assets = Assets.get();
        GameFont labels = assets.labelFont();
        GameFont text = assets.textFont();
        drawBitmap(canvas, assets.menuBackground(), 0, 0, Anchor.TOP | Anchor.LEFT);
        labels.drawText(canvas, "High scores", width() / 2, 20, Anchor.TOP | Anchor.HCENTER, Color.WHITE);
        text.drawText(canvas, "Difficulty", 10, 10, Anchor.TOP | Anchor.LEFT, Color.WHITE);
        text.drawText(canvas, settings.difficultyName(), 10, 10 + text.height() + 2,
                Anchor.TOP | Anchor.LEFT, Color.WHITE);

        Bitmap perfect = assets.levelPerfect();
        int column = 32;
        int row = 0;
        for (int number = 1; number <= LEVEL_COUNT; number++) {
            if (number == 1 + LEVEL_COUNT / 2 + LEVEL_COUNT % 2) {
                column = 206;
                row = 0;
            }
            int y = 70 + row * 36;
            LevelRecord record = settings.level(number);
            if (record != null) {
                if (record.cleared) {
                    drawBitmap(canvas, perfect, column + 4 - perfect.getWidth(), y + 5, Anchor.TOP | Anchor.LEFT);
                }
                labels.drawText(canvas, "Level " + record.number + ": " + record.score, column + 6, y + 8,
                        Anchor.TOP | Anchor.LEFT, Color.WHITE);
            } else {
                labels.drawText(canvas, "Level " + number + ": 0", column + 6, y + 8,
                        Anchor.TOP | Anchor.LEFT, Color.WHITE);
            }
            row++;
        }
        paintWidgets(canvas);
    }
}
