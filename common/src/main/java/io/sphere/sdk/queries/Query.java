package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import io.sphere.sdk.client.ClientRequest;
import io.sphere.sdk.client.HttpResponse;
import io.sphere.sdk.client.Requestable;

public interface Query<T> extends ClientRequest<PagedQueryResult<T>> {

    @Override
    public abstract Function<HttpResponse, PagedQueryResult<T>> resultMapper();
}
