package io.sphere.sdk.search;

public interface TermModel<T, V> {

    public FilterSearchModel<T, V> filter();

    public FacetSearchModel<T, V> facet();
}