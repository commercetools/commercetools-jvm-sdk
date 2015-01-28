package io.sphere.sdk.search;

class RangeFacetExpression<T, V extends Comparable<? super V>> extends RangeExpression<T, V> implements FacetExpression<T> {

    RangeFacetExpression(final SearchModel<T> searchModel, final Iterable<Range<V>> ranges, final TypeSerializer<V> typeSerializer) {
        super(searchModel, ranges, typeSerializer);
    }

    @Override
    public String toSphereFacet() {
        return toSphereSearchExpression();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FacetExpression && toSphereFacet().equals(((FacetExpression) o).toSphereFacet());
    }
}
