package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

public class Bound<T extends Comparable<? super T>> extends Base {
    private final T endpoint;
    private final BoundType type;

    /**
     * Allows to define the bound type of a range.
     * A bound identified with INCLUSIVE type indicates the endpoint is included in the range.
     * A bound identified with EXCLUSIVE type indicates the endpoint is excluded in the range.
     */
    private static enum BoundType {
        INCLUSIVE,
        EXCLUSIVE
    }

    private Bound(final T endpoint, final BoundType type) {
        this.endpoint = endpoint;
        this.type = type;
    }

    public T endpoint() {
        return endpoint;
    }

    /**
     * Determines whether the endpoint is excluded from the range or included.
     * @return true if the bound is exclusive, false if it is inclusive.
     */
    public boolean isExclusive() {
        return type.equals(BoundType.EXCLUSIVE);
    }

    /**
     * Determines whether the endpoint is included from the range or excluded.
     * @return true if the bound is inclusive, false if it is exclusive.
     */
    public boolean isInclusive() {
        return !isExclusive();
    }

    public Bound<T> withEndpoint(final T endpoint) {
        return new Bound<>(endpoint, type);
    }

    /**
     * Creates a bound with the given endpoint, excluded from the range.
     * @param endpoint the endpoint value of the given type T.
     * @return the exclusive bound with the endpoint.
     */
    public static <T extends Comparable<? super T>> Bound<T> exclusive(T endpoint) {
        return new Bound<>(endpoint, BoundType.EXCLUSIVE);
    }

    /**
     * Creates a bound with the given endpoint, included from the range.
     * @param endpoint the endpoint value of the given type T.
     * @return the inclusive bound with the endpoint.
     */
     public static <T extends Comparable<? super T>> Bound<T> inclusive(T endpoint) {
        return new Bound<>(endpoint, BoundType.INCLUSIVE);
    }
}
