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
     * Eventually generates an expression to obtain range facets, as well as expressions to filter elements by certain ranges,
     * in both cases using the same attribute path. The model requires simple Strings, to allow maximal flexibility.
     * @return the faceted search model for this instance
     */
    //FacetAndFilterSearchModel<T> facetedAndFiltered();
}