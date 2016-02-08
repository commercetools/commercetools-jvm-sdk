package io.sphere.sdk.search;

public interface SortExpression<T> extends SearchExpression<T> {

    static <T> SortExpression<T> of(final String sphereSortExpression) {
        return new SimpleSortExpression<>(sphereSortExpression);
    }
}
