package io.sphere.sdk.search;

public interface SearchSortingModel<T, S extends SearchSortDirection> {
    public SearchSort<T> sort(S sortDirection);
}