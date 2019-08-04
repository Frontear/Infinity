package org.frontear.framework.logger;

public interface ILogger {
	void debug(Object object, Object... args);

	void info(Object object, Object... args);

	void warn(Object object, Object... args);

	void error(Object object, Object... args);

	void fatal(Object object, Object... args);

	default void endStartSection(String title) {
		endSection();
		startSection(title);
	}

	void endSection();

	void startSection(String title);
}
