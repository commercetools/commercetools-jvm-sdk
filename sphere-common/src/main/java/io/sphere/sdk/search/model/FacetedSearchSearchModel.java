package io.sphere.sdk.search.model;

import io.sphere.sdk.search.TermFacetedSearchExpression;

/**
 * Model to build facets and filters.
 * @param <T> type of the resource
 */
public interface FacetedSearchSearchModel<T> {

    /**
     * Generates an expression to select all elements, without filtering, along with the facet for all terms for this attribute.
     * @return a bundle of an empty filter expression and a facet expression for all terms
     */
    TermFacetedSearchExpression<T> allTerms();

    /**
     * Generates an expression to select all elements with the given attribute value, along with the facet for all terms for this attribute.
     * For example: filtering by "red" color would select only those elements with "red" value, while the color facet would contain all possible values.
     * @param value the value to filter by
     * @return a bundle of the filter expressions for the given value and a facet expression for all terms
     */
    TermFacetedSearchExpression<T> is(final String value);

    /**
     * Generates an expression to select all elements with attributes matching any of the given values, along with the facet for all terms for this attribute.
     * For example: filtering by ["red", "blue"] color would select only those elements with either "red" or "blue" value, while the color facet would contain all possible values.
     *
     * <p>Alias for {@link #containsAny(Iterable)}.</p>
     *
     * @param values the values to filter by
     * @return a bundle of the filter expressions for the given values and a facet expression for all terms
     */
    default TermFacetedSearchExpression<T> isIn(final Iterable<String> values) {
        return containsAny(values);
    }

    /**
     * Generates an expression to select all elements with attributes matching any of the given values, along with the facet for all terms for this attribute.
     * For example: filtering by ["red", "blue"] color would select only those elements with either "red" or "blue" value, while the color facet would contain all possible values.
     * @param values the values to filter by
     * @return a bundle of the filter expressions for the given values and a facet expression for all terms
     */
    TermFacetedSearchExpression<T> containsAny(final Iterable<String> values);

    /**
     * Generates an expression to select all elements with attributes matching all the given values, along with the facet for all terms for this attribute.
     * For example: filtering by ["red", "blue"] color would select only those elements with both "red" and "blue" values, while the color facet would contain all possible values.
     * @param values the values to filter by
     * @return a bundle of the filter expressions for the given values and a facet expression for all terms
     */
    TermFacetedSearchExpression<T> containsAll(final Iterable<String> values);

    /**
     * @deprecated use {@link #is(String)} instead
     * @param value deprecated
     * @return deprecated
     */
    @Deprecated
    default TermFacetedSearchExpression<T> by(final String value) {
        return is(value);
    }

    /**
     * @deprecated use {@link #containsAny(Iterable)} instead
     * @param values deprecated
     * @return deprecated
     */
    @Deprecated
    default TermFacetedSearchExpression<T> byAny(final Iterable<String> values) {
        return containsAny(values);
    }

    /**
     * @deprecated use {@link #containsAll(Iterable)} instead
     * @param values deprecated
     * @return deprecated
     */
    @Deprecated
    default TermFacetedSearchExpression<T> byAll(final Iterable<String> values) {
        return containsAll(values);
    }
}
