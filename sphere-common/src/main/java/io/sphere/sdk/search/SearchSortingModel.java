package io.sphere.sdk.search;

public interface SearchSortingModel<T, S extends SearchSortDirection> {
    SearchSort<T> sorted(S sortDirection);
}