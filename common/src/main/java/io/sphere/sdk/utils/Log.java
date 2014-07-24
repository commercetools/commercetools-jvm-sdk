package io.sphere.sdk.utils;

/** Internal logging used by the sphere Java client itself.
 *
 *  Uses slf4j logger named 'sphere' and does not depend on Play logger
 *  (which also uses slf4j loggers, named 'play' and 'application').
 *
 *  The logger can be configured per Play application using 'logger.sphere' in 'application.conf'.
 * */
public class Log {
    // Log into 'sphere' logger - can be configured in 'application.conf' of the target application.
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("sphere");

    public static void trace(Object message) {
        if (log.isTraceEnabled()) log.trace(message.toString());
    }

    public static void debug(Object message) {
        if (log.isDebugEnabled()) log.debug(message.toString());
    }

    public static void info(Object message) {
        if (log.isInfoEnabled()) log.info(message.toString());
    }

    public static void warn(Object message) {
        if (log.isWarnEnabled()) log.warn(message.toString());
    }

    public static void warn(Object message, Throwable error) {
        if (log.isWarnEnabled()) log.warn(message.toString(), error);
    }

    public static void warn(Throwable error) {
        warn(error.getMessage(), error);
    }

    public static void error(Object message) {
        if (log.isErrorEnabled()) log.error(message.toString());
    }

    public static void error(Object message, Throwable error) {
        if (log.isErrorEnabled()) log.error(message.toString(), error);
    }

    public static void error(Throwable error) {
        error(error.getMessage(), error);
    }

    public static boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    public static boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public static boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public static boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    public static boolean isErrorEnabled() {
        return log.isWarnEnabled();
    }
}