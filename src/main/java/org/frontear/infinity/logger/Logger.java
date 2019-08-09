package org.frontear.infinity.logger;

import org.apache.commons.lang3.StringUtils;
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
			info(object, args);
		}
	}

	@Override public void info(Object object, Object... args) {
		log.info(String.format(String.valueOf(object), args));
	}

	@Override public void warn(Object object, Object... args) {
		log.warn(String.format(String.valueOf(object), args));
	}

	@Override public void error(Object object, Object... args) {
		log.error(String.format(String.valueOf(object), args));
	}

	@Override public void fatal(Throwable throwable, Object object, Object... args) {
		log.fatal(String.format(String.valueOf(object), args), throwable);
	}

	@Override public void endSection() {
		info(StringUtils.repeat(pad, repeat));
	}

	@Override public void startSection(String title) {
		info(StringUtils.center(String.format(" %s ", title), repeat, pad));
	}
}
