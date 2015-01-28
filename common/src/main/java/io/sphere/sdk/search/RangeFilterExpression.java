package io.sphere.sdk.search;

class RangeFilterExpression<T, V extends Comparable<? super V>> extends RangeExpression<T, V> implements FilterExpression<T> {

    RangeFilterExpression(final SearchModel<T> searchModel, final Iterable<Range<V>> ranges, final TypeSerializer<V> typeSerializer) {
        super(searchModel, ranges, typeSerializer);
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
