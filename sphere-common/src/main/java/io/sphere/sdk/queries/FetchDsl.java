package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ReferenceExpandeableDsl;

public interface FetchDsl<T, C> extends Fetch<T>, ReferenceExpandeableDsl<T, C> {
    Fetch<T> toFetch();
}
