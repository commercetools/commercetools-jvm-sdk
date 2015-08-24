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

    /**
     * Gets an untyped search model (expecting simple Strings) while keeping the same search model path as this.
     * This untyped search model allows you to build filters and facets, both range and term models.
     * @return the untyped search model for this instance
     */
    default UntypedSearchModel<T> untyped() {
        return new UntypedSearchModel<>(getSearchModel(), null);
    }

    /**
     * Gets the underlying search model.
     * @return the search model for this instance
     */
    SearchModel<T> getSearchModel();
}