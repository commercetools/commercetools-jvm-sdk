package io.sphere.sdk.client;

public class JsonException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public JsonException() {
    }

    public JsonException(final Throwable cause) {
        super(cause);
    }

    public JsonException(final String message) {
        super(message);
    }

    public JsonException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public JsonException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
