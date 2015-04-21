package io.sphere.sdk.search;

import java.util.Optional;

import static java.util.Arrays.asList;

public class FilterSearchModel<T, V> extends SearchModelImpl<T> {
    protected TypeSerializer<V> typeSerializer;

    FilterSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final TypeSerializer<V> typeSerializer) {
        super(parent, pathSegment);
        this.typeSerializer = typeSerializer;
    }

    public FilterExpression<T> exactly(final V value) {
        return exactly(asList(value));
    }

    public FilterExpression<T> exactly(final Iterable<V> values) {
        return new TermFilterExpression<>(this, typeSerializer, values);
    }
}
