package io.sphere.sdk.search;

public class FilteredFacetExpression<T, V> extends TermExpression<T, V> implements FacetExpressionBase<T> {

    FilteredFacetExpression(final SearchModel<T> searchModel, final Iterable<V> terms, final TypeSerializer<V> typeSerializer) {
        super(searchModel, terms, typeSerializer);
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
