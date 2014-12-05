package io.sphere.sdk.search;

class TermFacetExpression<T> extends TermExpression<T> implements FacetExpression<T> {

    public TermFacetExpression(final SearchModel<T> searchModel, final Iterable<String> terms) {
        super(searchModel, terms);
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
