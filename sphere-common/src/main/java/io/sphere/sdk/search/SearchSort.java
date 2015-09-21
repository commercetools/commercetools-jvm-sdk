package io.sphere.sdk.search;

public interface SearchSort<T> {
    /**
     * returns a sort expression.
     * Example: dog.age asc
     * @return String with unescaped sphere sort expression
     */
    String toSphereSort();

    static <T> SearchSort<T> of(final String sphereSortExpression) {
        return new SimpleSearchSort<>(sphereSortExpression);
    }
}
