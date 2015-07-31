package io.sphere.sdk.search;

public interface RangeTermModel<T, V extends Comparable<? super V>> {

    /**
     * Eventually generates an expression to filter elements by certain range of values.
     * @return the model to specify the range of values to filter by
     */
    RangedFilterSearchModel<T, V> filtered();

    /**
     * Eventually generates an expression to obtain the facets of the attribute for certain range of values.
     * @return the model to specify the range of values from which to obtain the facets
     */
    RangedFacetSearchModel<T, V> faceted();
}