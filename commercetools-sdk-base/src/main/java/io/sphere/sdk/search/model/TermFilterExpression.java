package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.function.Function;

import static io.sphere.sdk.utils.SphereInternalUtils.requireNonEmpty;

public class TermFilterExpression<T, V> extends TermExpression<T, V> implements FilterExpression<T> {

    public TermFilterExpression(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, final Iterable<V> terms) {
        super(searchModel, typeSerializer, requireNonEmpty(terms), null, false);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FilterExpression && expression().equals(((FilterExpression) o).expression());
    }
}
