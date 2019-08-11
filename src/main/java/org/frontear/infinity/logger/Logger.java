package org.frontear.infinity.logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.frontear.framework.logger.ILogger;

public class Logger implements ILogger {
	private static final boolean debug = Boolean.parseBoolean(System.getProperty("frontear.debug", "false"));
	private static final char pad = '—'; // \u2014
	private static final int repeat = 64;
	private final org.apache.logging.log4j.Logger log;

	public Logger(String name) {
		this.log = LogManager.getLogger(name);
	}

	@Override public <T extends Throwable> T fatal(T throwable, Object object, Object... args) throws T {
		log(Level.FATAL, object, args);
		throw throwable;
	}

	@Override public void error(Object object, Object... args) {
		log(Level.ERROR, object, args);
	}

	@Override public void warn(Object object, Object... args) {
		log(Level.WARN, object, args);
	}

	@Override public void info(Object object, Object... args) {
		log(Level.INFO, object, args);
	}

	@Override public void debug(Object object, Object... args) {
		if (debug) { // either get value of frontear.debug, or return false if it doesn't exist
			log(Level.OFF, object, args);
		}
	}

	@Override public void endSection() {
		info(StringUtils.repeat(pad, repeat));
	}

	@Override public void startSection(String title) {
		info(StringUtils.center(String.format(" %s ", title), repeat, pad));
	}

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
