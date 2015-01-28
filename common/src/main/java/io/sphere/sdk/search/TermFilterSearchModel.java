package io.sphere.sdk.search;

import java.util.Optional;

import static java.util.Arrays.asList;

public class TermFilterSearchModel<T, V> extends SearchModelImpl<T> {
    protected TypeSerializer<V> typeSerializer;

    TermFilterSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final TypeSerializer<V> typeSerializer) {
        super(parent, pathSegment);
        this.typeSerializer = typeSerializer;
    }

    public FilterExpression<T> is(final V value) {
        return isIn(asList(value));
    }

    public FilterExpression<T> isIn(final Iterable<V> values) {
        return new TermFilterExpression<>(this, values, typeSerializer);
    }
}
