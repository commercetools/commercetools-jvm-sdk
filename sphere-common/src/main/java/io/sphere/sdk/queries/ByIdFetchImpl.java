package io.sphere.sdk.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.http.HttpQueryParameter;

import java.util.List;

public abstract class ByIdFetchImpl<T> extends FetchImpl<T> {

    protected ByIdFetchImpl(final String id, final JsonEndpoint<T> endpoint) {
        super(endpoint, id);
    }

    public ByIdFetchImpl(final JsonEndpoint<T> endpoint, final String identifierToSearchFor, final List<HttpQueryParameter> additionalParameters) {
        super(endpoint, identifierToSearchFor, additionalParameters);
    }
}
