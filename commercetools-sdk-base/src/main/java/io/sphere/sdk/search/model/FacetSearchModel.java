package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilteredFacetExpression;
import io.sphere.sdk.search.TermFacetExpression;

import javax.annotation.Nullable;

/**
 * Model to build facets.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public interface FacetSearchModel<T, V> {

    /**
     * The search model for the facet.
     * @return the facet search model
     */
    SearchModel<T> getSearchModel();

    /**
     * The alias related to the facet.
     * @return the facet alias
     */
    @Nullable
    String getAlias();

    /**
     * Whether the facet is counting products.
     * @return whether the facet is counting products
     */
    @Nullable
    Boolean isCountingProducts();

    /**
     * Allows to set an alias to identify the facet.
     * @param alias the identifier to use for the facet
     * @return a new facet search model identical to the current one, but with the given alias
     */
    FacetSearchModel<T, V> withAlias(final String alias);

    /**
     * Allows to enable/disable the counting of products.
     * @param isCountingProducts whether to count products or not
     * @return a new facet search model identical to the current one, but with the counting of products enabled/disabled
     */
    FacetSearchModel<T, V> withCountingProducts(final Boolean isCountingProducts);

    /**
     * Generates an expression to obtain the facets of the attribute for all values.
     * For example: a possible faceted classification could be ["red": 4, "yellow": 2, "blue": 1].
     * @return a facet expression for all values
     */
    TermFacetExpression<T> allTerms();

    /**
     * Generates an expression to obtain the facet of the attribute for only the given value.
     * For example: a possible faceted classification for "red" could be ["red": 4].
     * @param value the value from which to obtain the facet
     * @return a facet expression for only the given value
     */
    FilteredFacetExpression<T> onlyTerm(final V value);

    /**
     * Generates an expression to obtain the facets of the attribute for only the given values.
     * For example: a possible faceted classification for ["red", "blue"] could be ["red": 4, "blue": 1].
     * @param values the values from which to obtain the facets
     * @return a facet expression for only the given values
     */
    FilteredFacetExpression<T> onlyTerm(final Iterable<V> values);

    /**
     * Generates an expression to obtain the facets of the attribute for only the given values.
     * For example: a possible faceted classification for ["red", "blue"] could be ["red": 4, "blue": 1].
     * @param values the values from which to obtain the facets
     * @return a facet expression for only the given values
     */
    FilteredFacetExpression<T> onlyTermAsString(final Iterable<String> values);
}
