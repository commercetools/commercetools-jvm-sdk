package io.sphere.sdk.search;

import java.util.Optional;

class RangeFilterExpression<T, V extends Comparable<? super V>> extends RangeExpression<T, V> implements FilterExpression<T> {

    RangeFilterExpression(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, final Iterable<FilterRange<V>> ranges) {
        super(searchModel, typeSerializer, ranges, Optional.empty());
    }

    @Override
    public String toSphereFilter() {
        return toSphereSearchExpression();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FilterExpression && toSphereFilter().equals(((FilterExpression) o).toSphereFilter());
    }
}
