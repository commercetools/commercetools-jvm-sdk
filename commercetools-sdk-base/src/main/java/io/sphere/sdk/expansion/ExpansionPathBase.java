package io.sphere.sdk.expansion;

import io.sphere.sdk.models.Base;

abstract class ExpansionPathBase<T> extends Base implements ExpansionPath<T> {

    @Override
    public final boolean equals(final Object o) {
        return equals(this, o);
    }

    public static <T> boolean equals(final ExpansionPath<T> expansionPath, final Object o) {
        return o != null && (o instanceof ExpansionPath) && ((ExpansionPath) o).toSphereExpand().equals(expansionPath.toSphereExpand());
    }

    @Override
    public final int hashCode() {
        return toSphereExpand().hashCode();
    }
}
