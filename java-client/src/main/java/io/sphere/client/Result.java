package io.sphere.client;

/** Result of a request to the Sphere backend. */
public final class Result<T> extends io.sphere.internal.util.Result<T, SphereException> {
    private Result(T value, SphereException exception) {
        super(value, exception);
    }

    /** Creates a new successful result. */
    public static <T> Result<T> success(T value) {
        return new Result(value, null);
    }

    /** Creates a new erroneous result. */
    public static <T> Result<T> error(SphereException exception) {
        return new Result<T>(null, exception);
    }
}
