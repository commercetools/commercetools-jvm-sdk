package io.sphere.sdk.queries;

public interface QuerySortingModel<T> {
    QuerySort<T> sort(final QuerySortDirection sortDirection);

    DirectionlessQuerySort<T> sort();
}