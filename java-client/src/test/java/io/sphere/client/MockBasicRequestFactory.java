package io.sphere.client;

import io.sphere.internal.request.BasicRequestFactory;
import io.sphere.internal.request.RequestHolder;

/** Creates fake requests for {@link io.sphere.internal.request.RequestFactoryImpl}. */
public class MockBasicRequestFactory implements BasicRequestFactory {
    private String fakeResponseBody;
    private int fakeResponseStatus;

    public MockBasicRequestFactory(String fakeResponseBody, int fakeResponseStatus) {
        this.fakeResponseBody = fakeResponseBody;
        this.fakeResponseStatus = fakeResponseStatus;
    }

    @Override public <T> RequestHolder<T> createGet(String url) {
        return new MockRequestHolder<T>(url, "GET", fakeResponseStatus, fakeResponseBody);
    }

    @Override public <T> RequestHolder<T> createPost(String url) {
        return new MockRequestHolder<T>(url, "POST", fakeResponseStatus, fakeResponseBody);
    }

    @Override public <T> RequestHolder<T> createDelete(String url) {
        return new MockRequestHolder<T>(url, "DELETE", fakeResponseStatus, fakeResponseBody);
    }
}
