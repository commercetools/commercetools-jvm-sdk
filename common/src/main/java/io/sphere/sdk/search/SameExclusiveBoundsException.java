package io.sphere.sdk.search;

public class SameExclusiveBoundsException extends InvalidRangeException {
    private static final long serialVersionUID = -2217525602650171975L;

    public SameExclusiveBoundsException() {
    }

    public SameExclusiveBoundsException(String message) {
        super(message);
    }
}
