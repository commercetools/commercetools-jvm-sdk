package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;
import java.util.function.Function;

/**
 * Model to build term filters.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public class TermFilterSearchModelImpl<T, V> extends TermFilterBaseSearchModel<T, V> implements TermFilterExistsAndMissingSearchModel<T, V> {

    public TermFilterSearchModelImpl(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        super(searchModel, typeSerializer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> is(final V value) {
        return super.is(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> isIn(final Iterable<V> values) {
        return super.isIn(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> containsAny(final Iterable<V> values) {
        return super.containsAny(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> containsAll(final Iterable<V> values) {
        return super.containsAll(values);
    }

    /**
     * Creates an instance of the search model to generate term filters.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param typeSerializer the function to convert the provided value to a string accepted by Commercetools Platform
     * @param <T> type of the resource
     * @param <V> type of the value
     * @return new instance of TermFilterSearchModel
     */
    public static <T, V> TermFilterSearchModelImpl<T, V> of(final String attributePath, final Function<V, String> typeSerializer) {
        return new TermFilterSearchModelImpl<>(new SearchModelImpl<>(attributePath), typeSerializer);
    }
}
