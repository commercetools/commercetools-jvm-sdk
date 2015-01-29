package io.sphere.sdk.search;

public class NotSupportedException extends InvalidRangeException {
    private static final long serialVersionUID = -2217525602650171976L;

    public NotSupportedException() {
    }

    public <T extends Comparable<? super T>> NotSupportedException(final Range<T> range) {
        super(range);
    }
}
