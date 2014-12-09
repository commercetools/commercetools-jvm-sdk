package io.sphere.sdk.search;

public interface SearchSortingModel<T> {
    public SearchSort<T> sort(SearchSortDirection sortDirection);
}