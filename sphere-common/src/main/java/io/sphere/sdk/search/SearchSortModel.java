package io.sphere.sdk.search;

public interface SearchSortModel<T, S extends DirectionlessSearchSortModel<T>> {

    S sorted();
}