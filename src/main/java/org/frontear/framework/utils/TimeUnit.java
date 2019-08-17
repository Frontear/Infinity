package org.frontear.framework.utils;

public enum TimeUnit {
	NANOSECOND(1L),
	MILLISECOND(1000000L),
	SECOND(1000000000L),
	MINUTE(60000000000L),
	HOUR(3600000000000L);

	private final long nano_divisor;

	TimeUnit(long nano_divisor) {
		this.nano_divisor = nano_divisor;
	}

	public long getDivisor() {
		return nano_divisor;
	}
}
