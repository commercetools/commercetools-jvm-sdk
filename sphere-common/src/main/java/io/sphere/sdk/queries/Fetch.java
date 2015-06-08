package io.sphere.sdk.queries;

import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpResponse;

import java.util.Optional;

public interface Fetch<T> extends SphereRequest<Optional<T>> {
    @Override
    Optional<T> deserialize(final HttpResponse httpResponse);

    Fetch<T> toFetch();
}
