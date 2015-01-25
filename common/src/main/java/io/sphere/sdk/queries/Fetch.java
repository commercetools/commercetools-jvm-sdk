package io.sphere.sdk.queries;

import io.sphere.sdk.client.ClientRequest;
import io.sphere.sdk.http.HttpResponse;

import java.util.function.Function;

import java.util.Optional;

public interface Fetch<T> extends ClientRequest<Optional<T>> {
    @Override
    public abstract Function<HttpResponse, Optional<T>> resultMapper();
}
