package io.sphere.sdk.queries;

final class SimpleSort<T> extends SortBase<T> {
    private final String sphereSortExpression;

    SimpleSort(final String sphereSortExpression) {
        this.sphereSortExpression = sphereSortExpression;
    }

    @Override
    public String toSphereSort() {
        return sphereSortExpression;
    }
}
