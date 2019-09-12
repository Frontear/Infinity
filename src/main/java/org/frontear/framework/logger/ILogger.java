package org.frontear.framework.logger;

import lombok.NonNull;

/**
 * A basic logging system, backed by {@link org.apache.logging.log4j.Logger}
 */
public interface ILogger {
	/**
	 * A fatal error is usually an error that will no longer allow the application to continue running.
	 *
	 * @param throwable The throwable that prevents continuation of the application
	 * @param object    An object that is converted into a string
	 * @param args      Any additional objects that are passed into a {@link String#format(String, Object...)}
	 * @param <T>       represents the type of throwable
	 *
	 * @return nothing, this is just for hacky convenience
	 *
	 * @throws T The throwable that prevents continuation of the application
	 */
	<T extends Throwable> T fatal(@NonNull T throwable, @NonNull Object object, Object... args) throws T;

	/**
	 * An error usually represents something that causes an issue in a small part of the application, such as a method
	 * call
	 *
	 * @param object An object that is converted into a string
	 * @param args   Any additional objects that are passed into a {@link String#format(String, Object...)}
	 */
	void error(@NonNull Object object, Object... args);

	/**
	 * A warning represents a minor issue, something that can potentially end up causing an error
	 *
	 * @param object An object that is converted into a string
	 * @param args   Any additional objects that are passed into a {@link String#format(String, Object...)}
	 */
	void warn(@NonNull Object object, Object... args);

	/**
	 * An info represents a simple informative log, this can just be telling the user of small things, such as changes
	 * in the application
	 *
	 * @param object An object that is converted into a string
	 * @param args   Any additional objects that are passed into a {@link String#format(String, Object...)}
	 */
	void info(@NonNull Object object, Object... args);

	/**
	 * A debug represents a log that will only show up if "-Dfrontear.debug=true" is passed as a JVM argument
	 *
	 * @param object An object that is converted into a string
	 * @param args   Any additional objects that are passed into a {@link String#format(String, Object...)}
	 */
	void debug(@NonNull Object object, Object... args);

	/**
	 * Calls {@link ILogger#endSection()}, then {@link ILogger#startSection(String)}
	 *
	 * @param title The section name
	 */
	default void endStartSection(@NonNull String title) {
		endSection();
		startSection(title);
	}

	/**
	 * Ends a section of the log. This is mostly for organization
	 */
	void endSection();

	/**
	 * Starts a new section in the log.
	 *
	 * @param title The section name
	 */
	void startSection(@NonNull String title);
}
