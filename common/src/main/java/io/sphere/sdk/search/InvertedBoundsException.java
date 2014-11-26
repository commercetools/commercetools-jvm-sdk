package io.sphere.sdk.search;

public class InvertedBoundsException extends InvalidRangeException {
    private static final long serialVersionUID = 4533894392711955663L;

    public InvertedBoundsException() {
    }

    public InvertedBoundsException(String message) {
        super(message);
    }
}
