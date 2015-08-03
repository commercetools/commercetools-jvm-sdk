package io.sphere.sdk.queries;

public interface TimestampSortingModel<T> extends QuerySortingModel<T> {
    @Override
    QuerySort<T> sort(QuerySortDirection sortDirection);

    @Override
    DirectionlessQuerySort<T> sort();
}
