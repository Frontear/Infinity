package org.frontear.framework.logger.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.Message;
import org.frontear.framework.client.impl.Client;
import org.frontear.framework.logger.ILogger;

/**
 * An implementation of {@link ILogger}
 */
public final class Logger implements ILogger {
	private static final char pad = '—'; // \u2014
	private static final int repeat = 64;
	private final org.apache.logging.log4j.Logger log;

	/**
	 * @param name Will prefix all log stream outputs
	 */
	public Logger(String name) {
		this.log = LogManager.getLogger(name);
	}

	/**
	 * Creates a logger instance, and will automatically find the Class name
	 */
	public Logger() {
		final String[] split = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
		this.log = LogManager.getLogger(split[split.length - 1]);
	}

	/**
	 * A fatal error should be seldom used, but it can indicate extremely bad problems
	 *
	 * @see ILogger#fatal(Throwable, Object, Object...)
	 */
	@Override public <T extends Throwable> T fatal(T throwable, Object object, Object... args) throws T {
		log(Level.FATAL, object, args);
		throw throwable;
	}

	/**
	 * Indicates a relatively serious issue, but something that can be handled internally, nonetheless, it's an
	 * important problem as it ideally shouldn't happen
	 *
	 * @see ILogger#error(Object, Object...)
	 */
	@Override public void error(Object object, Object... args) {
		log(Level.ERROR, object, args);
	}

	/**
	 * Very minor issue, something which was within the realm of expectation and will easily be handled
	 *
	 * @see ILogger#warn(Object, Object...)
	 */
	@Override public void warn(Object object, Object... args) {
		log(Level.WARN, object, args);
	}

	/**
	 * Informs the user of some type of change. This is commonly used just to keep track of what's internally going on
	 *
	 * @see ILogger#info(Object, Object...)
	 */
	@Override public void info(Object object, Object... args) {
		log(Level.INFO, object, args);
	}

	/**
	 * Makes use of {@link org.apache.logging.log4j.Logger#log(Level, Message)} using {@link Level#OFF} to send only
	 * debug messages when <i>-Dfrontear.debug=true</i> is set
	 *
	 * @see ILogger#debug(Object, Object...)
	 */
	@Override public void debug(Object object, Object... args) {
		if (Client.DEBUG) {
			log(Level.OFF, object, args);
		}
	}

	@Override public void endSection() {
		info(StringUtils.repeat(pad, repeat));
	}

	@Override public void startSection(String title) {
		info(StringUtils.center(String.format(" %s ", title), repeat, pad));
	}

	/**
	 * Internally handles all logging calls given to {@link ILogger}, will throw a {@link UnsupportedOperationException}
	 * if a specified level isn't internally supported
	 *
	 * @param level  The level specified by the logging call
	 * @param object Will be converted into a string via {@link String#valueOf(Object)}
	 * @param args   Extra arguments for {@link String#format(String, Object...)}
	 */
	private void log(Level level, Object object, Object... args) {
		final String message = String.format(String.valueOf(object), args);

		switch (level) {
			case OFF:
			case FATAL:
			case ERROR:
			case WARN:
			case INFO:
				log.log(level, message);
				return;
			default:
				fatal(new UnsupportedOperationException(), "Level %s is not supported", level.name());
		}
	}
}
