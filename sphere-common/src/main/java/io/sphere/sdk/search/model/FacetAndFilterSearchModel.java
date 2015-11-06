package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.TermFacetAndFilterExpression;
import io.sphere.sdk.search.TermFacetExpression;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

abstract class FacetAndFilterSearchModel<T, V> extends SearchModelImpl<T> {
    private final TermFacetExpression<T> facetExpression;
    private final TermFilterSearchModel<T, V> filterSearchModel;

    FacetAndFilterSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer) {
        super(parent, null);
        this.facetExpression = new TermFacetSearchModel<>(parent, typeSerializer).allTerms();
        this.filterSearchModel = new TermFilterSearchModel<>(parent, typeSerializer);
    }

    /**
     * Generates an expression to select all elements with the given attribute value.
     * For example: filtering by "red" would select only those elements with "red" value.
     * @param value the value to filter by
     * @return a filter expression for the given value
     */
    public TermFacetAndFilterExpression<T> by(final V value) {
        return expression(filterSearchModel.by(value));
    }

    /**
     * Generates an expression to select all elements with attributes matching any of the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with either "red" or "blue" value.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    public TermFacetAndFilterExpression<T> byAny(final Iterable<V> values) {
        return expression(filterSearchModel.byAny(values));
    }

    /**
     * Generates an expression to select all elements with attributes matching all the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with both "red" and "blue" values.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    public TermFacetAndFilterExpression<T> byAll(final Iterable<V> values) {
        return expression(filterSearchModel.byAll(values));
    }

    /**
     * Generates an expression to select all elements with attributes matching any of the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with either "red" or "blue" value.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    public TermFacetAndFilterExpression<T> byAnyAsString(final Iterable<String> values) {
        return expression(filterSearchModel.byAnyAsString(values));
    }

    /**
     * Generates an expression to select all elements with attributes matching all the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with both "red" and "blue" values.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    public TermFacetAndFilterExpression<T> byAllAsString(final Iterable<String> values) {
        return expression(filterSearchModel.byAllAsString(values));
    }

    private TermFacetAndFilterExpressionImpl<T> expression(final List<FilterExpression<T>> filterExpressions) {
        return new TermFacetAndFilterExpressionImpl<>(facetExpression, filterExpressions);
    }
}
