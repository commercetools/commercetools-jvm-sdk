package io.sphere.sdk.search;

class RangeFacetExpression<T> extends RangeExpression<T> implements FacetExpression<T> {

    RangeFacetExpression(final SearchModel<T> searchModel, final Iterable<String> ranges) {
        super(searchModel, ranges);
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
