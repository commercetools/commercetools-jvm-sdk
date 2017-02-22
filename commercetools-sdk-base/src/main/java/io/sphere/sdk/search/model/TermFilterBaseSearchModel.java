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
abstract class TermFilterBaseSearchModel<T, V> extends Base implements FilterSearchModel<T, V>, ExistsAndMissingFilterSearchModelSupport<T> {
    protected final SearchModel<T> searchModel;
    protected final Function<V, String> typeSerializer;

    TermFilterBaseSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        this.searchModel = searchModel;
        this.typeSerializer = typeSerializer;
    }

    @Override
    public SearchModel<T> getSearchModel() {
        return searchModel;
    }

    @Override
    public List<FilterExpression<T>> exists() {
        return ExistsAndMissingFilterSearchModelSupportUtils.exists(searchModel);
    }

    @Override
    public List<FilterExpression<T>> missing() {
        return ExistsAndMissingFilterSearchModelSupportUtils.missing(searchModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> is(final V value) {
        return singletonList(filterBy(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> containsAny(final Iterable<V> values) {
        return singletonList(filterBy(values));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> containsAll(final Iterable<V> values) {
        return toStream(values)
                .map(value -> filterBy(value))
                .collect(toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> containsAnyAsString(final Iterable<String> values) {
        return singletonList(filterByAsString(values));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> containsAllAsString(final Iterable<String> values) {
        return toStream(values)
                .map(value -> filterByAsString(value))
                .collect(toList());
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
