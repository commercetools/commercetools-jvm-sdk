package io.sphere.internal.request;

/** Provides access to request internals, for debugging, logging and testing purposes. */
public interface TestableRequestHolder {
    /** The HTTP method of the request. */
    String getMethod();

    /** The url the request will be sent to. */
    String getUrl();

    /** The body of the request. */
    String getBody();
}
