package io.sphere.sdk.utils;

import java.util.function.Supplier;

/** Internal logging used by the sphere Java client itself.
 *
 *  <p>Uses slf4j logger named 'sphere' and does not depend on Play logger
 *  (which also uses slf4j loggers, named 'play' and 'application').</p>
 *
 *  <p>The logger can be configured per Play application using 'logger.sphere' in 'application.conf'.</p>
 *
 *  <p>You can provide directly objects to log, toString will only be called if the log level is enabled:</p>
 *
 *  {@include.example example.Logging#showObjectToString()}
 *
 *  <p>If you want to create complex Strings for logging, you can do it lazily by providing a Supplier for the log message:</p>
 *
 *  {@include.example example.Logging#showLazyToString()}
 *
 * */
public class Log {
    // Log into 'sphere' logger - can be configured in 'application.conf' of the target application.
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("sphere");

    public static void trace(Object message) {
        if (log.isTraceEnabled()) {
            log.trace(message.toString());
        }
    }

    public static void trace(Supplier<Object> message) {
        if (log.isTraceEnabled()) {
            log.trace(message.get().toString());
        }
    }

    public static void debug(Object message) {
        if (log.isDebugEnabled()) {
            log.debug(message.toString());
        }
    }

    public static void debug(Supplier<Object> message) {
        if (log.isDebugEnabled()) {
            log.debug(message.get().toString());
        }
    }

    public static void info(Object message) {
        if (log.isInfoEnabled()) {
            log.info(message.toString());
        }
    }

    public static void info(Supplier<Object> message) {
        if (log.isInfoEnabled()) {
            log.info(message.get().toString());
        }
    }

    public static void warn(Object message) {
        if (log.isWarnEnabled()) {
            log.warn(message.toString());
        }
    }

    public static void warn(Supplier<Object> message) {
        if (log.isWarnEnabled()) {
            log.warn(message.get().toString());
        }
    }

    public static void warn(Object message, Throwable error) {
        if (log.isWarnEnabled()) {
            log.warn(message.toString(), error);
        }
    }

    public static void warn(Throwable error) {
        warn(error.getMessage(), error);
    }

    public static void error(Object message) {
        if (log.isErrorEnabled()) {
            log.error(message.toString());
        }
    }

    public static void error(Supplier<Object> message) {
        if (log.isErrorEnabled()) {
            log.error(message.get().toString());
        }
    }

    public static void error(Object message, Throwable error) {
        if (log.isErrorEnabled()) {
            log.error(message.toString(), error);
        }
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