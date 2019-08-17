package org.frontear.framework.utils;

public class Timer {
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
}
