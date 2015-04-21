package io.sphere.sdk.queries;

final class SimpleQuerySort<T> extends QuerySortBase<T> {
    private final String sphereSortExpression;

    SimpleQuerySort(final String sphereSortExpression) {
        this.sphereSortExpression = sphereSortExpression;
    }

    @Override
    public String toSphereSort() {
        return sphereSortExpression;
    }
}
