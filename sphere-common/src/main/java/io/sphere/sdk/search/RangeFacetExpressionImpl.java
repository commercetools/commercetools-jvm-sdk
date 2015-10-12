package io.sphere.sdk.search;

import javax.annotation.Nullable;

class RangeFacetExpressionImpl<T, V extends Comparable<? super V>> extends RangeExpression<T, V> implements RangeFacetExpression<T> {

    RangeFacetExpressionImpl(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, final Iterable<? extends Range<V>> ranges, @Nullable final String alias) {
        super(searchModel, typeSerializer, ranges, alias);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FacetExpression && expression().equals(((FacetExpression) o).expression());
    }
}
