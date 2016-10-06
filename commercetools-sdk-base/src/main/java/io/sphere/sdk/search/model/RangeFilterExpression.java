package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.function.Function;

class RangeFilterExpression<T, V extends Comparable<? super V>> extends RangeExpression<T, V> implements FilterExpression<T> {

    RangeFilterExpression(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, final Iterable<FilterRange<V>> ranges) {
        super(searchModel, typeSerializer, ranges, null, false);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FilterExpression && expression().equals(((FilterExpression) o).expression());
    }
}
