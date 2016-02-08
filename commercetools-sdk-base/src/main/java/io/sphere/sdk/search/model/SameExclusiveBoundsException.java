package io.sphere.sdk.search.model;

public final class SameExclusiveBoundsException extends InvalidRangeException {
    private static final long serialVersionUID = -2217525602650171975L;

    public SameExclusiveBoundsException() {
    }

    public <T extends Comparable<? super T>> SameExclusiveBoundsException(final Range<T> range) {
        super(range);
    }
}
