package io.sphere.sdk.queries;

import java.util.function.Function;
import io.sphere.sdk.requests.ClientRequest;
import io.sphere.sdk.requests.HttpResponse;

public interface Query<T> extends ClientRequest<PagedQueryResult<T>> {

    @Override
    public abstract Function<HttpResponse, PagedQueryResult<T>> resultMapper();
}
