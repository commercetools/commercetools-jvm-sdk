package io.sphere.internal.request;

import com.google.common.base.Function;
import io.sphere.client.exceptions.SphereBackendException;
import io.sphere.internal.util.Validation;
import net.jcip.annotations.Immutable;

import javax.annotation.Nonnull;

/** Raw result of a request to the Sphere backend, used internally by the Java client. */
@Immutable
public final class SphereResultRaw<T> extends Validation<T, SphereBackendException> {
    private SphereResultRaw(T value, SphereBackendException exception) {
        super(value, exception);
    }

    /** Creates a new successful result. */
    public static <T> SphereResultRaw<T> success(T value) {
        return new SphereResultRaw<T>(value, null);
    }

    /** Creates a new erroneous result. */
    public static <T> SphereResultRaw<T> error(SphereBackendException exception) {
        return new SphereResultRaw<T>(null, exception);
    }

    @Override public T getValue() {
        if (!isSuccess()) throw getError();
        return super.getValue();
    }

    /** If successful, transforms the success value. Otherwise does nothing. */
    public <R> SphereResultRaw<R> transform(@Nonnull Function<T, R> successFunc) {
        return isSuccess() ?
                SphereResultRaw.success(successFunc.apply(getValue())) :
                SphereResultRaw.<R>error(getError());
    }

    /** If error, creates a new error result of given type (doesn't transform anything, just changes the type).
     *  Otherwise throws an exception */
   public <R> SphereResultRaw<R> castError() {
        if (isSuccess()) throw new IllegalStateException("Can't call castError on a successful result.");
        return SphereResultRaw.error(this.getError());
   }

   @Override
   public String toString() {
       return this.getClass().getCanonicalName() + "{" + super.toString() + "}";
   }
}
