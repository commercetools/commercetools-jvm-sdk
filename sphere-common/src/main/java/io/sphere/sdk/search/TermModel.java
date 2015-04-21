package io.sphere.sdk.search;

public interface TermModel<T, V> {

    FilterSearchModel<T, V> filterBy();

    FacetSearchModel<T, V> facetOf();
}