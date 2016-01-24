package io.sphere.sdk.search.model;

import io.sphere.sdk.search.TermFacetAndFilterExpression;

/**
 * Model to build facets and filters.
 * @param <T> type of the resource
 */
public interface FacetAndFilterSearchModel<T> {

    /**
     * Generates an expression to select all elements, without filtering, along with the facet for all terms for this attribute.
     * @return a bundle of an empty filter expression and a facet expression for all terms
     */
    TermFacetAndFilterExpression<T> allTerms();

    /**
     * Generates an expression to select all elements with the given attribute value, along with the facet for all terms for this attribute.
     * For example: filtering by "red" color would select only those elements with "red" value, while the color facet would contain all possible values.
     * @param value the value to filter by
     * @return a bundle of the filter expressions for the given value and a facet expression for all terms
     */
    TermFacetAndFilterExpression<T> is(final String value);

    /**
     * @see #containsAny(Iterable)
     */
    default TermFacetAndFilterExpression<T> isIn(final Iterable<String> values) {
        return containsAny(values);
    }

    /**
     * Generates an expression to select all elements with attributes matching any of the given values, along with the facet for all terms for this attribute.
     * For example: filtering by ["red", "blue"] color would select only those elements with either "red" or "blue" value, while the color facet would contain all possible values.
     * @param values the values to filter by
     * @return a bundle of the filter expressions for the given values and a facet expression for all terms
     */
    TermFacetAndFilterExpression<T> containsAny(final Iterable<String> values);

    /**
     * Generates an expression to select all elements with attributes matching all the given values, along with the facet for all terms for this attribute.
     * For example: filtering by ["red", "blue"] color would select only those elements with both "red" and "blue" values, while the color facet would contain all possible values.
     * @param values the values to filter by
     * @return a bundle of the filter expressions for the given values and a facet expression for all terms
     */
    TermFacetAndFilterExpression<T> containsAll(final Iterable<String> values);

    /**
     * @deprecated use {@link #is(String)} instead
     */
    @Deprecated
    default TermFacetAndFilterExpression<T> by(final String value) {
        return is(value);
    }

    /**
     * @deprecated use {@link #containsAny(Iterable)} instead
     */
    @Deprecated
    default TermFacetAndFilterExpression<T> byAny(final Iterable<String> values) {
        return containsAny(values);
    }

    /**
     * @deprecated use {@link #containsAll(Iterable)} instead
     */
    @Deprecated
    default TermFacetAndFilterExpression<T> byAll(final Iterable<String> values) {
        return containsAll(values);
    }
}
