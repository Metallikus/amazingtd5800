package com.amazingtd5800;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads every bitmap and font the screens draw from, decoding each asset from assets/ on first use.
 * Original: cj.
 */
public final class Assets {

    private static final String TAG = "AMazingTD";

    private static Assets instance;

    /**
     * Tower sheets in instruction-page order; the tower frame is cropped at x = 168, where setFrame(7) took it.
     * Original: di.
     */
    private static final String[] TOWERS = {
            "images/towers/ArrowTower.png", "images/towers/SlowTower.png", "images/towers/BombTower.png",
            "images/towers/RapidTower.png", "images/towers/ForceFieldTower.png", "images/towers/LaserTower.png",
            "images/towers/DetectorTower.png", "images/towers/MoneyTower.png", "images/towers/SniperTower.png",
            "images/towers/TrackingTower.png", "images/towers/MissileTower.png",
    };

    private Bitmap menuBackground;
    private Bitmap logo;
    private Bitmap blueButton;
    private Bitmap redButton;
    private Bitmap redButtonShort;
    private Bitmap levelUnlocked;
    private Bitmap levelDisabled;
    private Bitmap levelSelected;
    private Bitmap levelPerfect;
    private Bitmap star1b;
    private Bitmap star2b;
    private Bitmap star3b;
    private Bitmap star4b;
    private Bitmap arrows;
    private Bitmap enhancedSpots;
    private Bitmap enhancedSpotIcons;
    private Context context;
    private Bitmap flare;
    private Bitmap explosion;
    private Bitmap coin;
    private Bitmap levelBackground;
    private int levelBackgroundIndex;
    private final Map<String, Bitmap> enemies = new HashMap<>();
    private final Bitmap[] towers = new Bitmap[TOWERS.length];
    /** Whole tower sheets, one 24x24 frame per level number. Original: cj. */
    private final Bitmap[] towerSheets = new Bitmap[TOWERS.length];
    /** Glyphs used to come from a sheet; now they are TTFs of the same height from assets/fonts. Original: dt/du. */
    private static final String PIXEL_FONT = "fonts/Pixeloid Font 0.5/PixeloidSans.ttf";
    private static final String LIBERATION_SANS = "fonts/Liberation Sans/LiberationSans-Regular.ttf";

    /** Pixel font for HUD counters and the main-menu footer. Original: dt (j, ce, am). */
    private TrueTypeFont pixelFont;
    /** Text font for the remaining screens and for damage labels. Original: du (di, cc, cp, cs). */
    private TrueTypeFont textFont;
    /** 14px label font for button, panel and battlefield captions. Original: cj.a().A() = dr(Font.getFont(0, 0, 8)). */
    private TrueTypeFont labelFont;
    /** 17px default screen font (MEDIUM): screen titles and node numbers. Original: an.paint, bs, bd. */
    private TrueTypeFont titleFont;

    public static Assets get() {
        if (instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    public void load(Context context) {
        if (menuBackground != null) {
            return;
        }
        menuBackground = decode(context, "images/MenuBackground.jpg");
        logo = decode(context, "images/AmazingTD.png");
        blueButton = decode(context, "images/BlueButton.png");
        redButton = decode(context, "images/RedButton.png");
        redButtonShort = decode(context, "images/RedButtonShort.png");
        pixelFont = new TrueTypeFont(context, PIXEL_FONT, 13, 1);
        textFont = new TrueTypeFont(context, LIBERATION_SANS, 16, 4);
        labelFont = new TrueTypeFont(context, LIBERATION_SANS, 14, 4);
        titleFont = new TrueTypeFont(context, LIBERATION_SANS, 17, 4);
        levelUnlocked = decode(context, "images/Level_unlocked_32.png");
        levelDisabled = decode(context, "images/Level_disabled_32.png");
        levelSelected = decode(context, "images/Level_selected_32.png");
        levelPerfect = decode(context, "images/Level_perfect_32.png");
        star3b = decode(context, "images/Star-3b.png");
        star2b = decode(context, "images/Star-2b.png");
        star4b = decode(context, "images/Star-4b.png");
        star1b = decode(context, "images/Star-1b.png");
        arrows = decode(context, "images/Arrows.png");
        enhancedSpots = decode(context, "images/EnhancedSpots.png");
        enhancedSpotIcons = decode(context, "images/EnhancedSpotIcons.png");
        for (int i = 0; i < TOWERS.length; i++) {
            towerSheets[i] = decode(context, TOWERS[i]);
            towers[i] = crop(towerSheets[i], 168, 24);
        }
        this.context = context;
        flare = decode(context, "images/Flares2.png");
        explosion = decode(context, "images/Explosion4a.png");
        coin = decode(context, "images/coin.png");
    }

    /** Arrows.png frame x: next screen, previous screen, down and up arrows. Original: cj.ad()/ae()/af()/ag(). */
    public Bitmap arrow(int x) {
        return crop(arrows, x, 32);
    }

    /** EnhancedSpotIcons frame 0/48/72. Original: cj.aj()/al()/ak(). */
    public Bitmap enhancedSpotIcon(int x) {
        return crop(enhancedSpotIcons, x, 24);
    }

    /** EnhancedSpots frame 0/48/72. Original: di.l()/m()/n(). */
    public Bitmap enhancedSpot(int x) {
        return crop(enhancedSpots, x, 24);
    }

    /** Tower sheet, its frame selected by the level number. Original: cj.a(). */
    public Bitmap towerSheet(int index) {
        return towerSheets[index];
    }

    /** Enemy sprite sheet, loaded by file name. Original: cj.c()..i(). */
    public Bitmap enemy(String name) {
        return enemies.computeIfAbsent(name, file -> decode(context, "images/" + file));
    }

    /** The coin the player has to tap. Original: cj.x(). */
    public Bitmap coin() {
        return coin;
    }

    /** Level background; level 1 is LevMoon.jpg. Original: cj.a(int). */
    public Bitmap levelBackground(int level) {
        if (levelBackground == null || levelBackgroundIndex != level) {
            levelBackground = decode(context, "images/" + new String[]{"", "LevMoon", "LevConcrete", "LevMars", "LevDesert",
                    "LevRubble", "MenuBackground"}[level] + ".jpg");
            levelBackgroundIndex = level;
        }
        return levelBackground;
    }

    /** Projectile flares (Flares2.png). Original: cj.w(). */
    public Bitmap flare() {
        return flare;
    }

    /** Explosion frames (Explosion4a.png). Original: cj.y(). */
    public Bitmap explosion() {
        return explosion;
    }

    /** Tower sprite: the 24x24 frame the original showed with setFrame(7). Original: di.f. */
    public Bitmap tower(int index) {
        return towers[index];
    }

    public Bitmap levelUnlocked() {
        return levelUnlocked;
    }

    public Bitmap levelDisabled() {
        return levelDisabled;
    }

    public Bitmap levelSelected() {
        return levelSelected;
    }

    public Bitmap levelPerfect() {
        return levelPerfect;
    }

    public Bitmap star1b() {
        return star1b;
    }

    public Bitmap star2b() {
        return star2b;
    }

    public Bitmap star3b() {
        return star3b;
    }

    public Bitmap star4b() {
        return star4b;
    }

    public Bitmap menuBackground() {
        return menuBackground;
    }

    public Bitmap logo() {
        return logo;
    }

    public Bitmap blueButton() {
        return blueButton;
    }

    public Bitmap redButton() {
        return redButton;
    }

    public Bitmap redButtonShort() {
        return redButtonShort;
    }

    /** Pixel font for the version and copyright captions (Pixeloid Sans, was a 13px sheet). Original: cj.y(). */
    public GameFont pixelFont() {
        return pixelFont;
    }

    /** Text font for the remaining screens (Liberation Sans, was a 16px sheet). Original: cj.z(). */
    public GameFont textFont() {
        return textFont;
    }

    /** 14px label font for panels, buttons and the battlefield. Original: cj.A() = dr(Font.getFont(0, 0, 8)). */
    public GameFont labelFont() {
        return labelFont;
    }

    /** 17px default screen font (MEDIUM): screen titles and node numbers. Original: an.paint, bs, bd. */
    public GameFont titleFont() {
        return titleFont;
    }

    private Bitmap decode(Context context, String path) {
        try (InputStream stream = context.getAssets().open(path)) {
            return BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            Log.e(TAG, "failed to load " + path, e);
            return null;
        }
    }

    /** Square frame cropped out of a sheet. Original: Image.createImage(image, x, 0, size, size). */
    private static Bitmap crop(Bitmap sheet, int x, int size) {
        return Bitmap.createBitmap(sheet, x, 0, size, size);
    }
}
