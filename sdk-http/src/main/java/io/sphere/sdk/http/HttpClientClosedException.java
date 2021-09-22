package io.sphere.sdk.http;

public class HttpClientClosedException extends HttpException {
    static final long serialVersionUID = 0L;

    public HttpClientClosedException(Throwable cause) {
        super(cause);
    }
}
