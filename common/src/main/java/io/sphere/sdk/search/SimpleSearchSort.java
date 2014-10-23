package io.sphere.sdk.search;

final class SimpleSearchSort<T> extends SearchSortBase<T> {
    private final String sphereSortExpression;

    SimpleSearchSort(final String sphereSortExpression) {
        this.sphereSortExpression = sphereSortExpression;
    }

    @Override
    public String toSphereSort() {
        return sphereSortExpression;
    }
}
