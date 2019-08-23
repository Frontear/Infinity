package org.frontear.framework.utils;

/**
 * Represents possible conversions from nanoseconds to multiple units of time
 */
public enum TimeUnit {
	NANOSECOND(1L),
	MILLISECOND(1000000L),
	SECOND(1000000000L),
	MINUTE(60000000000L),
	HOUR(3600000000000L);

	private final long nano_divisor;

	/**
	 * @param nano_divisor The value which divides a span of nanotime to evaluate to the unit of time
	 */
	TimeUnit(long nano_divisor) {
		this.nano_divisor = nano_divisor;
	}

	/**
	 * @return The divisor to convert nanoseconds span to some unit of time
	 */
	public long getDivisor() {
		return nano_divisor;
	}
}
