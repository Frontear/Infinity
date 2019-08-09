package org.frontear.framework.logger;

public interface ILogger {
	void debug(Object object, Object... args);

	void info(Object object, Object... args);

	void warn(Object object, Object... args);

	void error(Object object, Object... args);

	// doesn't actually throw anything, just for hacky convenience
	Throwable fatal(Throwable throwable, Object object, Object... args) throws Throwable;

	default void endStartSection(String title) {
		endSection();
		startSection(title);
	}

	void endSection();

	void startSection(String title);
}
