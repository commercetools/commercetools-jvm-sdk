package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class TermFilterSearchModel<T, V> extends FilterSearchModel<T, V> {

    TermFilterSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer) {
        super(parent, typeSerializer);
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
}
