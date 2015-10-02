package io.sphere.sdk.search;

public interface TermModel<T, V> {

    /**
     * Eventually generates an expression to filter elements by certain values.
     * @return the model to specify the values to filter by
     */
    FilterSearchModel<T, V> filtered();

    /**
     * Eventually generates an expression to obtain the facets of the attribute for certain values.
     * @return the model to specify the values from which to obtain the facets
     */
    FacetSearchModel<T, V> faceted();

    /**
     * Eventually generates an expression to obtain term facets, as well as expressions to filter elements by certain values,
     * in both cases using the same attribute path. The model requires simple Strings, to allow maximal flexibility.
     * @return the faceted search model for this instance
     */
    FacetedSearchModel<T> facetedSearch();

}