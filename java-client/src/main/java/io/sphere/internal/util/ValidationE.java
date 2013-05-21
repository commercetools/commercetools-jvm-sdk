package io.sphere.internal.util;

import io.sphere.client.SphereException;
import net.jcip.annotations.Immutable;

/** Validation with the error type being {@link SphereException}. */
@Immutable
public final class ValidationE<T> extends Validation<T, SphereException> {
    private ValidationE(T value, SphereException exception) {
        super(value, exception);
    }

    /** Creates a new successful result. */
    public static <T> ValidationE<T> success(T value) {
        return new ValidationE<T>(value, null);
    }

    /** Creates a new erroneous result. */
    public static <T> ValidationE<T> error(SphereException exception) {
        return new ValidationE<T>(null, exception);
    }
}

