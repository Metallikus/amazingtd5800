package com.amazingtd5800;

import android.graphics.Canvas;

/** Placeholder for the game screen; shows the original's title and a button back to the menu. */
final class PlaceholderScreen extends Screen {

    private final String title;

    PlaceholderScreen(Game game, String title) {
        super(game, game.context());
        this.title = title;
        Assets assets = Assets.get();
        add(new TextButton(this, (width() - assets.redButtonShort().getWidth()) / 2, height() - 40,
                assets.redButtonShort(), 2, "Menu", assets.labelFont(), (x, y) -> game.showMainMenu()));
    }

    @Override
    protected void paint(Canvas canvas) {
        Assets assets = Assets.get();
        drawBitmap(canvas, assets.menuBackground(), 0, 0, Anchor.TOP | Anchor.LEFT);
        assets.labelFont().drawText(canvas, title, width() / 2, 20, Anchor.TOP | Anchor.HCENTER);
        paintWidgets(canvas);
    }
}
