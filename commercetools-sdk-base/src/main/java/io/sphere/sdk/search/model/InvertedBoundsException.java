package io.sphere.sdk.search.model;

/**
 * Exception for the case that the lower bound is greater than the upper bound.
 *
 */
public class InvertedBoundsException extends InvalidRangeException {
    private static final long serialVersionUID = 4533894392711955663L;

    public InvertedBoundsException() {
    }

    public <T extends Comparable<? super T>> InvertedBoundsException(final Range<T> range) {
        super(range);
    }
}
