package io.sphere.sdk.search;

class RangeFilterExpression<T, V extends Comparable<? super V>> extends RangeExpression<T, V> implements FilterExpression<T> {

    RangeFilterExpression(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, final Iterable<FilterRange<V>> ranges) {
        super(searchModel, typeSerializer, ranges, null);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FilterExpression && toSearchExpression().equals(((FilterExpression) o).toSearchExpression());
    }
}
