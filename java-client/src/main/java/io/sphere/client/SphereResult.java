package io.sphere.client;

import com.google.common.base.Function;
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

    @Override public T getValue() {
        if (!isSuccess()) throw getError();
        return super.getValue();
    }

    /** If successful, transforms the success value. Otherwise does nothing. */
    public <R> SphereResult<R> transform(Function<T, R> successFunc) {
        return isError() ?
                SphereResult.<R>error(getError()) :
                SphereResult.success(successFunc.apply(getValue()));
    }
}
