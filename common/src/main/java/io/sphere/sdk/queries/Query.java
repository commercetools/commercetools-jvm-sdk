package io.sphere.sdk.queries;

import java.util.function.Function;
import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.http.HttpResponse;

public interface Query<T> extends ClientRequest<PagedQueryResult<T>> {

    @Override
    public abstract Function<HttpResponse, PagedQueryResult<T>> resultMapper();

    default Query<T> toQuery() {
        return this;
    }
}
