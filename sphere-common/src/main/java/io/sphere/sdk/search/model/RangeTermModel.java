package io.sphere.sdk.search.model;

interface RangeTermModel<T, V extends Comparable<? super V>> {

    /**
     * Eventually generates an expression to filter elements by certain range of values.
     * @return the model to specify the range of values to filter by
     */
    RangeFilterSearchModel<T, V> filtered();

    /**
     * Eventually generates an expression to obtain the facets of the attribute for certain range of values.
     * @return the model to specify the range of values from which to obtain the facets
     */
    RangeFacetSearchModel<T, V> faceted();

    /**
     * Eventually generates both an expression to obtain the facets of the attribute for all ranges
     * and an expression to filter elements by certain range of values.
     * @return the model to specify the values from which to obtain the facets and to filter by
     */
    RangeFacetAndFilterSearchModel<T, V> facetedAndFiltered();

}