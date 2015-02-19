package io.sphere.sdk.queries;

import io.sphere.sdk.client.JsonEndpoint;

public abstract class FetchByIdImpl<T> extends FetchImpl<T> {

    protected FetchByIdImpl(final String id, final JsonEndpoint<T> endpoint) {
        super(endpoint, id);
    }
}
