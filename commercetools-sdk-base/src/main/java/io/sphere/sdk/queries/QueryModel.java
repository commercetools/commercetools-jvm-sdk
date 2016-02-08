package io.sphere.sdk.queries;

import javax.annotation.Nullable;

public interface QueryModel<T> {
    @Nullable
    String getPathSegment();

    @Nullable
    QueryModel<T> getParent();
}