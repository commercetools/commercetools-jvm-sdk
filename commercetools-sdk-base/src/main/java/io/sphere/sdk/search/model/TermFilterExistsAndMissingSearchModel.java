package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;
import java.util.function.Function;

/**
 * Model to build term filters with exits and missing predicates.
 * .
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public interface TermFilterExistsAndMissingSearchModel<T, V> extends TermFilterSearchModel<T, V>, ExistsAndMissingFilterSearchModelSupport<T> {
    @Override
    List<FilterExpression<T>> containsAll(final Iterable<V> values);

    @Override
    List<FilterExpression<T>> containsAllAsString(final Iterable<String> values);

    @Override
    List<FilterExpression<T>> containsAny(final Iterable<V> values);

    @Override
    List<FilterExpression<T>> containsAnyAsString(final Iterable<String> values);

    @Override
    List<FilterExpression<T>> is(final V value);

    @Override
    List<FilterExpression<T>> isIn(final Iterable<V> values);

    /**
     * Creates an instance of the search model to generate term filters.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param typeSerializer the function to convert the provided value to a string accepted by Commercetools Platform
     * @param <T> type of the resource
     * @param <V> type of the value
     * @return new instance of TermFilterSearchModel
     */
    static <T, V> TermFilterExistsAndMissingSearchModel<T, V> of(final String attributePath, final Function<V, String> typeSerializer) {
        return new TermFilterSearchModelImpl<>(new SearchModelImpl<>(attributePath), typeSerializer);
    }
}
