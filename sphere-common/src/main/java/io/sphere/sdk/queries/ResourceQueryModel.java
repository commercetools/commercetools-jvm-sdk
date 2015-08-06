package io.sphere.sdk.queries;

public interface ResourceQueryModel<T> {
    StringQuerySortingModel<T> id();

    TimestampSortingModel<T> createdAt();

    TimestampSortingModel<T> lastModifiedAt();
}
