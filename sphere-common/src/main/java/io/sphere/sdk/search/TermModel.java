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
}