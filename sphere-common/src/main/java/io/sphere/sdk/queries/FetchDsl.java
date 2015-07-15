package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ReferenceExpandeableDsl;

public interface FetchDsl<R, T, C> extends Fetch<R>, ReferenceExpandeableDsl<T, C> {
    Fetch<R> toFetch();
}
