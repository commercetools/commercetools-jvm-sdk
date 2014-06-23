package io.sphere.sdk.queries;

import com.google.common.base.Function;
import io.sphere.sdk.client.ClientRequest;
import io.sphere.sdk.client.HttpResponse;

public interface Query<T> extends ClientRequest<PagedQueryResult<T>> {

    @Override
    public abstract Function<HttpResponse, PagedQueryResult<T>> resultMapper();
}
