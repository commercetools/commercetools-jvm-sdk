package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;

final class SimpleSort<T> extends Base implements Sort<T> {
    private final String sphereSortExpression;

    SimpleSort(final String sphereSortExpression) {
        this.sphereSortExpression = sphereSortExpression;
    }

    @Override
    public String toSphereSort() {
        return sphereSortExpression;
    }
}
