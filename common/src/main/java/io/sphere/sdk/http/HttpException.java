package io.sphere.sdk.http;

public class HttpException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }
}