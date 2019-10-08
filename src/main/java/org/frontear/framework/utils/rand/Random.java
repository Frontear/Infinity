package org.frontear.framework.utils.rand;

import com.google.common.base.Preconditions;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.lang.reflect.Array;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass public class Random {
	private final ThreadLocalRandom random = ThreadLocalRandom.current();
	private final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();

	public String nextString(final int length, final boolean rand_case) {
		val string = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			string.append(nextChar(rand_case && nextBoolean()));
		}

		return string.toString();
	}

	public char nextChar(final boolean upper) {
		val c = chars[nextInt(chars)];

		return upper ? Character.toUpperCase(c) : c;
	}

	public boolean nextBoolean() {
		return nextInt(0, 1) == 1;
	}

	public int nextInt(final Object array) {
		Preconditions.checkArgument(array.getClass().isArray());

		return nextInt(0, Array.getLength(array) - 1);
	}

	public int nextInt(final int min, final int max) {
		Preconditions.checkArgument((max > min) && (max != Integer.MAX_VALUE)); // we don't want an overflow

		return random.nextInt(min, max + 1);
	}
}
