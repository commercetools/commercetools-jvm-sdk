package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;

abstract class QuerySortBase<T> extends Base implements QuerySort<T> {
    @Override
    public final boolean equals(Object o) {
        return o != null && o instanceof QuerySort && toSphereSort().equals(((QuerySort) o).toSphereSort());
    }

    @Override
    public final int hashCode() {
        return toSphereSort().hashCode();
    }


}
