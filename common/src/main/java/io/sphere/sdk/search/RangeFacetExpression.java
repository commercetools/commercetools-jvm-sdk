package io.sphere.sdk.search;

public class RangeFacetExpression<T, V extends Comparable<? super V>> extends RangeExpression<T, V> implements FacetExpressionBase<T> {

    RangeFacetExpression(final SearchModel<T> searchModel, final Iterable<FacetRange<V>> ranges, final TypeSerializer<V> typeSerializer) {
        super(searchModel, ranges, typeSerializer);
    }

    @Override
    public String toSphereFacet() {
        return super.toSphereSearchExpression();
    }

    @Override
    public String searchPath() {
        return super.path();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FacetExpression && toSphereFacet().equals(((FacetExpression) o).toSphereFacet());
    }
}
