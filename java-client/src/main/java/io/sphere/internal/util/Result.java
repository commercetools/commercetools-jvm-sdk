package io.sphere.internal.util;

import net.jcip.annotations.Immutable;

/** Either a value or an exception. */
@Immutable
public class Result<T, E> {
    private final T value;
    private final E error;

    public Result(T value, E error) {
        this.value = value;
        this.error = error;
    }

    /** Creates a new successful result. */
    public static <T, E> Result<T, E> success(T value) {
        return new Result<T, E>(value, null);
    }

    /** Creates a new erroneous result. */
    public static <T, E> Result<T, E> error(E error) {
        return new Result<T, E>(null, error);
    }

    /** Returns true if this is a successful result. */
    public boolean isSuccess() { return error == null; }

    /** Returns true if this is an erroneous result. */
    public boolean isError() { return !isSuccess(); }

    /** If this is a successful result, returns the value.
     *  You should always check for {@link #isSuccess() isSuccess} before calling this method. */
    public T getValue() {
        if (!isSuccess()) throw new IllegalStateException("Can't access value of an erroneous result.");
        return value;
    }

    /** If this is a successful result, returns the value.
     *  You should always check for {@link #isSuccess() isSuccess} before calling this method. */
    public E getError() {
        if (!isError()) throw new IllegalStateException("Can't access error value of a successful result.");
        return error;
    }
}
