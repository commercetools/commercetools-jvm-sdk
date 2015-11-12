package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;
import java.util.function.Function;

/**
 * Model to build term filters.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public class TermFilterSearchModel<T, V> extends TermFilterBaseSearchModel<T, V> {

    TermFilterSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        super(searchModel, typeSerializer);
    }

    @Override
    public List<FilterExpression<T>> by(final V value) {
        return super.by(value);
    }

    @Override
    public List<FilterExpression<T>> byAny(final Iterable<V> values) {
        return super.byAny(values);
    }

    @Override
    public List<FilterExpression<T>> byAll(final Iterable<V> values) {
        return super.byAll(values);
    }

    public static <T, V> TermFilterSearchModel<T, V> of(final String path, final Function<V, String> typeSerializer) {
        return new TermFilterSearchModel<>(new SearchModelImpl<>(path), typeSerializer);
    }
}
