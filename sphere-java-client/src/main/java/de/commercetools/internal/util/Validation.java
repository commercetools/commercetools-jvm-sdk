package de.commercetools.internal.util;

import net.jcip.annotations.Immutable;

/** Either a value, or an exception. */
@Immutable
public final class Validation<T> {
    private final T value;
    private final RuntimeException exception;

    public Validation(T value, RuntimeException exception) {
        this.value = value;
        this.exception = exception;
    }

    public static <T> Validation<T> success(T value) {
        return new Validation<T>(value, null);
    }

    public static <T> Validation<T> error(RuntimeException exception) {
        return new Validation<T>(null, exception);
    }

    public boolean isSuccess() {
        return exception == null;
    }

    public boolean isError() {
        return !isSuccess();
    }

    public T value() {
        return value;
    }

    public RuntimeException exception() {
        return exception;
    }
}
