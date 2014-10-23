package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

abstract class FilterExpressionBase<T> extends Base implements FilterExpression<T> {

    @Override
    public final boolean equals(Object o) {
        return o != null && o instanceof FilterExpression && toSphereFilter().equals(((FilterExpression) o).toSphereFilter());
    }

    @Override
    public final int hashCode() {
        return toSphereFilter().hashCode();
    }
}
