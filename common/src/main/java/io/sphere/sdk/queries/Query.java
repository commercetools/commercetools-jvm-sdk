package io.sphere.sdk.queries;

import java.util.function.Function;
import io.sphere.sdk.client.ClientRequest;
import io.sphere.sdk.http.HttpResponse;

public interface Query<T> extends ClientRequest<PagedQueryResult<T>> {
    public static final int MAX_OFFSET = 100000;
    public static final int MIN_OFFSET = 0;

    @Override
    public abstract Function<HttpResponse, PagedQueryResult<T>> resultMapper();

    default Query<T> toQuery() {
        return this;
    }
}
