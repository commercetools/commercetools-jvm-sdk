package io.sphere.sdk.search.model;

/**
 * Filter ranges should be of the form [a, b], (-∞, b] or [a, +∞).
 * @param <T> type of the range domain.
 */
public final class FilterRange<T extends Comparable<? super T>> extends Range<T> {

    private FilterRange(final Bound<T> lowerBound, final Bound<T> upperBound) {
        super(lowerBound, upperBound);
    }

    /**
     * Creates an interval with the given lower and upper endpoints.
     * @param lowerEndpoint lower endpoint, included in the range.
     * @param upperEndpoint upper endpoint, included in the range.
     * @param <T> type of the range domain.
     * @return the range with the given bounds of the form [a, b].
     */
    public static <T extends Comparable<? super T>> FilterRange<T> of(final T lowerEndpoint, final T upperEndpoint) {
        return new FilterRange<>(Bound.inclusive(lowerEndpoint), Bound.inclusive(upperEndpoint));
    }

    /**
     * Creates an interval with all values that are less than or equal to the given endpoint.
     * @param upperEndpoint upper endpoint, included in the range.
     * @param <T> type of the range domain.
     * @return the range of the form (-∞, b].
     */
    public static <T extends Comparable<? super T>> FilterRange<T> atMost(final T upperEndpoint) {
        return new FilterRange<>(null, Bound.inclusive(upperEndpoint));
    }

    /**
     * Creates an interval with all values that are greater than or equal to the given endpoint.
     * @param lowerEndpoint lower endpoint, included in the range.
     * @param <T> type of the range domain.
     * @return the range of the form [a, +∞).
     */
    public static <T extends Comparable<? super T>> FilterRange<T> atLeast(final T lowerEndpoint) {
        return new FilterRange<>(Bound.inclusive(lowerEndpoint), null);
    }
}
