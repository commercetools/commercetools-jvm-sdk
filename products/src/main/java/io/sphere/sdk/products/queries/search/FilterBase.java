package io.sphere.sdk.products.queries.search;

import io.sphere.sdk.models.Base;

abstract class FilterBase<T> extends Base implements Filter<T> {

    @Override
    public final boolean equals(Object o) {
        return o != null && o instanceof Filter && toSphereFilter().equals(((Filter) o).toSphereFilter());
    }

    @Override
    public final int hashCode() {
        return toSphereFilter().hashCode();
    }
}
