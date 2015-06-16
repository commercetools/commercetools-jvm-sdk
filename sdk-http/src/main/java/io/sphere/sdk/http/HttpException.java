package io.sphere.sdk.http;

public class HttpException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public HttpException(final Throwable cause) {
        super(cause);
    }

    public HttpException(final String message) {
        super(message);
    }

    public HttpException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
