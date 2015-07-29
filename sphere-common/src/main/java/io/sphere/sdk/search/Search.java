package io.sphere.sdk.search;

import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpResponse;

public interface Search<T> extends SphereRequest<PagedSearchResult<T>> {
    int MAX_OFFSET = 100000;
    int MIN_OFFSET = 0;

    @Override
    PagedSearchResult<T> deserialize(final HttpResponse httpResponse);

    default Search<T> toSearch() {
        return this;
    }
}
