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
     * Gets an faceted search model (expecting simple Strings) while keeping the same search model path as this.
     * This untyped search model allows you to build filters and facets, both range and term models.
     * @return the faceted search model for this instance
     */
    FacetedSearchModel<T> facetedSearch();

}