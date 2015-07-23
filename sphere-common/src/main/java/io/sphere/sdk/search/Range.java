package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

class Range<T extends Comparable<? super T>> extends Base {
    private static final String UNBOUND = "*";
    @Nullable
    protected final Bound<T> lowerBound;
    @Nullable
    protected final Bound<T> upperBound;

    Range(@Nullable final Bound<T> lowerBound, @Nullable final Bound<T> upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        if (hasInvertedBounds()) {
            throw new InvertedBoundsException(this);
        }
        if (hasSameExclusiveBounds()) {
            throw new SameExclusiveBoundsException(this);
        }
    }

    public Bound<T> lowerBound() {
        return lowerBound;
    }

    public Bound<T> upperBound() {
        return upperBound;
    }

    /**
     * Gets the endpoint of the lower bound, if defined.
     * @return the lower endpoint, or absent if the range has no lower bound.
     */
    public Optional<T> lowerEndpoint() {
        return Optional.ofNullable(lowerBound).map(bound -> bound.endpoint());
    }

    /**
     * Gets the endpoint of the upper bound, if defined.
     * @return the upper endpoint, or absent if the range has no upper bound.
     */
    public Optional<T> upperEndpoint() {
        return Optional.ofNullable(upperBound).map(bound -> bound.endpoint());
    }

    /**
     * Determines whether the range contains no values. A range is empty when it has the form (a, a] or [a, a).
     * @return true if the range cannot contain any value.
     */
    public boolean isEmpty() {
        return isBounded() && lowerEndpoint().equals(upperEndpoint())
                && lowerBound.isExclusive() != upperBound.isExclusive();
    }

    /**
     * Determines whether the range has both bounds.
     * @return true if the range has both lower and upper bounds, false otherwise.
     */
    public boolean isBounded() {
        return lowerBound != null && upperBound != null;
    }

    public String serialize(final Function<T, String> serializer) {
        return String.format("(%s to %s)",
                lowerEndpoint().map(e -> serializer.apply(e)).orElse(UNBOUND),
                upperEndpoint().map(e -> serializer.apply(e)).orElse(UNBOUND));
    }

    @Override
    public String toString() {
        final String lower = Optional.ofNullable(lowerBound()).map(b -> (b.isExclusive() ? "(" : "[") + b.endpoint()).orElse("(*");
        final String upper = Optional.ofNullable(upperBound()).map(b -> b.endpoint() + (b.isExclusive() ? ")" : "]")).orElse("*)");
        return lower + " to " + upper;
    }

    /**
     * Determines whether the lower endpoint is greater than the upper endpoint.
     * @return true if the bounds are inverted, false otherwise.
     */
    private boolean hasInvertedBounds() {
        return isBounded() && lowerEndpoint().get().compareTo(upperEndpoint().get()) > 0;
    }

    /**
     * Determines whether the range is of the form (a, a).
     * @return true if the range is of the form (a, a), false otherwise.
     */
    private boolean hasSameExclusiveBounds() {
        return isBounded() && lowerBound.equals(upperBound) && lowerBound.isExclusive();
    }
}
