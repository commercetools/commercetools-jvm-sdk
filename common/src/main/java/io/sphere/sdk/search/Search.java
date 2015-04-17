package io.sphere.sdk.search;

import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpResponse;

public interface Search<T> extends SphereRequest<PagedSearchResult<T>> {

    @Override
    PagedSearchResult<T> deserialize(final HttpResponse httpResponse);
}
