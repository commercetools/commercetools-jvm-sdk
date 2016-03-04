package io.sphere.sdk.search.model;

/**
 * Exception for invalid {@link Range}s.
 *
 * @see InvertedBoundsException
 * @see SameExclusiveBoundsException
 */
public class InvalidRangeException extends IllegalArgumentException {
    private static final long serialVersionUID = 1098946885116985560L;

    public InvalidRangeException() {
    }

    public <T extends Comparable<? super T>> InvalidRangeException(final Range<T> range) {
        this("Invalid range", range);
    }

    public <T extends Comparable<? super T>> InvalidRangeException(final String message, final Range<T> range) {
        super(message + ": " + range.toString());
    }
}
