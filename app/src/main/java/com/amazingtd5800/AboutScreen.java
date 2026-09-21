package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.List;

/** "About" screen: the credits text and a button back to the menu. Original: cc. */
public final class AboutScreen extends Screen {

    private static final String CREDITS = "This is the Android remake of «Amazing TD» by Pavel Chalov — a free-to-play tower defense game for the "
            + "Nokia 5800 Xpress Music, originally created by Johan Krüger in 2010" + System.lineSeparator() + System.lineSeparator()
            + "-= Credits =- " + System.lineSeparator() + System.lineSeparator()
            + "- Original symbian game developer - " + System.lineSeparator() + "Johan Krüger" + System.lineSeparator() + System.lineSeparator()
            + "- Testing and Balancing original game - " + System.lineSeparator() + "Zuul" + System.lineSeparator()
            + "XRC Kingkoning" + System.lineSeparator() + System.lineSeparator()
            + "- Graphics - " + System.lineSeparator() + "Zuul" + System.lineSeparator() + System.lineSeparator()
            + "- Sound effects - " + System.lineSeparator() + "James Tubbritt, Fxhome.com " + System.lineSeparator()
            + "Brettsta, Fxhome.com" + System.lineSeparator() + System.lineSeparator()
            + "- Level design - " + System.lineSeparator() + "15 - jinsk8er" + System.lineSeparator()
            + "16 - XRC Kingkoning" + System.lineSeparator() + "17 - Zuul" + System.lineSeparator();

    private final List<String> credits;

    AboutScreen(Game game) {
        super(game, game.context());
        credits = TextBlock.wrap(CREDITS, Assets.get().textFont(), width() - 20);
        Assets assets = Assets.get();
        add(new TextButton(this, (width() - assets.redButtonShort().getWidth()) / 2, height() - 40,
                assets.redButtonShort(), 2, "Menu", assets.labelFont(), (x, y) -> game.showMainMenu()));
    }

    @Override
    protected void paint(Canvas canvas) {
        Assets assets = Assets.get();
        drawBitmap(canvas, assets.menuBackground(), 0, 0, Anchor.TOP | Anchor.LEFT);
        assets.labelFont().drawText(canvas, "About", width() / 2, 20, Anchor.TOP | Anchor.HCENTER, Color.WHITE);
        TextBlock.draw(canvas, credits, assets.textFont(), 10, 70, Color.WHITE);
        paintWidgets(canvas);
    }
}
