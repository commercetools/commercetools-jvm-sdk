package io.sphere.sdk.requests;

import java.util.function.Function;

import java.util.Optional;

public interface Fetch<T> extends ClientRequest<Optional<T>> {
    @Override
    public abstract Function<HttpResponse, Optional<T>> resultMapper();
}
