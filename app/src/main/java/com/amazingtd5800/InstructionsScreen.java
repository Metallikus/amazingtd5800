package com.amazingtd5800;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.List;

/**
 * Instructions screen: page 0 is about the game and the enhanced spots, pages 1..11 one tower each — name,
 * icon, price/damage/range/rate and description.
 * Original: amazingtd.di.
 */
public final class InstructionsScreen extends Screen {

    /** Text of page zero, the page di.a() shows by default. */
    private static final String GAME_INSTRUCTIONS = "Defend your phone against the attacking viruses by "
            + "building towers that prevent the viruses from reaching the end of the path. To build a "
            + "tower drag it from the tower buttons in bottom of the screen and drop it close to a path. "
            + "By clicking twice on a tower a menu appears. From the menu the tower can be upgraded or "
            + "sold for 70% of its current value. When enemies are killed you are rewarded with money and "
            + "score. Sometimes a bonus coin is also generated, click on it before it disappears to get "
            + "more money. The attackers come in waves, have different strengths and weaknesses, some of "
            + "them are invisible and some even have shields. How long can you hold them down?";

    private final GameFont font = Assets.get().textFont();
    private final TextButton nextPage;
    private final TextButton previousPage;
    private final TextButton menu;
    private int page;
    private Tower tower;
    private String title = "Game instructions";
    private List<String> lines;

    public InstructionsScreen(Game game) {
        super(game, game.context());
        Assets assets = Assets.get();
        GameFont labels = assets.labelFont();
        this.nextPage = new TextButton(this, width() - 10 - assets.arrow(0).getWidth(), height() - 36,
                assets.arrow(0), 1, null, labels, (x, y) -> page(1));
        this.previousPage = new TextButton(this, 10, height() - 36, assets.arrow(64), 1, null, labels,
                (x, y) -> page(-1));
        this.previousPage.setVisible(false);
        this.menu = new TextButton(this, (width() - assets.redButtonShort().getWidth()) / 2, height() - 40,
                assets.redButtonShort(), 2, "Menu", labels, (x, y) -> game.showMainMenu());
        add(nextPage);
        add(previousPage);
        add(menu);
        refresh();
    }

    /** Page step; at an edge the arrow pointing that way hides and the other one shows. Original: bv, bw. */
    private void page(int step) {
        page += step;
        if (step > 0) {
            if (page == Tower.values().length) {
                nextPage.setEnabled(false);
            }
            previousPage.setEnabled(true);
        } else {
            if (page == 0) {
                previousPage.setEnabled(false);
            }
            nextPage.setEnabled(true);
        }
        refresh();
        invalidate();
    }

    /** The current page's text and the tower to draw for it. Original: di.a(). */
    private void refresh() {
        if (page == 0) {
            tower = null;
            title = "Game instructions";
            lines = TextBlock.wrap(GAME_INSTRUCTIONS, font, width() - 20);
            return;
        }
        tower = Tower.values()[page - 1];
        title = tower.displayName();
        lines = TextBlock.wrap(tower.text(), font, width() - 20);
    }

    @Override
    protected void paint(Canvas canvas) {
        Assets assets = Assets.get();
        drawBitmap(canvas, assets.menuBackground(), 0, 0, Anchor.TOP | Anchor.LEFT);
        if (tower == null) {
            font.drawText(canvas, title, width() / 2, 20, Anchor.TOP | Anchor.HCENTER, Color.WHITE);
            TextBlock.draw(canvas, lines, font, 10, 70, Color.WHITE);
            font.drawText(canvas, "Enhanced tower positions", 10, 420, Anchor.TOP | Anchor.LEFT, Color.WHITE);
            enhancedSpot(canvas, assets, 440, 0, "30% faster fire rate");
            enhancedSpot(canvas, assets, 470, 48, "50% longer range");
            enhancedSpot(canvas, assets, 500, 72, "50% more damage");
        } else {
            font.drawText(canvas, title, 40, 20, Anchor.TOP | Anchor.LEFT, Color.WHITE);
            drawBitmap(canvas, assets.tower(page - 1), 8, 22, Anchor.TOP | Anchor.LEFT);
            int step = font.height() + 3;
            font.drawText(canvas, "Price: $" + tower.price(), 10, 70, Anchor.TOP | Anchor.LEFT, Color.WHITE);
            font.drawText(canvas, "Power: " + tower.power(), 10, 70 + step, Anchor.TOP | Anchor.LEFT, Color.WHITE);
            font.drawText(canvas, "Range: " + tower.range(), 100, 70, Anchor.TOP | Anchor.LEFT, Color.WHITE);
            font.drawText(canvas, "Rate: " + tower.rate(), 100, 70 + step, Anchor.TOP | Anchor.LEFT, Color.WHITE);
            TextBlock.draw(canvas, lines, font, 10, 130, Color.WHITE);
        }
        paintWidgets(canvas);
    }

    /**
     * The spot and its icon at one point, caption to the right.
     * Original: di.a(Graphics, int, int, Image, Image, String).
     */
    private void enhancedSpot(Canvas canvas, Assets assets, int y, int x, String text) {
        drawBitmap(canvas, assets.enhancedSpot(x), 10, y, Anchor.TOP | Anchor.LEFT);
        drawBitmap(canvas, assets.enhancedSpotIcon(x), 10, y, Anchor.TOP | Anchor.LEFT);
        font.drawText(canvas, text, 40, y + 4, Anchor.TOP | Anchor.LEFT, Color.WHITE);
    }
}
