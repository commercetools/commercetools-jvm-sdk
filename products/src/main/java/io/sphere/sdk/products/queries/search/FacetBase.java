package io.sphere.sdk.products.queries.search;

import io.sphere.sdk.models.Base;

abstract class FacetBase<T> extends Base implements Facet<T> {

    @Override
    public final boolean equals(Object o) {
        return o != null && o instanceof Facet && toSphereFacet().equals(((Facet) o).toSphereFacet());
    }

    @Override
    public final int hashCode() {
        return toSphereFacet().hashCode();
    }
}
