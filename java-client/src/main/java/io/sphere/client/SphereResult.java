package io.sphere.client;

import io.sphere.internal.util.Validation;
import net.jcip.annotations.Immutable;

/** Result of a request to the Sphere backend. */
@Immutable
public final class SphereResult<T> extends Validation<T, SphereBackendException> {
    private SphereResult(T value, SphereBackendException exception) {
        super(value, exception);
    }

    /** Creates a new successful result. */
    public static <T> SphereResult<T> success(T value) {
        return new SphereResult<T>(value, null);
    }

    /** Creates a new erroneous result. */
    public static <T> SphereResult<T> error(SphereBackendException exception) {
        return new SphereResult<T>(null, exception);
    }
}
