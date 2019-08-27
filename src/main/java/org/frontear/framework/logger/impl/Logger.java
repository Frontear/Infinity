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
	private static final char pad = '—';
	private static final int repeat = 64;
	private final org.apache.logging.log4j.Logger log;
	private final boolean trace;

	/**
	 * Creates a logger instance, will automatically find the class name. and will enable tracing
	 */
	public Logger() {
		this(true);
	}

	/**
	 * Creates a logger instance, and will automatically find the class name
	 *
	 * @param trace Attempts to find class and method name where Logger is invoked, and will prefix the entry with that
	 *              information
	 */
	public Logger(boolean trace) {
		this(sanitize(Thread.currentThread().getStackTrace()[2].getClassName()), trace);
	}

	/**
	 * Creates a logger instance with prefix beginning with your specified name
	 *
	 * @param name  Will prefix all log stream outputs
	 * @param trace Attempts to find class and method name where {@link Logger#debug(Object, Object...)} is invoked, and
	 *              will prefix the entry with that information
	 */
	public Logger(String name, boolean trace) {
		this.log = LogManager.getLogger(name);
		this.trace = trace;
	}

	// removes package prefix
	private static String sanitize(String className) {
		final String[] split = className.split("\\.");
		return split[split.length - 1];
	}

	/**
	 * Creates a logger instance with prefix beginning with your specified name, and will enable tracing
	 *
	 * @param name Will prefix all log stream outputs
	 */
	public Logger(String name) {
		this(name, true);
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
		final StackTraceElement element = Thread.currentThread().getStackTrace()[3];
		final StringBuilder message = new StringBuilder();

		switch (level) {
			case OFF:
				if (trace) {
					message.append(String
							.format("[%s#%s]: ", sanitize(element.getClassName()), element.getMethodName()));
				}
			case FATAL:
			case ERROR:
			case WARN:
			case INFO:
				message.append(String.format(String.valueOf(object), args));
				log.log(level, message.toString());
				return;
			default:
				fatal(new UnsupportedOperationException(), "Level %s is not supported", level.name());
		}
	}
}
