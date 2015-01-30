package io.sphere.sdk.search;

import java.util.Optional;

/**
 * Filter ranges should be of the form [a, b], (-∞, b] or [a, +∞).
 * @param <T> type of the range domain.
 */
public class FilterRange<T extends Comparable<? super T>> extends Range<T> {

    private FilterRange(final Optional<Bound<T>> lowerBound, final Optional<Bound<T>> upperBound) {
        super(lowerBound, upperBound);
    }

    /**
     * Creates an interval with the given lower and upper endpoints.
     * @param lowerEndpoint lower endpoint, included in the range.
     * @param upperEndpoint upper endpoint, included in the range.
     * @return the range with the given bounds of the form [a, b].
     * @throws io.sphere.sdk.search.InvertedBoundsException if the lower endpoint is greater than the upper endpoint.
     */
    public static <T extends Comparable<? super T>> FilterRange<T> of(final T lowerEndpoint, final T upperEndpoint) {
        return new FilterRange<>(Optional.of(Bound.inclusive(lowerEndpoint)), Optional.of(Bound.inclusive(upperEndpoint)));
    }

    /**
     * Creates an interval with all values that are less than or equal to the given endpoint.
     * @return the range of the form (-∞, b].
     */
    public static <T extends Comparable<? super T>> FilterRange<T> atMost(final T upperEndpoint) {
        return new FilterRange<>(Optional.empty(), Optional.of(Bound.inclusive(upperEndpoint)));
    }

    /**
     * Creates an interval with all values that are greater than or equal to the given endpoint.
     * @return the range of the form [a, +∞).
     */
    public static <T extends Comparable<? super T>> FilterRange<T> atLeast(final T lowerEndpoint) {
        return new FilterRange<>(Optional.of(Bound.inclusive(lowerEndpoint)), Optional.empty());
    }
}
