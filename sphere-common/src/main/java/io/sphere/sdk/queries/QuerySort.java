package io.sphere.sdk.queries;

public interface QuerySort<T> {
    /**
     * returns a sort expression.
     * Example: dog.age asc
     * @return String with unescaped sphere sort expression
     */
    String toSphereSort();

    static <T> QuerySort<T> of(final String sphereSortExpression) {
        return new SimpleQuerySort<>(sphereSortExpression);
    }
}
