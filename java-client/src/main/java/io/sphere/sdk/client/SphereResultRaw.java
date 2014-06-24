package io.sphere.sdk.client;

import com.google.common.base.Function;
import io.sphere.sdk.utils.Validation;

import javax.annotation.Nonnull;

final class SphereResultRaw<T> extends Validation<T, SphereBackendException> {

    //TODO make private?
    public SphereResultRaw(T value, SphereBackendException exception) {
        super(value, exception);
    }

    /**
     * Creates a new erroneous result.
     */
    public static <T> SphereResultRaw<T> error(SphereBackendException exception) {
        return new SphereResultRaw<>(null, exception);
    }

    @Override
    public T getValue() {
        if (!isSuccess()) throw getError();
        return super.getValue();
    }

    /**
     * If successful, transforms the success value. Otherwise does nothing.
     */
    public <R> SphereResultRaw<R> transform(@Nonnull Function<T, R> successFunc) {
        return isSuccess() ?
                new SphereResultRaw<>(successFunc.apply(getValue()), null) :
                SphereResultRaw.<R>error(getError());
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName() + "{" + super.toString() + "}";
    }
}
