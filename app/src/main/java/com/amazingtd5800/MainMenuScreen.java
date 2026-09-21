package com.amazingtd5800;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

/** Main menu with the original's six buttons. Original: am. */
public final class MainMenuScreen extends Screen {

    MainMenuScreen(Game game) {
        super(game, game.context());
        Assets assets = Assets.get();
        GameFont labels = assets.labelFont();
        Bitmap blueButton = assets.blueButton();
        add(new TextButton(this, 100, 100, blueButton, 2, "Start game", labels,
                (x, y) -> game.showLevelSelect()));
        add(new TextButton(this, 100, 150, blueButton, 2, "Options", labels,
                (x, y) -> game.showOptions()));
        add(new TextButton(this, 100, 200, blueButton, 2, "High scores", labels,
                (x, y) -> game.showHighScores()));
        add(new TextButton(this, 100, 250, blueButton, 2, "Instructions", labels,
                (x, y) -> game.showInstructions()));
        add(new TextButton(this, 100, 300, blueButton, 2, "About", labels,
                (x, y) -> game.showAbout()));
        add(new TextButton(this, 100, 350, assets.redButton(), 2, "Quit", labels,
                (x, y) -> game.exit()));
    }

    @Override
    protected void paint(Canvas canvas) {
        Assets assets = Assets.get();
        drawBitmap(canvas, assets.menuBackground(), 0, 0, Anchor.TOP | Anchor.LEFT);
        drawBitmap(canvas, assets.logo(), width() / 2, 20, Anchor.TOP | Anchor.HCENTER);
        GameFont footer = assets.pixelFont();
        footer.drawText(canvas, "v" + game().version(), 2, height() - 2, Anchor.BOTTOM | Anchor.LEFT, Color.WHITE);
        footer.drawText(canvas, "Symbian version: © 2010, Johan Krüger", width() - 2, height() - 22,
                Anchor.BOTTOM | Anchor.RIGHT, Color.WHITE);
        footer.drawText(canvas, "Android remake: © 2026, Pavel Chalov", width() - 2, height() - 2,
                Anchor.BOTTOM | Anchor.RIGHT, Color.WHITE);
        paintWidgets(canvas);
    }
}
