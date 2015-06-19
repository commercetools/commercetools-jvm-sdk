package io.sphere.sdk.expansion;

import io.sphere.sdk.models.Base;

abstract class ExpansionPathBase<T> extends Base implements ExpansionPath<T> {

    @Override
    public final boolean equals(final Object o) {
        return o != null && (o instanceof ExpansionPath) && ((ExpansionPath) o).toSphereExpand().equals(toSphereExpand());
    }

    @Override
    public final int hashCode() {
        return toSphereExpand().hashCode();
    }
}
