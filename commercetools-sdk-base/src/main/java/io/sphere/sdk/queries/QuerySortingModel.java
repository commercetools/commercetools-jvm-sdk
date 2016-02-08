package io.sphere.sdk.queries;

public interface QuerySortingModel<T> {
    /**
     * Use {@link #sort()} instead.
     *
     * @param sortDirection the direction to sort with
     * @return query sort
     */
    @Deprecated
    QuerySort<T> sort(final QuerySortDirection sortDirection);

    DirectionlessQuerySort<T> sort();
}