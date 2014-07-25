package io.sphere.sdk.utils;

import io.sphere.sdk.requests.HttpRequest;
import io.sphere.sdk.requests.HttpResponse;
import io.sphere.sdk.requests.Requestable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

import static org.apache.commons.lang3.StringUtils.substringBefore;

/** Internal logging used by the sphere Java client itself.
 *
 *  <p>Uses slf4j logger named 'sphere' and does not depend on Play logger
 *  (which also uses slf4j loggers, named 'play' and 'application').</p>
 *
 *  <p>The logger can be configured per Play application using 'logger.sphere' in 'application.conf'.</p>
 *
 *  <p>If you want to create complex Strings for logging, you can do it lazily by providing a Supplier for the log message:</p>
 *
 *  {@include.example example.Logging#showLazyToString()}
 *
 * */
public class SphereInternalLogger {
    private final Logger underlyingLogger;

    private SphereInternalLogger(final Logger underlyingLogger) {
        this.underlyingLogger = underlyingLogger;
    }

    public SphereInternalLogger debug(final Supplier<Object> message) {
        if (underlyingLogger.isDebugEnabled()) {
            underlyingLogger.debug(message.get().toString());
        }
        return this;
    }

    public SphereInternalLogger trace(final Supplier<Object> message) {
        if (underlyingLogger.isTraceEnabled()) {
            underlyingLogger.trace(message.get().toString());
        }
        return this;
    }

    public SphereInternalLogger warn(final Supplier<Object> message) {
        if (underlyingLogger.isWarnEnabled()) {
            underlyingLogger.warn(message.get().toString());
        }
        return this;
    }

    public SphereInternalLogger error(final Supplier<Object> message, final Throwable throwable) {
        if (underlyingLogger.isErrorEnabled()) {
            underlyingLogger.error(message.get().toString(), throwable);
        }
        return this;
    }

    public static SphereInternalLogger getLogger(final Requestable requestable) {
        final HttpRequest httpRequest = requestable.httpRequest();
        return getLogger(getFirstPathElement(httpRequest) + ".requests");
    }

    public static SphereInternalLogger getLogger(final HttpResponse response) {
        final String firstPathElement = response.getAssociatedRequest()
                .map(r -> getFirstPathElement(r)).orElse("endpoint-unknown");
        return getLogger(firstPathElement + ".responses");
    }

    private static String getFirstPathElement(final HttpRequest httpRequest) {
        final String path = httpRequest.getPath();
        final String leadingSlashRemoved = path.substring(1);
        return substringBefore(substringBefore(leadingSlashRemoved, "/"), "?");
    }

    public static SphereInternalLogger getLogger(final String loggerName) {
        return new SphereInternalLogger(LoggerFactory.getLogger("sphere." + loggerName));
    }
}