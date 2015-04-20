package io.sphere.sdk.queries;

import io.sphere.sdk.client.JsonEndpoint;

public abstract class ByIdFetchImpl<T> extends FetchImpl<T> {

    protected ByIdFetchImpl(final String id, final JsonEndpoint<T> endpoint) {
        super(endpoint, id);
    }
}
