package org.frontear.infinity.logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.frontear.framework.logger.ILogger;

public class Logger implements ILogger {
	private final org.apache.logging.log4j.Logger log;
	private final char pad = '—'; // \u2014
	private final int repeat = 64;

	public Logger(String name) {
		this.log = LogManager.getLogger(name);
	}

	@Override public void debug(Object object, Object... args) {
		if (Boolean.parseBoolean(System
				.getProperty("frontear.debug", "false"))) { // either get value of frontear.debug, or return false if it doesn't exist
			log(Level.DEBUG, object, args);
		}
	}

	@Override public void info(Object object, Object... args) {
		log(Level.INFO, object, args);
	}

	@Override public void warn(Object object, Object... args) {
		log(Level.WARN, object, args);
	}

	@Override public void error(Object object, Object... args) {
		log(Level.ERROR, object, args);
	}

	@Override public <T extends Throwable> T fatal(T throwable, Object object, Object... args) throws T {
		log(Level.FATAL, object, args);
		throw throwable;
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
			case DEBUG: // just use info stuff
			case INFO:
				log.info(message);
				return;
			case WARN:
				log.warn(message);
				return;
			case ERROR:
				log.error(message);
				return;
			case FATAL:
				log.fatal(message);
			default:
				fatal(new UnsupportedOperationException(), "Level %s is not supported", level.name());
		}
	}
}
