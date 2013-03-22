package io.sphere.internal.request;

import com.google.common.collect.Multimap;

/** Provides access to request internals, for debugging, logging and testing purposes. */
public interface TestableRequestHolder {
    /** The HTTP method of the request. */
    String getMethod();

    /** The url the request will be sent to. */
    String getUrl();

    /** The full url with serialized query params. */
    String getFullUrl();

    /** The query parameters of the request. */
    Multimap<String, String> getQueryParams();

    /** The body of the request. */
    String getBody();
}
