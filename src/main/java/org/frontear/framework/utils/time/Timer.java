package org.frontear.framework.utils.time;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE) public final class Timer {
	long nanoseconds;

	/**
	 * Creates a new Timer, which calculates the passage of time using {@link System#nanoTime()}
	 */
	public Timer() {
		reset();
	}

	/**
	 * Resets the internal clock to the most recent value of {@link System#nanoTime()}
	 */
	public void reset() {
		this.nanoseconds = System.nanoTime();
	}

	/**
	 * Determines how much time has passed by making use of {@link Timer#getElapsed(TimeUnit)}
	 *
	 * @param unit The unit of time we want to check
	 * @param time The duration, bound to the unit of time specified
	 *
	 * @return Whether that amount has elapsed yet
	 */
	public boolean hasElapsed(TimeUnit unit, long time) {
		return getElapsed(unit) >= time;
	}

	/**
	 * Attempts to calculate the amount of time that has passed by subtracting the internal nanoTime to the most recent
	 * {@link System#nanoTime()}
	 *
	 * @param unit The unit of time we wish to evaluate to
	 *
	 * @return The duration, specific to the unit of time
	 */
	public long getElapsed(TimeUnit unit) {
		val time = (System.nanoTime() - nanoseconds);
		switch (unit) {
			case NANOSECONDS:
				return TimeUnit.NANOSECONDS.toNanos(time);
			case MICROSECONDS:
				return TimeUnit.NANOSECONDS.toMicros(time);
			case MILLISECONDS:
				return TimeUnit.NANOSECONDS.toMillis(time);
			case SECONDS:
				return TimeUnit.NANOSECONDS.toSeconds(time);
			case MINUTES:
				return TimeUnit.NANOSECONDS.toMinutes(time);
			case HOURS:
				return TimeUnit.NANOSECONDS.toHours(time);
			case DAYS:
				return TimeUnit.NANOSECONDS.toDays(time);
			default:
				throw new UnsupportedOperationException("Unknown TimeUnit ${unit.getSimpleName()}");
		}
	}

	/**
	 * Formats the time to a <b>HH:mm:ss:SS</b>
	 *
	 * @return A formatted time string
	 */
	@Override public String toString() {
		return String
				.format("%02d:%02d:%02d.%03d", getElapsed(TimeUnit.HOURS) % 24, getElapsed(TimeUnit.MINUTES) % 60, getElapsed(TimeUnit.SECONDS) % 60, getElapsed(TimeUnit.MILLISECONDS) % 1000);
	}
}
