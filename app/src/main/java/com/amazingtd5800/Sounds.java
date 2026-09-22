package com.amazingtd5800;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaMetadataRetriever;
import android.media.SoundPool;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

/**
 * Eleven shots and a coin. Sound plays only when it is not muted (ru.b) and when the previous shot has
 * finished playing (ru.c — set by start(), cleared by sc at endOfMedia), so shots never overlap.
 * Original: ru, sc.
 */
final class Sounds {

    private static final String TAG = "Amazing TD";
    /** Load order matches the order of the original's fields. Original: ru.d..n. */
    private static final String[] SOUNDS = {"fireArrow", "fireBullet", "fireLaser", "forceField", "bombExplosion",
            "fireSniper", "fireDetector", "fireSlow", "fireMoney", "fireTracking", "coinBonus"};

    private static Sounds instance;

    private final int[] sound = new int[SOUNDS.length];
    /** Milliseconds each shot takes to play; the others stay silent while it plays. Original: ru.d..n. */
    private final long[] duration = new long[SOUNDS.length];
    /** Sound is muted. Original: ru.b. */
    private boolean muted = true;
    /**
     * A sound is already playing; sc clears the flag at endOfMedia, so the rest stay silent until then.
     * Original: ru.c.
     */
    private long playingUntil;
    private SoundPool pool;

    static Sounds get() {
        if (instance == null) {
            instance = new Sounds();
        }
        return instance;
    }

    /** Shots are loaded once, before the game screen opens. Original: ru.b(). */
    void load(Context context) {
        if (pool != null) {
            return;
        }
        SoundPool.Builder builder = new SoundPool.Builder().setMaxStreams(1);
        if (Build.VERSION.SDK_INT >= 26) {
            builder.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build());
        }
        pool = builder.build();
        for (int i = 0; i < SOUNDS.length; i++) {
            try (android.content.res.AssetFileDescriptor file =
                         context.getAssets().openFd("sounds/" + SOUNDS[i] + ".wav")) {
                sound[i] = pool.load(file, 1);
                // endOfMedia ends "playing" from the track's own length: SoundPool has no end-of-sound callback
                // below API 33, and shots are shorter than 557 ms. Original: sc.playerUpdate.
                MediaMetadataRetriever header = new MediaMetadataRetriever();
                header.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                duration[i] = Long.parseLong(
                        header.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
                header.release();
            } catch (Exception e) {
                Log.e(TAG, "failed to load sound " + SOUNDS[i], e);
            }
        }
    }

    /** The "Sound" setting unmutes the sound when it is switched on. Original: ru.a(boolean). */
    void setEnabled(boolean enabled) {
        muted = !enabled;
    }

    /** Whether sound is on. Original: ru.c(). */
    boolean enabled() {
        return !muted;
    }

    /** Plays when sound is on and the previous shot has finished (state == PREFETCHED). Original: ru.a(Player). */
    private void play(int index) {
        long now = SystemClock.uptimeMillis();
        if (muted || pool == null || sound[index] == 0 || now < playingUntil) {
            return;
        }
        playingUntil = now + duration[index];
        pool.play(sound[index], 1.0f, 1.0f, 1, 0, 1.0f);
    }

    /** Autobow shot. Original: ru.e(). */
    void fireArrow() {
        play(0);
    }

    /** Chain gun burst. Original: ru.f(). */
    void fireBullet() {
        play(1);
    }

    /** Pulsed laser. Original: ru.d(). */
    void fireLaser() {
        play(2);
    }

    /** Force field hits the enemy through everything. Original: ru.h(). */
    void forceField() {
        play(3);
    }

    /** Mine and rocket exploded. Original: ru.g(). */
    void bombExplosion() {
        play(4);
    }

    /** Sniper and rocket in flight. Original: ru.i(). */
    void fireSniper() {
        play(5);
    }

    /** Detector highlighted an enemy. Original: ru.k(). */
    void fireDetector() {
        play(6);
    }

    /** Slow tower's arrow. Original: ru.j(). */
    void fireSlow() {
        play(7);
    }

    /** Money tower hit an enemy. Original: ru.l(). */
    void fireMoney() {
        play(8);
    }

    /** Tracking laser holds its target. Original: ru.m(). */
    void fireTracking() {
        play(9);
    }

    /** The coin was caught. Original: ru.n(). */
    void coinBonus() {
        play(10);
    }
}
