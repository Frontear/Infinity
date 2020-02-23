package com.github.frontear.framework.utils.rand;

import com.google.common.base.Preconditions;
import java.lang.reflect.Array;
import java.util.concurrent.ThreadLocalRandom;
import lombok.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Random {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public String nextString(final int length, final boolean rand_case) {
        Preconditions
            .checkArgument(length > 0, "Length of random string must not be less than 0 [is %d]",
                length);

        val string = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            string.append(nextChar(rand_case && nextBoolean()));
        }

        return string.toString();
    }

    public char nextChar(final boolean upper) {
        val c = chars[nextIndex(chars)];

        return upper ? Character.toUpperCase(c) : c;
    }

    public boolean nextBoolean() {
        return nextInt(0, 1) != 0;
    }

    public int nextIndex(@NonNull final Object array) {
        Preconditions.checkArgument(array.getClass().isArray(), "Invalid array [is %s]",
            array.getSimpleName());

        return nextInt(0, Array.getLength(array) - 1);
    }

    public int nextInt(final int min, final int max) {
        Preconditions
            .checkArgument((max > min) && (max != Integer.MAX_VALUE)); // we don't want an overflow

        return random.nextInt(min, max + 1);
    }

    public double nextDouble(final double min, final double max) {
        Preconditions
            .checkArgument((max > min) && (max != Double.MAX_VALUE)); // we don't want an overflow

        return random.nextDouble(min, max + 1);
    }
}
