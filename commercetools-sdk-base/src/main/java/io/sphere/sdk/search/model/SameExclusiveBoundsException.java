package io.sphere.sdk.search.model;

/**
 * Exception for {@link Range} in case the lower and upper bounds are exclusive and of the same value.
 *
 * {@include.example io.sphere.sdk.search.model.RangeTest#isInvalidWhenBoundsAreEqualAndExclusive()}
 */
public final class SameExclusiveBoundsException extends InvalidRangeException {
    private static final long serialVersionUID = -2217525602650171975L;

    public SameExclusiveBoundsException() {
    }

    public <T extends Comparable<? super T>> SameExclusiveBoundsException(final Range<T> range) {
        super(range);
    }
}
