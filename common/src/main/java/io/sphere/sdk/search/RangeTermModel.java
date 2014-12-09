package io.sphere.sdk.search;

public interface RangeTermModel<T, V extends Comparable<? super V>> {

    public RangeTermFilterSearchModel<T, V> filter();

    public RangeTermFacetSearchModel<T, V> facet();
}