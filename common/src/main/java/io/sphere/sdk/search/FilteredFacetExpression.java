package io.sphere.sdk.search;

import java.util.Optional;

public class FilteredFacetExpression<T, V> extends TermExpression<T, V> implements FacetExpressionBase<T> {

    FilteredFacetExpression(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, final Iterable<V> terms, final Optional<String> alias) {
        super(searchModel, typeSerializer, terms, alias);
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
    public boolean equals(Object o) {
        return o != null && o instanceof FacetExpression && toSphereFacet().equals(((FacetExpression) o).toSphereFacet());
    }
}
