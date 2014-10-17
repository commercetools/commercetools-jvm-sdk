package io.sphere.sdk.products.queries.search;

import io.sphere.sdk.models.Base;

abstract class SearchSortBase<T> extends Base implements SearchSort<T> {
    @Override
    public final boolean equals(Object o) {
        return o != null && o instanceof SearchSort && toSphereSort().equals(((SearchSort) o).toSphereSort());
    }

    @Override
    public final int hashCode() {
        return toSphereSort().hashCode();
    }
}
