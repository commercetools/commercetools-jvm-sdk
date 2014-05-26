package io.sphere.sdk.queries;

import com.google.common.base.Optional;

public interface QueryModel<T> {
    Optional<String> getPathSegment();

    Optional<? extends QueryModel<T>> getParent();
}