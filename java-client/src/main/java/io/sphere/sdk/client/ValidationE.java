package io.sphere.sdk.client;

import io.sphere.sdk.utils.Validation;
import net.jcip.annotations.Immutable;

/** Validation with the error type being {@link io.sphere.sdk.client.SphereClientException}. */
@Immutable
public final class ValidationE<T> extends Validation<T, SphereClientException> {

    //TODO make private
    public ValidationE(T value, SphereClientException exception) {
        super(value, exception);
    }

    /** Creates a new erroneous result. */
    public static <T> ValidationE<T> error(SphereClientException exception) {
        return new ValidationE<T>(null, exception);
    }
}

