package io.sphere.sdk.queries;

public interface Sort {
    /**
     * returns a sort expression.
     * Example: dog.age asc
     * @return String with unescaped sphere sort expression
     */
    String toSphereSort();
}
