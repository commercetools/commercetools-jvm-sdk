package io.sphere.sdk.search;

class TermFacetExpression<T, V> extends TermExpression<T, V> implements FacetExpression<T> {

    TermFacetExpression(final SearchModel<T> searchModel, final Iterable<V> terms, final TypeSerializer<V> typeSerializer) {
        super(searchModel, terms, typeSerializer);
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
