package com.amazingtd5800;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

/** Options screen: difficulty, sound, vibration, tower highlight and clearing high scores. Original: co. */
public final class OptionsScreen extends Screen {

    private static final String QUESTION = "Are you sure you want to delete all high scores?";

    private final Settings settings;
    private final TextButton difficulty;
    private final TextButton sound;
    private final TextButton vibration;
    private final TextButton towerAdjust;

    OptionsScreen(Game game) {
        super(game, game.context());
        this.settings = game.settings();
        Assets assets = Assets.get();
        GameFont labels = assets.labelFont();
        Bitmap blue = assets.blueButton();
        difficulty = new TextButton(this, 100, 100, blue, 2, settings.difficultyName(), labels,
                (x, y) -> {
                    settings.setDifficulty((settings.difficulty() + 1) % 3);
                    refresh();
                });
        sound = new TextButton(this, 100, 150, blue, 2, onOff(settings.soundEnabled(), "Sound"), labels,
                (x, y) -> {
                    // The "Sound" setting silences shots, not the next settings call. Original: be.b(boolean).
                    settings.setSoundEnabled(!settings.soundEnabled());
                    Sounds.get().setEnabled(settings.soundEnabled());
                    refresh();
                });
        vibration = new TextButton(this, 100, 200, blue, 2, onOff(settings.vibrationEnabled(), "Vibration"), labels,
                (x, y) -> {
                    settings.setVibrationEnabled(!settings.vibrationEnabled());
                    refresh();
                });
        towerAdjust = new TextButton(this, 100, 250, blue, 2,
                onOff(settings.towerAdjustEnabled(), "Tower adj"), labels,
                (x, y) -> {
                    settings.setTowerAdjustEnabled(!settings.towerAdjustEnabled());
                    refresh();
                });
        add(difficulty);
        add(sound);
        add(vibration);
        add(towerAdjust);
        add(new TextButton(this, 100, 300, blue, 2, "Clear scores", labels,
                (x, y) -> showDialog(new ConfirmDialog(OptionsScreen.this, settings, QUESTION,
                        settings::clearLevels, this::closeDialog))));
        add(new TextButton(this, 100, 350, assets.redButton(), 2, "Menu", labels, (x, y) -> game.showMainMenu()));
    }

    /** Button captions always reflect the current settings. Original: co.a()/d()/c()/b(). */
    void refresh() {
        difficulty.setLabel(settings.difficultyName());
        sound.setLabel(onOff(settings.soundEnabled(), "Sound"));
        vibration.setLabel(onOff(settings.vibrationEnabled(), "Vibration"));
        towerAdjust.setLabel(onOff(settings.towerAdjustEnabled(), "Tower adj"));
    }

    private static String onOff(boolean enabled, String name) {
        return name + ": " + (enabled ? "On" : "Off");
    }

    @Override
    protected void paint(Canvas canvas) {
        drawBitmap(canvas, Assets.get().menuBackground(), 0, 0, Anchor.TOP | Anchor.LEFT);
        Assets.get().labelFont().drawText(canvas, "Options", width() / 2, 20, Anchor.TOP | Anchor.HCENTER,
                Color.WHITE);
        paintWidgets(canvas);
    }
}
