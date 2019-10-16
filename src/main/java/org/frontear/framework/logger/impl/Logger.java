package org.frontear.framework.logger.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.Message;
import org.frontear.framework.client.impl.Client;
import org.frontear.framework.logger.ILogger;

/**
 * An implementation of {@link ILogger}
 */
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class Logger implements ILogger {
    private static final char pad = '—';
    private static final int repeat = 64;
    org.apache.logging.log4j.Logger log;

    /**
     * Creates a logger instance, and will automatically find the prefix name based on the calling
     * class
     */
    public Logger() {
        this(sanitize(Thread.currentThread().getStackTrace()[2].getClassName()));
    }

    /**
     * Creates a logger instance with prefix beginning with your specified name
     *
     * @param name Will prefix all log stream outputs
     */
    public Logger(@NonNull final String name) {
        this.log = LogManager.getLogger(name);
    }

    // removes package prefix
    private static String sanitize(@NonNull final String className) {
        val split = className.split("\\.");
        return split[split.length - 1];
    }

    /**
     * A fatal error should be seldom used, but it can indicate extremely bad problems
     *
     * @see ILogger#fatal(Throwable, Object, Object...)
     */
    @Override
    public <T extends Throwable> T fatal(@NonNull final T throwable, @NonNull final Object object,
        final Object... args) {
        log(Level.FATAL, object, args);
        throw throwable;
    }

    /**
     * Indicates a relatively serious issue, but something that can be handled internally,
     * nonetheless, it's an important problem as it ideally shouldn't happen
     *
     * @see ILogger#error(Object, Object...)
     */
    @Override
    public void error(@NonNull final Object object, final Object... args) {
        log(Level.ERROR, object, args);
    }

    /**
     * Very minor issue, something which was within the realm of expectation and will easily be
     * handled
     *
     * @see ILogger#warn(Object, Object...)
     */
    @Override
    public void warn(@NonNull final Object object, final Object... args) {
        log(Level.WARN, object, args);
    }

    /**
     * Informs the user of some type of change. This is commonly used just to keep track of what's
     * internally going on
     *
     * @see ILogger#info(Object, Object...)
     */
    @Override
    public void info(@NonNull final Object object, final Object... args) {
        log(Level.INFO, object, args);
    }

    /**
     * Makes use of {@link org.apache.logging.log4j.Logger#log(Level, Message)} using {@link
     * Level#OFF} to send only debug messages when <i>-Dfrontear.debug=true</i> is set
     *
     * @see ILogger#debug(Object, Object...)
     */
    @Override
    public void debug(@NonNull final Object object, final Object... args) {
        if (Client.DEBUG) {
            log(Level.OFF, object, args);
        }
    }

    @Override
    public void endSection() {
        info(StringUtils.repeat(pad, repeat));
    }

    @Override
    public void startSection(@NonNull final String title) {
        info(StringUtils.center(" $title ", repeat, pad));
    }

    /**
     * Internally handles all logging calls given to {@link ILogger}, will throw a {@link
     * UnsupportedOperationException} if a specified level isn't internally supported. All logger
     * calls which prefix with a Class#Method format if {@link Client#DEBUG}
     *
     * @param level  The level specified by the logging call
     * @param object Will be converted into a string via {@link String#valueOf(Object)}
     * @param args   Extra arguments for {@link String#format(String, Object...)}
     */
    private void log(final Level level, final Object object, final Object... args) {
        //noinspection unused
        val element = Thread.currentThread().getStackTrace()[3];
        val message = new StringBuilder();
        if (Client.DEBUG) {
            message.append("[${sanitize(element.getClassName())}#${element.getMethodName()}]: ");
        }
        message.append(String.format(String.valueOf(object), args));

        switch (level) {
            case OFF:
            case FATAL:
            case ERROR:
            case WARN:
            case INFO:
                log.log(level, message.toString());
                return;
            default:
                fatal(new UnsupportedOperationException(), "Level %s is not supported",
                    level.name());
        }
    }
}
