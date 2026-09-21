package com.amazingtd5800;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;

/**
 * Screen manager: switches between the main menu, level select, instructions, options, high scores and the
 * game screen. The game screen outlives the switches, so choosing a level opens its towers there.
 * Original: be.
 */
public final class Game {

    private final Activity activity;
    private Settings settings;
    private MainMenuScreen mainMenu;
    private LevelSelectScreen levelSelect;
    private InstructionsScreen instructions;
    private OptionsScreen options;
    private HighScoresScreen highScores;
    /**
     * The game screen; it survives screen switches, so level select can hand it the towers unlocked by
     * levels cleared without deaths.
     * Original: be.h.
     */
    private GameScreen screen;
    private String version;

    public Game(Activity activity) {
        this.activity = activity;
    }

    public void start() {
        settings = new Settings(activity);
        Assets.get().load(activity);
        // The sound flag comes from settings; shots are loaded once. Original: ru.a(settings.b()), ru.b().
        Sounds.get().setEnabled(settings.soundEnabled());
        Sounds.get().load(activity);
        showMainMenu();
    }

    /** Activity context, which screens need for the View constructor. */
    public Context context() {
        return activity;
    }

    /** Settings and cleared levels. Original: be.j. */
    public Settings settings() {
        return settings;
    }

    public void showMainMenu() {
        if (mainMenu == null) {
            mainMenu = new MainMenuScreen(this);
        }
        levelSelect = null;
        instructions = null;
        options = null;
        highScores = null;
        activity.setContentView(mainMenu);
    }

    /** Level select screen, re-reading progress on every open. Original: be.e(). */
    public void showLevelSelect() {
        if (levelSelect == null) {
            levelSelect = new LevelSelectScreen(this);
        }
        levelSelect.refresh();
        activity.setContentView(levelSelect);
    }

    /** Instructions screen, opened on page zero. Original: be.f(). */
    public void showInstructions() {
        if (instructions == null) {
            instructions = new InstructionsScreen(this);
        }
        activity.setContentView(instructions);
    }

    /** Options screen; button captions are re-read before it shows. Original: be.g(). */
    public void showOptions() {
        if (options == null) {
            options = new OptionsScreen(this);
        }
        options.refresh();
        activity.setContentView(options);
    }

    public void showHighScores() {
        if (highScores == null) {
            highScores = new HighScoresScreen(this);
        }
        activity.setContentView(highScores);
    }

    /** About screen, shown on top of the main menu, which clears the other screens behind it. Original: be.d(). */
    public void showAbout() {
        activity.setContentView(new AboutScreen(this));
    }

    /**
     * The game screen, where level select opens the towers unlocked by levels cleared without deaths.
     * Original: be.u().
     */
    public GameScreen screen() {
        return screen;
    }

    /**
     * Creates the game screen for the chosen level and hands it the towers unlocked by levels cleared
     * without deaths.
     * Original: be.d().
     */
    public void showLevel(int number) {
        screen = new GameScreen(this, activity, number);
        if (levelSelect != null) {
            levelSelect.showTowers();
        }
        activity.setContentView(screen);
    }

    public void exit() {
        activity.finish();
    }

    /** Version name, taken from the MIDlet-Version of the original manifest. Original: AMazingTDMidlet.a. */
    public String version() {
        if (version == null) {
            try {
                version = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                version = "";
            }
        }
        return version;
    }

    /** Vibration pulse, unless the setting switched it off. Original: be.q(). */
    public void vibrate() {
        if (settings.vibrationEnabled()) {
            vibrate(200);
        }
    }

    /**
     * Flash of the original's backlight for this long; on a phone that is the same vibration pulse. The
     * "Vibration" setting does not gate it — in the original the flash never looked at be.r().
     * Original: be.a(int).
     */
    public void vibrate(int millis) {
        Vibrator vibrator = vibrator();
        if (vibrator == null || !vibrator.hasVibrator()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(millis, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(millis);
        }
    }

    private Vibrator vibrator() {
        if (Build.VERSION.SDK_INT >= 31) {
            return activity.getSystemService(VibratorManager.class).getDefaultVibrator();
        }
        return (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
    }
}
