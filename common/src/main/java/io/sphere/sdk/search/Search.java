package io.sphere.sdk.search;

import io.sphere.sdk.client.ClientRequest;
import io.sphere.sdk.http.HttpResponse;

import java.util.function.Function;

public interface Search<T> extends ClientRequest<PagedSearchResult<T>> {

    @Override
    public abstract Function<HttpResponse, PagedSearchResult<T>> resultMapper();


}
