package com.amazingtd5800;

import java.util.Random;

/**
 * Game randomness on the same generator as the original, quirk included: the float range takes the remainder
 * of the spread instead of scaling it.
 * Original: AMazingTDMidlet.b and its static a() overloads.
 */
final class Randoms {

    private static final Random RANDOM = new Random();

    private Randoms() {
    }

    /** Random value from 0 up to max. Original: AMazingTDMidlet.a(int). */
    static int pick(int max) {
        return (RANDOM.nextInt() & Integer.MAX_VALUE) % max;
    }

    /** Random value inside a range. Original: AMazingTDMidlet.a(int, int). */
    static int between(int from, int to) {
        return from + (RANDOM.nextInt() & Integer.MAX_VALUE) % (to - from);
    }

    /** Raw float from the shared generator. Original: AMazingTDMidlet.b. */
    static float nextFloat() {
        return RANDOM.nextFloat();
    }

    /** Random float in a range, keeping the original remainder quirk. Original: AMazingTDMidlet.a(float, float). */
    static float between(float from, float to) {
        return from + RANDOM.nextFloat() % (to - from);
    }
}
