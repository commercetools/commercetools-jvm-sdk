package io.sphere.sdk.search;

import javax.annotation.Nullable;

import static java.util.Collections.emptyList;

public class TermFacetExpression<T, V> extends TermExpression<T, V> implements FacetExpressionBase<T> {
    SearchModel<T> model;

    TermFacetExpression(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, @Nullable final String alias) {
        super(searchModel, typeSerializer, emptyList(), alias);
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
