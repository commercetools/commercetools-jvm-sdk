package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

abstract class FacetExpressionBase<T> extends Base implements FacetExpression<T> {

    @Override
    public final boolean equals(Object o) {
        return o != null && o instanceof FacetExpression && toSphereFacet().equals(((FacetExpression) o).toSphereFacet());
    }

    @Override
    public final int hashCode() {
        return toSphereFacet().hashCode();
    }
}
