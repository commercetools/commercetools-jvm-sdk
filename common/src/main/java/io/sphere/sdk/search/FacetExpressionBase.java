package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

abstract class FacetExpressionBase<T> extends Base implements FacetExpression<T> {

    protected String buildQuery(final SearchModel<T> model, final String definition) {
        final String current = (model.getPathSegment().isPresent() ? model.getPathSegment().get() : "") + definition;

        if (model.getParent().isPresent()) {
            SearchModel<T> parent = model.getParent().get();
            return buildQuery(parent, parent.getPathSegment().isPresent() ? "." + current : current);
        } else {
            return current;
        }
    }

    @Override
    public String toString() {
        return toSphereFacet();
    }

    @Override
    public final boolean equals(Object o) {
        return o != null && o instanceof FacetExpression && toSphereFacet().equals(((FacetExpression) o).toSphereFacet());
    }

    @Override
    public final int hashCode() {
        return toSphereFacet().hashCode();
    }
}
