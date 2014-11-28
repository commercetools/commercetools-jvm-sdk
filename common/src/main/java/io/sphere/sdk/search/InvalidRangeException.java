package io.sphere.sdk.search;

public class InvalidRangeException extends RuntimeException {
    private static final long serialVersionUID = 1098946885116985560L;

    public InvalidRangeException() {
    }

    public InvalidRangeException(String message) {
        super(message);
    }

    public <T extends Comparable<? super T>> InvalidRangeException(Range<T> range) {
        super("Inverted bounds: " + range);
    }
}
