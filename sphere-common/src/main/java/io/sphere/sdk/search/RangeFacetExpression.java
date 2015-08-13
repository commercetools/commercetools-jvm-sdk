package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class RangeFacetExpression<T, V extends Comparable<? super V>> extends RangeExpression<T, V> implements FacetExpressionBase<T> {

    RangeFacetExpression(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, final Iterable<? extends Range<V>> ranges, @Nullable final String alias) {
        super(searchModel, typeSerializer, ranges, alias);
    }

    @Override
    public String toSphereFacet() {
        return super.toSphereSearchExpression();
    }

    @Override
    public String resultPath() {
        return super.buildResultPath();
    }

    @Override
    public String path() {
        return super.serializedPath();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FacetExpression && toSphereFacet().equals(((FacetExpression) o).toSphereFacet());
    }
}
