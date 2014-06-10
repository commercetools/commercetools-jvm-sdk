package io.sphere.sdk.validation;

import net.jcip.annotations.Immutable;

/** Either a value or an exception. */
@Immutable
public class Validation<T, E> {
    protected final T value;
    protected final E error;

    public static <T, E> Validation<T, E> success(T value) {
        return new Validation<T, E>(value, null);
    }

    public Validation(T value, E error) {
        this.value = value;
        this.error = error;
    }

    public boolean isSuccess() { return error == null; }

    public boolean isError() { return !isSuccess(); }

    /** If this is a successful result, returns the value.
     *  You should always check for {@link #isSuccess() isSuccess} before calling this method.
     *
     *  @return the value if present
     */
    public T getValue() {
        if (!isSuccess()) throw new IllegalStateException("Can't access value of an erroneous result.");
        return value;
    }

    /** If this is a successful result, returns the value.
     *  You should always check for {@link #isSuccess() isSuccess} before calling this method.
     *
     *  @return error value if defined
     */
    public E getError() {
        if (!isError()) throw new IllegalStateException("Can't access error value of a successful result.");
        return error;
    }

    @Override
    public String toString() {
        return "Validation{" +
                "value=" + value +
                ", error=" + error +
                '}';
    }

    @SuppressWarnings("rawtypes")//at runtime generic type is not determinable
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Validation)) return false;

        Validation that = (Validation) o;

        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        return result;
    }
}