package io.sphere.sdk.search;

public interface SearchSortingModel<T, S extends SearchSortDirection> {
    SearchSort<T> sort(S sortDirection);
}