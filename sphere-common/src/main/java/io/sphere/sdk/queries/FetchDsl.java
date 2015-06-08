package io.sphere.sdk.queries;

public interface FetchDsl<T, C> extends Fetch<T>, ReferenceExpandeableDsl<T, C> {
    Fetch<T> toFetch();
}
