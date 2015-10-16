package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FacetExpression;
import io.sphere.sdk.search.RangeFacetExpression;

import javax.annotation.Nullable;
import java.util.function.Function;

class RangeFacetExpressionImpl<T, V extends Comparable<? super V>> extends RangeExpression<T, V> implements RangeFacetExpression<T> {

    RangeFacetExpressionImpl(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, final Iterable<? extends Range<V>> ranges, @Nullable final String alias) {
        super(searchModel, typeSerializer, ranges, alias);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FacetExpression && expression().equals(((FacetExpression) o).expression());
    }
}
