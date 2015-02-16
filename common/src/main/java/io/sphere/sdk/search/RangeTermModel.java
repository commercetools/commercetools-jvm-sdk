package io.sphere.sdk.search;

public interface RangeTermModel<T, V extends Comparable<? super V>> {

    public RangedFilterSearchModel<T, V> filter();

    public RangedFacetSearchModel<T, V> facet();
}