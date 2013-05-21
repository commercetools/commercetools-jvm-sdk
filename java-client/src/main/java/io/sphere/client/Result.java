package io.sphere.client;

import io.sphere.internal.util.Validation;
import net.jcip.annotations.Immutable;

/** Result of a request to the Sphere backend. */
@Immutable
public final class Result<T> extends Validation<T, SphereBackendException> {
    private Result(T value, SphereBackendException exception) {
        super(value, exception);
    }

    /** Creates a new successful result. */
    public static <T> Result<T> success(T value) {
        return new Result<T>(value, null);
    }

    /** Creates a new erroneous result. */
    public static <T> Result<T> error(SphereBackendException exception) {
        return new Result<T>(null, exception);
    }
}
