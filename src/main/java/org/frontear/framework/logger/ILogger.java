package org.frontear.framework.logger;

public interface ILogger {
	// doesn't return anything, just for hacky convenience
	<T extends Throwable> T fatal(T throwable, Object object, Object... args) throws T;

	void error(Object object, Object... args);

	void warn(Object object, Object... args);

	void info(Object object, Object... args);

	void debug(Object object, Object... args);

	default void endStartSection(String title) {
		endSection();
		startSection(title);
	}

	void endSection();

	void startSection(String title);
}
