package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import java.util.Optional;
import java.util.function.Function;

public class Range<T extends Comparable<? super T>> extends Base {
    private static final String UNBOUND = "*";
    private final Optional<Bound<T>> lowerBound;
    private final Optional<Bound<T>> upperBound;

    protected Range(final Optional<Bound<T>> lowerBound, final Optional<Bound<T>> upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        if (hasInvertedBounds()) {
            throw new InvertedBoundsException(this);
        }
        if (hasSameExclusiveBounds()) {
            throw new SameExclusiveBoundsException(this);
        }
    }

    public Optional<Bound<T>> lowerBound() {
        return lowerBound;
    }

    public Optional<Bound<T>> upperBound() {
        return upperBound;
    }

    /**
     * Gets the endpoint of the lower bound, if defined.
     * @return the lower endpoint, or absent if the range has no lower bound.
     */
    public Optional<T> lowerEndpoint() {
        return lowerBound.map(bound -> bound.endpoint());
    }

    /**
     * Gets the endpoint of the upper bound, if defined.
     * @return the upper endpoint, or absent if the range has no upper bound.
     */
    public Optional<T> upperEndpoint() {
        return upperBound.map(bound -> bound.endpoint());
    }

    /**
     * Determines whether the range has closed bounds.
     * @return true if the range has both lower and upper bounds, false otherwise.
     */
    public boolean hasClosedBounds() {
        return lowerBound.isPresent() && upperBound.isPresent();
    }

    /**
     * Determines whether the range contains no values. A range is empty when it has the form (a, a] or [a, a).
     * @return true if the range cannot contain any value.
     */
    public boolean isEmpty() {
        return hasClosedBounds() && lowerEndpoint().equals(upperEndpoint())
                && lowerBound.get().isExclusive() != upperBound.get().isExclusive();
    }

    public String serialize(final Function<T, String> serializer) {
        return String.format("(%s to %s)",
                lowerEndpoint().map(e -> serializer.apply(e)).orElse(UNBOUND),
                upperEndpoint().map(e -> serializer.apply(e)).orElse(UNBOUND));
    }

    @Override
    public String toString() {
        final String lower = lowerBound().map(b -> (b.isExclusive() ? "(" : "[") + b.endpoint()).orElse("(*");
        final String upper = upperBound().map(b -> b.endpoint() + (b.isExclusive() ? ")" : "]")).orElse("*)");
        return lower + " to " + upper;
    }

    /**
     * Determines whether the lower endpoint is greater than the upper endpoint.
     * @return true if the bounds are inverted, false otherwise.
     */
    private boolean hasInvertedBounds() {
        return hasClosedBounds() && lowerEndpoint().get().compareTo(upperEndpoint().get()) > 0;
    }

    /**
     * Determines whether the range is of the form (a, a).
     * @return true if the range is of the form (a, a), false otherwise.
     */
    private boolean hasSameExclusiveBounds() {
        return hasClosedBounds() && lowerBound.equals(upperBound) && lowerBound.get().isExclusive();
    }

    /**
     * Creates an interval with the given lower and upper bounds.
     * @return the range with the given bounds.
     * @throws io.sphere.sdk.search.InvertedBoundsException if the lower endpoint is greater than the upper endpoint.
     * @throws io.sphere.sdk.search.SameExclusiveBoundsException if the range is of the form (a, a).
     */
    static <T extends Comparable<? super T>> Range<T> of(final Optional<Bound<T>> lowerBound, final Optional<Bound<T>> upperBound) {
        return new Range<>(lowerBound, upperBound);
    }

    /**
     * Creates an interval with the given lower and upper bounds.
     * @return the range with the given bounds.
     * @throws io.sphere.sdk.search.InvertedBoundsException if the lower endpoint is greater than the upper endpoint.
     * @throws io.sphere.sdk.search.SameExclusiveBoundsException if the range is of the form (a, a).
     */
    public static <T extends Comparable<? super T>> Range<T> of(final Bound<T> lowerBound, final Bound<T> upperBound) {
        return Range.of(Optional.of(lowerBound), Optional.of(upperBound));
    }

    /**
     * Creates an interval with all values that are strictly less than the given endpoint.
     * @return the range of the form (-∞, b)
     */
    public static <T extends Comparable<? super T>> Range<T> lessThan(final T upperEndpoint) {
        return Range.of(Optional.empty(), Optional.of(Bound.exclusive(upperEndpoint)));
    }

    /**
     * Creates an interval with all values that are strictly greater than the given endpoint.
     * @return the range of the form (a, +∞)
     */
    public static <T extends Comparable<? super T>> Range<T> greaterThan(final T lowerEndpoint) {
        return Range.of(Optional.of(Bound.exclusive(lowerEndpoint)), Optional.empty());
    }

    /**
     * Creates an interval with all values that are less than or equal to the given endpoint.
     * @return the range of the form (-∞, b]
     */
    public static <T extends Comparable<? super T>> Range<T> atMost(final T upperEndpoint) {
        return new Range<>(Optional.empty(), Optional.of(Bound.inclusive(upperEndpoint)));
    }

    /**
     * Creates an interval with all values that are greater than or equal to the given endpoint.
     * @return the range of the form [a, +∞)
     */
    public static <T extends Comparable<? super T>> Range<T> atLeast(final T lowerEndpoint) {
        return new Range<>(Optional.of(Bound.inclusive(lowerEndpoint)), Optional.empty());
    }

    /**
     * Creates an interval with all values, with no bounds.
     * @return the range of the form (-∞, +∞)
     */
    public static <T extends Comparable<? super T>> Range<T> all() {
        return Range.of(Optional.empty(), Optional.empty());
    }
}
