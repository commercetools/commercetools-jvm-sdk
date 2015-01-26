package io.sphere.sdk.client;

import io.sphere.sdk.utils.Validation;

/** Validation with the error type being {@link io.sphere.sdk.client.SphereClientException}. */
final class ValidationE<T> extends Validation<T, SphereClientException> {

    //TODO make private
    public ValidationE(T value, SphereClientException exception) {
        super(value, exception);
    }

    /**
     * Creates a new erroneous result.
     * @param exception the error of the result
     * @param <T> the type of the possible value, but absent value
     * @return the result
     */
    public static <T> ValidationE<T> error(SphereClientException exception) {
        return new ValidationE<>(null, exception);
    }
}

