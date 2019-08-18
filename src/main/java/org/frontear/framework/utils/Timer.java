package org.frontear.framework.utils;

public final class Timer {
	private long nanoseconds;

	public Timer() {
		reset();
	}

	public void reset() {
		this.nanoseconds = System.nanoTime();
	}

	public boolean hasElapsed(TimeUnit unit, long time) {
		return getElapsed(unit) >= time;
	}

	public long getElapsed(TimeUnit unit) {
		return (System.nanoTime() - nanoseconds) / unit.getDivisor();
	}

	@Override public String toString() {
		return String
				.format("%02d:%02d:%02d.%03d", getElapsed(TimeUnit.HOUR) % 24, getElapsed(TimeUnit.MINUTE) % 60, getElapsed(TimeUnit.SECOND) % 60, getElapsed(TimeUnit.MILLISECOND) % 1000);
	}
}
