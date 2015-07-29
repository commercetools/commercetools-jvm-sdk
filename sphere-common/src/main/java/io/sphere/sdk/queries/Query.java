package io.sphere.sdk.queries;

import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpResponse;

public interface Query<T> extends SphereRequest<PagedQueryResult<T>> {
    int MAX_OFFSET = 100000;
    int MIN_OFFSET = 0;

    @Override
    PagedQueryResult<T> deserialize(final HttpResponse httpResponse);

    default Query<T> toQuery() {
        return this;
    }
}
