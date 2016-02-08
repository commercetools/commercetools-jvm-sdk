package io.sphere.sdk.search.model;

interface TermModel<T, V> {

    /**
     * Eventually generates an expression to filter elements by certain values.
     * @return the model to specify the values to filter by
     */
    TermFilterSearchModel<T, V> filtered();

    /**
     * Eventually generates an expression to obtain the facets of the attribute for certain values.
     * @return the model to specify the values from which to obtain the facets
     */
    TermFacetSearchModel<T, V> faceted();

    /**
     * Eventually generates both an expression to obtain the facets of the attribute for all terms
     * and an expression to filter elements by certain values.
     * @return the model to specify the values from which to obtain the facets and to filter by
     */
    TermFacetedSearchSearchModel<T> facetedAndFiltered();

}