package de.commercetools.sphere.client;

import de.commercetools.internal.*;

/** Fake request construction for RequestFactoryImpl. */
public class MockRequestFactory extends RequestFactoryImpl {
    private String fakeResponseBody;
    private int fakeResponseStatus;

    public MockRequestFactory(String fakeResponseBody, int fakeResponseStatus) {
        super(null, null);
        this.fakeResponseBody = fakeResponseBody;
        this.fakeResponseStatus = fakeResponseStatus;
    }

    @Override
    public <T> RequestHolder<T> createGetRequest(String url) {
        return new MockRequestHolder<T>(url, fakeResponseStatus, fakeResponseBody);
    }

    @Override
    public <T> RequestHolder<T> createPostRequest(String url) {
        return new MockRequestHolder<T>(url, fakeResponseStatus, fakeResponseBody);
    }
}
