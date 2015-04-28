package io.sphere.sdk.search;

public interface RangeTermModel<T, V extends Comparable<? super V>> {

    RangedFilterSearchModel<T, V> filterBy();

    RangedFacetSearchModel<T, V> facetOf();
}