package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilteredFacetExpression;
import io.sphere.sdk.search.TermFacetExpression;

import java.util.function.Function;

/**
 * Model to build term facets.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public class TermFacetSearchModelImpl<T, V> extends TermFacetBaseSearchModel<T, V> implements TermFacetSearchModel<T, V> {

    protected TermFacetSearchModelImpl(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, final String alias, final Boolean isCountingProducts) {
        super(searchModel, typeSerializer, alias, isCountingProducts);
    }

    protected TermFacetSearchModelImpl(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        super(searchModel, typeSerializer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetSearchModel<T, V> withAlias(final String alias) {
        return new TermFacetSearchModelImpl<>(searchModel, typeSerializer, alias, isCountingProducts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetSearchModel<T, V> withCountingProducts(final Boolean isCountingProducts) {
        return new TermFacetSearchModelImpl<>(searchModel, typeSerializer, alias, isCountingProducts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetExpression<T> allTerms() {
        return super.allTerms();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilteredFacetExpression<T> onlyTerm(final V value) {
        return super.onlyTerm(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilteredFacetExpression<T> onlyTerm(final Iterable<V> values) {
        return super.onlyTerm(values);
    }

    /**
     * Creates an instance of the search model to generate term facet expressions.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param typeSerializer the function to convert the provided value to a string accepted by Commercetools Platform
     * @param <T> type of the resource
     * @param <V> type of the value
     * @return new instance of TermFacetSearchModel
     */
    public static <T, V> TermFacetSearchModelImpl<T, V> of(final String attributePath, final Function<V, String> typeSerializer) {
        return new TermFacetSearchModelImpl<>(new SearchModelImpl<>(attributePath), typeSerializer);
    }


}
