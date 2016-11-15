package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

/**
 * Facet ranges should be of the form [a, b), (-∞, b) or [a, +∞).
 * @param <T> type of the range domain.
 */
public final class FacetRange<T extends Comparable<? super T>> extends Range<T> {

    private FacetRange(@Nullable final Bound<T> lowerBound, final Bound<T> upperBound) {
        super(lowerBound, upperBound);
    }

    /**
     * Creates an interval with the given lower and upper endpoints.
     * @param lowerEndpoint lower endpoint, included in the range.
     * @param upperEndpoint upper endpoint, excluded in the range.
     * @param <T> type of the range domain.
     * @return the range with the given bounds of the form [a, b).
     */
    public static <T extends Comparable<? super T>> FacetRange<T> of(final T lowerEndpoint, final T upperEndpoint) {
        return new FacetRange<>(Bound.inclusive(lowerEndpoint), Bound.exclusive(upperEndpoint));
    }

    /**
     * Creates an interval with all values that are strictly less than the given endpoint.
     * @param upperEndpoint upper endpoint, excluded in the range.
     * @param <T> type of the range domain.
     * @return the range of the form (-∞, b).
     */
    public static <T extends Comparable<? super T>> FacetRange<T> lessThan(final T upperEndpoint) {
        return new FacetRange<>(null, Bound.exclusive(upperEndpoint));
    }

    /**
     * Creates an interval with all values that are greater than or equal to the given endpoint.
     * @param lowerEndpoint lower endpoint, included in the range.
     * @param <T> type of the range domain.
     * @return the range of the form [a, +∞).
     */
    public static <T extends Comparable<? super T>> FacetRange<T> atLeast(final T lowerEndpoint) {
        return new FacetRange<>(Bound.inclusive(lowerEndpoint), null);
    }
}
