package io.sphere.sdk.search.model;

import io.sphere.sdk.search.TermFacetAndFilterExpression;

import java.util.function.Function;

/**
 * Model to build term facets and filters.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public class TermFacetAndFilterSearchModel<T, V> extends TermFacetAndFilterBaseSearchModel<T, V> {

    TermFacetAndFilterSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        super(searchModel, typeSerializer);
    }

    @Override
    public TermFacetAndFilterExpression<T> by(final V value) {
        return super.by(value);
    }

    @Override
    public TermFacetAndFilterExpression<T> byAny(final Iterable<V> values) {
        return super.byAny(values);
    }

    @Override
    public TermFacetAndFilterExpression<T> byAll(final Iterable<V> values) {
        return super.byAll(values);
    }

    /**
     * Creates an instance of the search model to generate term faceted search expressions.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param typeSerializer the function to convert the provided value to a string accepted by Commercetools Platform
     * @param <T> type of the resource
     * @param <V> type of the value
     * @return new instance of TermFacetAndFilterSearchModel
     */
    public static <T, V> TermFacetAndFilterSearchModel<T, V> of(final String attributePath, final Function<V, String> typeSerializer) {
        return new TermFacetAndFilterSearchModel<>(new SearchModelImpl<>(attributePath), typeSerializer);
    }
}
