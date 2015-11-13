package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.TermFacetAndFilterExpression;
import io.sphere.sdk.search.TermFacetExpression;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Model to build term facets and filters.
 * This class is abstract to force the subclass to select the methods that need to be highlighted and/or extended.
 * @param <T> type of the resource
 */
abstract class TermFacetAndFilterBaseSearchModel<T> extends Base {
    protected static final TypeSerializer<String> TYPE_SERIALIZER = TypeSerializer.ofString();
    protected final SearchModel<T> searchModel;
    private final TermFacetExpression<T> facetExpression;
    private final TermFilterSearchModel<T, String> filterSearchModel;

    TermFacetAndFilterBaseSearchModel(final SearchModel<T> searchModel) {
        this.searchModel = searchModel;
        this.facetExpression = new TermFacetSearchModel<>(searchModel, TYPE_SERIALIZER).allTerms();
        this.filterSearchModel = new TermFilterSearchModel<>(searchModel, TYPE_SERIALIZER);
    }

    /**
     * Generates an expression to select all elements, without filtering, along with the facet for all terms for this attribute.
     * @return a bundle of an empty filter expression and a facet expression for all terms
     */
    public TermFacetAndFilterExpression<T> allTerms() {
        return buildExpression(emptyList());
    }

    /**
     * Generates an expression to select all elements with the given attribute value, along with the facet for all terms for this attribute.
     * For example: filtering by "red" color would select only those elements with "red" value, while the color facet would contain all possible values.
     * @param value the value to filter by
     * @return a bundle of the filter expressions for the given value and a facet expression for all terms
     */
    public TermFacetAndFilterExpression<T> by(final String value) {
        return byAny(singletonList(value));
    }

    /**
     * Generates an expression to select all elements with attributes matching any of the given values, along with the facet for all terms for this attribute.
     * For example: filtering by ["red", "blue"] color would select only those elements with either "red" or "blue" value, while the color facet would contain all possible values.
     * @param values the values to filter by
     * @return a bundle of the filter expressions for the given values and a facet expression for all terms
     */
    public TermFacetAndFilterExpression<T> byAny(final Iterable<String> values) {
        return buildExpression(filterSearchModel.byAnyAsString(values));
    }

    /**
     * Generates an expression to select all elements with attributes matching all the given values, along with the facet for all terms for this attribute.
     * For example: filtering by ["red", "blue"] color would select only those elements with both "red" and "blue" values, while the color facet would contain all possible values.
     * @param values the values to filter by
     * @return a bundle of the filter expressions for the given values and a facet expression for all terms
     */
    public TermFacetAndFilterExpression<T> byAll(final Iterable<String> values) {
        return buildExpression(filterSearchModel.byAll(values));
    }

    private TermFacetAndFilterExpression<T> buildExpression(final List<FilterExpression<T>> filterExpressions) {
        return TermFacetAndFilterExpression.of(facetExpression, filterExpressions);
    }
}
