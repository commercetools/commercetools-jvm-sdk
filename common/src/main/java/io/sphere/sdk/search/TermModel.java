package io.sphere.sdk.search;

public interface TermModel<T, V> {

    public TermFilterSearchModel<T, V> filter();

    public TermFacetSearchModel<T, V> facet();
}