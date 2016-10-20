package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;
import org.apache.commons.lang3.ObjectUtils;

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
        if (hasSameExclusiveBounds()) {
            throw new SameExclusiveBoundsException(this);
        }
    }

    @Nullable
    public Bound<T> lowerBound() {
        return lowerBound;
    }

    @Nullable
    public Bound<T> upperBound() {
        return upperBound;
    }

    /**
     * Gets the endpoint of the lower bound, if defined.
     * @return the lower endpoint, or absent if the range has no lower bound.
     */
    @Nullable
    public T lowerEndpoint() {
        return Optional.ofNullable(lowerBound).map(Bound::endpoint).orElse(null);
    }

    /**
     * Gets the endpoint of the upper bound, if defined.
     * @return the upper endpoint, or absent if the range has no upper bound.
     */
    @Nullable
    public T upperEndpoint() {
        return Optional.ofNullable(upperBound).map(Bound::endpoint).orElse(null);
    }

    /**
     * Determines whether the upper bound is exclusive bounded
     * @return true if the upper bound is exclusive, false if the upper bound is inclusive or does not exist
     */
    public boolean isUpperBoundExclusive() {
        return Optional.ofNullable(upperBound).map(Bound::isExclusive).orElse(false);
    }

    /**
     * Determines whether the upper bound is inclusive bounded
     * @return true if the upper bound is inclusive, false if the upper bound is exclusive or does not exist
     */
    public boolean isUpperBoundInclusive() {
        return Optional.ofNullable(upperBound).map(Bound::isInclusive).orElse(false);
    }

    /**
     * Determines whether the lower bound is exclusive bounded
     * @return true if the lower bound is exclusive, false if the lower bound is inclusive or does not exist
     */
    public boolean isLowerBoundExclusive() {
        return Optional.ofNullable(lowerBound).map(Bound::isExclusive).orElse(false);
    }

    /**
     * Determines whether the lower bound is inclusive bounded
     * @return true if the lower bound is inclusive, false if the lower bound is exclusive or does not exist
     */
    public boolean isLowerBoundInclusive() {
        return Optional.ofNullable(lowerBound).map(Bound::isInclusive).orElse(false);
    }

    /**
     * Determines whether the range contains no values. A range is empty when it has the form (a, a] or [a, a).
     * @return true if the range cannot contain any value.
     */
    public boolean isEmpty() {
        final boolean isBounded = lowerBound != null && upperBound != null;
        return isBounded && lowerBound.endpoint() == upperBound.endpoint()
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
                Optional.ofNullable(lowerEndpoint()).map(serializer).orElse(UNBOUND),
                Optional.ofNullable(upperEndpoint()).map(serializer).orElse(UNBOUND));
    }

    @Override
    public String toString() {
        final String lower = Optional.ofNullable(lowerBound()).map(b -> (b.isExclusive() ? "(" : "[") + b.endpoint()).orElse("(*");
        final String upper = Optional.ofNullable(upperBound()).map(b -> b.endpoint() + (b.isExclusive() ? ")" : "]")).orElse("*)");
        return lower + " to " + upper;
    }

    /**
     * Determines whether the range is of the form (a, a).
     * @return true if the range is of the form (a, a), false otherwise.
     */
    private boolean hasSameExclusiveBounds() {
        final boolean isBounded = lowerBound != null && upperBound != null;
        return isBounded && lowerBound.equals(upperBound) && lowerBound.isExclusive();
    }
}
