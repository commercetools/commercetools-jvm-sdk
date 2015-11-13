package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.TermFacetAndFilterExpression;
import io.sphere.sdk.search.TermFacetExpression;

import java.util.List;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Model to build term facets and filters.
 * @param <T> type of the resource
 */
public class TermFacetAndFilterSearchModel<T> extends Base {
    protected static final TypeSerializer<String> TYPE_SERIALIZER = TypeSerializer.ofString();
    protected final SearchModel<T> searchModel;
    private final TermFacetExpression<T> facetExpression;
    private final TermFilterSearchModel<T, String> filterSearchModel;

    TermFacetAndFilterSearchModel(final SearchModel<T> searchModel) {
        this.searchModel = searchModel;
        this.facetExpression = new TermFacetSearchModel<>(searchModel, TYPE_SERIALIZER).allTerms();
        this.filterSearchModel = new TermFilterSearchModel<>(searchModel, TYPE_SERIALIZER);
    }

    /**
     * Generates an expression to select all elements with the given attribute value.
     * For example: filtering by "red" would select only those elements with "red" value.
     * @param value the value to filter by
     * @return a filter expression for the given value
     */
    public TermFacetAndFilterExpression<T> by(final String value) {
        return byAny(singletonList(value));
    }

    /**
     * Generates an expression to select all elements with attributes matching any of the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with either "red" or "blue" value.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    public TermFacetAndFilterExpression<T> byAny(final Iterable<String> values) {
        return validateAndBuildExpression(values, v -> filterSearchModel.byAnyAsString(v));
    }

    /**
     * Generates an expression to select all elements with attributes matching all the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with both "red" and "blue" values.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    public TermFacetAndFilterExpression<T> byAll(final Iterable<String> values) {
        return validateAndBuildExpression(values, v -> filterSearchModel.byAll(v));
    }

    /**
     * Creates an instance of the search model to generate term faceted search expressions.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param <T> type of the resource
     * @return new instance of TermFacetAndFilterSearchModel
     */
    public static <T> TermFacetAndFilterSearchModel<T> of(final String attributePath) {
        return new TermFacetAndFilterSearchModel<>(new SearchModelImpl<>(attributePath));
    }

    private TermFacetAndFilterExpression<T> validateAndBuildExpression(final Iterable<String> values,
                                                                       final Function<Iterable<String>, List<FilterExpression<T>>> f) {
        final List<FilterExpression<T>> filterExpressions;
        if (values.iterator().hasNext()) {
            filterExpressions = f.apply(values);
        } else {
            filterExpressions = emptyList();
        }
        return TermFacetAndFilterExpression.of(facetExpression, filterExpressions);
    }
}
