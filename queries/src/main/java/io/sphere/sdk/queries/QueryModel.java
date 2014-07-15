package io.sphere.sdk.queries;

import java.util.Optional;

public interface QueryModel<T> {
    Optional<String> getPathSegment();

    Optional<? extends QueryModel<T>> getParent();
}