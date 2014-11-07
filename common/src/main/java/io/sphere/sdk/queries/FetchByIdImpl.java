package io.sphere.sdk.queries;

import io.sphere.sdk.http.JsonEndpoint;
import io.sphere.sdk.models.Identifiable;

public abstract class FetchByIdImpl<T> extends FetchImpl<T> {

    protected FetchByIdImpl(final Identifiable<T> identifiable, final JsonEndpoint<T> endpoint) {
        super(endpoint, identifiable.getId());
    }
}
