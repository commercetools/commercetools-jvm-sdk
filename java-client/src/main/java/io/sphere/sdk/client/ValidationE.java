package io.sphere.sdk.client;

import net.jcip.annotations.Immutable;

/** Validation with the error type being {@link SphereClientException}. */
@Immutable
public final class ValidationE<T> extends Validation<T, SphereClientException> {
    private ValidationE(T value, SphereClientException exception) {
        super(value, exception);
    }

    /** Creates a new successful result. */
    public static <T> ValidationE<T> success(T value) {
        return new ValidationE<T>(value, null);
    }

    /** Creates a new erroneous result. */
    public static <T> ValidationE<T> error(SphereClientException exception) {
        return new ValidationE<T>(null, exception);
    }
}

