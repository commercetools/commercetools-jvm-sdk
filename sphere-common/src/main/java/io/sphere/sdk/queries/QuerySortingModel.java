package io.sphere.sdk.queries;

public interface QuerySortingModel<T> {
    QuerySort<T> sort(QuerySortDirection sortDirection);
}