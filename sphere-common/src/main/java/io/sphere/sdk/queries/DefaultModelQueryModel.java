package io.sphere.sdk.queries;

public interface DefaultModelQueryModel<T> {
    StringQuerySortingModel<T> id();

    TimestampSortingModel<T> createdAt();

    TimestampSortingModel<T> lastModifiedAt();
}
