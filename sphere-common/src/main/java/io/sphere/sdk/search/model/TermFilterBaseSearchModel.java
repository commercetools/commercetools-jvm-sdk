package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.FilterExpression;

import java.util.List;
import java.util.function.Function;

import static io.sphere.sdk.utils.SphereInternalUtils.toStream;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

/**
 * Model to build term filters.
 * This class is abstract to force the subclass to select the methods that need to be highlighted and/or extended.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
abstract class TermFilterBaseSearchModel<T, V> extends Base implements FilterSearchModel<T, V> {
    protected final SearchModel<T> searchModel;
    protected final Function<V, String> typeSerializer;

    TermFilterBaseSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        this.searchModel = searchModel;
        this.typeSerializer = typeSerializer;
    }

    @Override
    public List<FilterExpression<T>> by(final V value) {
        return singletonList(filterBy(value));
    }

    @Override
    public List<FilterExpression<T>> byAny(final Iterable<V> values) {
        return singletonList(filterBy(values));
    }

    @Override
    public List<FilterExpression<T>> byAll(final Iterable<V> values) {
        return toStream(values)
                .map(value -> filterBy(value))
                .collect(toList());
    }

    @Override
    public List<FilterExpression<T>> byAnyAsString(final Iterable<String> values) {
        return singletonList(filterByAsString(values));
    }

    @Override
    public List<FilterExpression<T>> byAllAsString(final Iterable<String> values) {
        return toStream(values)
                .map(value -> filterByAsString(value))
                .collect(toList());
    }

    /**
     * Generates an expression to select all elements with attributes matching any of the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with either "red" or "blue" value.
     * @param values the values to filter by
     * @return a filter expression for the given values
     * @deprecated use {@link TermFilterBaseSearchModel#byAny} instead
     */
    @Deprecated
    public List<FilterExpression<T>> by(final Iterable<V> values) {
        return byAny(values);
    }

    private TermFilterExpression<T, V> filterBy(final V value) {
        return filterBy(singletonList(value));
    }

    private TermFilterExpression<T, V> filterBy(final Iterable<V> values) {
        return new TermFilterExpression<>(searchModel, typeSerializer, values);
    }

    private TermFilterExpression<T, String> filterByAsString(final String value) {
        return filterByAsString(singletonList(value));
    }

    private TermFilterExpression<T, String> filterByAsString(final Iterable<String> values) {
        return new TermFilterExpression<>(searchModel, TypeSerializer.ofString(), values);
    }
}
