package io.sphere.sdk.retry;

public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(final Throwable cause) {
        super(cause);
    }

    public ServiceException(final String message) {
        super(message);
    }

    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ServiceException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
