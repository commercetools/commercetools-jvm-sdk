package io.sphere.internal.util;

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

    public static void trace(String message) {
        if (log.isTraceEnabled()) log.trace(message);
    }

    public static void debug(String message) {
        if (log.isDebugEnabled()) log.debug(message);
    }

    public static void info(String message) {
        if (log.isInfoEnabled()) log.info(message);
    }

    public static void warn(String message) {
        if (log.isWarnEnabled()) log.warn(message);
    }
    public static void warn(String message, Throwable error) {
        if (log.isWarnEnabled()) log.warn(message, error);
    }
    public static void warn(Throwable error) {
        warn(error.getMessage(), error);
    }

    public static void error(String message) {
        if (log.isErrorEnabled()) log.error(message);
    }
    public static void error(String message, Throwable error) {
        if (log.isErrorEnabled()) log.error(message, error);
    }
    public static void error(Throwable error) {
        error(error.getMessage(), error);
    }

    public static boolean isTraceEnabled() { return log.isTraceEnabled(); }
    public static boolean isDebugEnabled() { return log.isDebugEnabled(); }
    public static boolean isInfoEnabled()  { return log.isInfoEnabled(); }
    public static boolean isWarnEnabled()  { return log.isWarnEnabled(); }
    public static boolean isErrorEnabled() { return log.isWarnEnabled(); }
}
