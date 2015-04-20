package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;

abstract class SortBase<T> extends Base implements Sort<T> {
    @Override
    public final boolean equals(Object o) {
        return o != null && o instanceof Sort && toSphereSort().equals(((Sort) o).toSphereSort());
    }

    @Override
    public final int hashCode() {
        return toSphereSort().hashCode();
    }


}
