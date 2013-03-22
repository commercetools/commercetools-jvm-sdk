package de.commercetools.internal.request;

/** Request that provides access to its internal request holder, therefore to the method, URL, query params, body. */
public interface TestableRequest {
    /** Returns the internal request holder, for testing purposes. */
    TestableRequestHolder getRequestHolder();
}
