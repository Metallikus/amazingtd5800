package com.amazingtd5800;

import android.content.Context;
import android.graphics.Color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Settings and cleared levels live in files named after the original's RecordStores, in the same binary format
 * (sound, vibration, tower adjust, difficulty; a level: number, score, cleared).
 * Original: cd, bz.
 */
public final class Settings {

    private static final String SETTINGS = "ATDsettings";
    private static final String[] LEVEL_STORES = {"clearedLevelsEasy", "clearedLevels", "clearedLevelsHard"};

    /** The original's colours (cj.a..f) that ca.a(difficulty) selects; screens paint borders and captions with them. */
    private static final int[][] COLORS = {
            {34816, 612617, 0x46FF46, 613129, 477959, 16384},
            {153, 0x4646FF, 65535, 0x4646FF, 2631860, 1052804},
            {0x990000, 5377024, 0xFF4646, 8654864, 6555656, 0x400000},
    };

    private final Context context;
    private final List<LevelRecord> levels = new ArrayList<>();
    private boolean soundEnabled = true;
    private boolean vibrationEnabled = true;
    private boolean towerAdjustEnabled = true;
    private int difficulty = 1;

    public Settings(Context context) {
        this.context = context;
        load();
    }

    /** Re-reads settings and the level list of the current difficulty. Original: cd.a(). */
    public final void load() {
        File settings = new File(context.getFilesDir(), SETTINGS);
        if (settings.exists()) {
            try (DataInputStream in = new DataInputStream(new FileInputStream(settings))) {
                soundEnabled = in.readBoolean();
                vibrationEnabled = in.readBoolean();
                towerAdjustEnabled = in.readBoolean();
                difficulty = in.readInt();
            } catch (IOException e) {
                throw new IllegalStateException(settings + " is corrupted", e);
            }
        }
        loadLevels();
    }

    /**
     * Switching difficulty deletes the previous difficulty's file entirely, as the original did
     * (RecordStore.deleteRecordStore before reopening), then re-reads the new one.
     * Original: cd.b().
     */
    public final void setDifficulty(int difficulty) {
        levelStore().delete();
        this.difficulty = difficulty;
        save();
        loadLevels();
    }

    /** Deletes the current difficulty's level list and re-reads it. Original: cd.b(). */
    public final void clearLevels() {
        levelStore().delete();
        loadLevels();
    }

    public final List<LevelRecord> levels() {
        return levels;
    }

    public final LevelRecord level(int number) {
        int index = indexOf(number);
        return index < 0 ? null : levels.get(index);
    }

    private int indexOf(int number) {
        for (int i = 0; i < levels.size(); i++) {
            if (levels.get(i).number == number) {
                return i;
            }
        }
        return -1;
    }

    public final boolean soundEnabled() {
        return soundEnabled;
    }

    public final void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
        save();
    }

    public final boolean vibrationEnabled() {
        return vibrationEnabled;
    }

    public final void setVibrationEnabled(boolean vibrationEnabled) {
        this.vibrationEnabled = vibrationEnabled;
        save();
    }

    public final boolean towerAdjustEnabled() {
        return towerAdjustEnabled;
    }

    public final void setTowerAdjustEnabled(boolean towerAdjustEnabled) {
        this.towerAdjustEnabled = towerAdjustEnabled;
        save();
    }

    public final int difficulty() {
        return difficulty;
    }

    public final String difficultyName() {
        return new String[]{"Easy", "Normal", "Hard"}[difficulty];
    }

    /** Filled border colour (cj.d). */
    public final int panelColor() {
        return Color.rgb(color(3) >> 16 & 0xFF, color(3) >> 8 & 0xFF, color(3) & 0xFF);
    }

    /** Pressed button caption colour (cj.c). */
    public final int pressedLabelColor() {
        return Color.rgb(color(2) >> 16 & 0xFF, color(2) >> 8 & 0xFF, color(2) & 0xFF);
    }

    /** Button plate colour (cj.e). */
    public final int buttonColor() {
        return Color.rgb(color(4) >> 16 & 0xFF, color(4) >> 8 & 0xFF, color(4) & 0xFF);
    }

    /** Disabled plate colour (cj.f). */
    public final int disabledColor() {
        return Color.rgb(color(5) >> 16 & 0xFF, color(5) >> 8 & 0xFF, color(5) & 0xFF);
    }

    /** Line colour between unlocked level-select nodes (cj.b). */
    public final int pathColor() {
        return Color.rgb(color(0) >> 16 & 0xFF, color(0) >> 8 & 0xFF, color(0) & 0xFF);
    }

    /** The level's road colour. Original: cj.c. */
    public final int roadColor() {
        return Color.rgb(color(2) >> 16 & 0xFF, color(2) >> 8 & 0xFF, color(2) & 0xFF);
    }

    /** The road's accent layer: cj.b darkened by 40 per layer. Original: ca.a(Graphics, dd, int, int). */
    public final int pathAccent(int layer) {
        return PanelButton.shade(color(0), -layer * 40);
    }

    /** Direction arrows: cj.a darkened by 10 per layer. Original: ca.a(Graphics, dd, int, int). */
    public final int arrowColor(int layer) {
        return PanelButton.shade(color(1), -layer * 10);
    }

    /** Saves the score, the lives lost and the cleared-without-losses flag. Original: cd.a(int, int, int, boolean). */
    public final void setLevel(int number, int score, int livesLost, boolean cleared) {
        int index = indexOf(number);
        if (index >= 0) {
            LevelRecord existing = levels.get(index);
            cleared = cleared || existing.cleared;
            score = Math.max(score, existing.score);
            levels.set(index, new LevelRecord(number, score, cleared, livesLost));
        } else {
            levels.add(new LevelRecord(number, score, cleared, livesLost));
        }
        saveLevels();
    }

    private int color(int index) {
        return COLORS[difficulty][index];
    }

    private File levelStore() {
        return new File(context.getFilesDir(), LEVEL_STORES[difficulty]);
    }

    private void loadLevels() {
        levels.clear();
        File store = levelStore();
        if (!store.exists()) {
            return;
        }
        try (DataInputStream in = new DataInputStream(new FileInputStream(store))) {
            while (in.available() > 0) {
                levels.add(new LevelRecord(in.readInt(), in.readInt(), in.readBoolean(),
                        in.available() > 0 ? in.readInt() : 0));
            }
        } catch (IOException e) {
            throw new IllegalStateException(store + " is corrupted", e);
        }
    }

    /** Writes a whole record: number, score, cleared, lives lost. Original: cd.a(bt). */
    private void saveLevels() {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(levelStore()))) {
            for (LevelRecord level : levels) {
                out.writeInt(level.number);
                out.writeInt(level.score);
                out.writeBoolean(level.cleared);
                out.writeInt(level.livesLost);
            }
        } catch (IOException e) {
            throw new IllegalStateException(levelStore() + " is corrupted", e);
        }
    }

    private void save() {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(context.getFilesDir(), SETTINGS)))) {
            out.writeBoolean(soundEnabled);
            out.writeBoolean(vibrationEnabled);
            out.writeBoolean(towerAdjustEnabled);
            out.writeInt(difficulty);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
