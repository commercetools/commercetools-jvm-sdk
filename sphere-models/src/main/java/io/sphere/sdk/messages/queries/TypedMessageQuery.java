package io.sphere.sdk.messages.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;

final class TypedMessageQuery<T> extends SphereRequestBase implements Query<T> {
    private final HttpRequestIntent httpRequestIntent;
    private final TypeReference<PagedQueryResult<T>> resultTypeReference;

    public TypedMessageQuery(final HttpRequestIntent httpRequestIntent, final TypeReference<PagedQueryResult<T>> resultTypeReference) {
        this.httpRequestIntent = httpRequestIntent;
        this.resultTypeReference = resultTypeReference;
    }

    @Override
    public PagedQueryResult<T> deserialize(final HttpResponse httpResponse) {
        return deserialize(httpResponse, resultTypeReference);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return httpRequestIntent;
    }
}