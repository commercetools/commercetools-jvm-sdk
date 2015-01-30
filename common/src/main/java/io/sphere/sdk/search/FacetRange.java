package io.sphere.sdk.search;

import java.util.Optional;

/**
 * Facet ranges should be of the form [a, b), (-∞, b) or [a, +∞).
 * @param <T> type of the range domain.
 */
public class FacetRange<T extends Comparable<? super T>> extends Range<T> {

    private FacetRange(final Optional<Bound<T>> lowerBound, final Optional<Bound<T>> upperBound) {
        super(lowerBound, upperBound);
    }

    /**
     * Creates an interval with the given lower and upper endpoints.
     * @param lowerEndpoint lower endpoint, included in the range.
     * @param upperEndpoint upper endpoint, excluded in the range.
     * @return the range with the given bounds of the form [a, b).
     * @throws InvertedBoundsException if the lower endpoint is greater than the upper endpoint.
     */
    public static <T extends Comparable<? super T>> FacetRange<T> of(final T lowerEndpoint, final T upperEndpoint) {
        return new FacetRange<>(Optional.of(Bound.inclusive(lowerEndpoint)), Optional.of(Bound.exclusive(upperEndpoint)));
    }

    /**
     * Creates an interval with all values that are strictly less than the given endpoint.
     * @param upperEndpoint upper endpoint, excluded in the range.
     * @return the range of the form (-∞, b).
     */
    public static <T extends Comparable<? super T>> FacetRange<T> lessThan(final T upperEndpoint) {
        return new FacetRange<>(Optional.empty(), Optional.of(Bound.exclusive(upperEndpoint)));
    }

    /**
     * Creates an interval with all values that are greater than or equal to the given endpoint.
     * @param lowerEndpoint lower endpoint, included in the range.
     * @return the range of the form [a, +∞).
     */
    public static <T extends Comparable<? super T>> FacetRange<T> atLeast(final T lowerEndpoint) {
        return new FacetRange<>(Optional.of(Bound.inclusive(lowerEndpoint)), Optional.empty());
    }
}
