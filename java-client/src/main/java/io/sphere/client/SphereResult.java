package io.sphere.client;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import io.sphere.client.exceptions.SphereBackendException;
import io.sphere.client.exceptions.SphereException;
import io.sphere.internal.request.SphereResultRaw;
import net.jcip.annotations.Immutable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Result of a request to the Sphere backend. */
@Immutable
public class SphereResult<T> {
    /** Specific error, based on the generic error of {@code rawResult}. */
    @Nonnull private final Optional<SphereException> specificError;
    /** Raw result returned by the Sphere backend. */
    @Nonnull private final SphereResultRaw<T> rawResult;

    private SphereResult(@Nonnull SphereResultRaw<T> rawResult, @Nonnull Optional<SphereException> specificError) {
        if (rawResult == null) throw new NullPointerException("rawResult");
        if (specificError == null) throw new NullPointerException("specificError");
        this.rawResult = rawResult;
        this.specificError = specificError;
    }

    /** Returns true if the request was successful and returned a status code of 2xx. */
    public boolean isSuccess() {
        return rawResult.isSuccess();
    }

    /** Returns true if the request returned a status code of 4xx or 5xx.
     *  Use {@link #isError(Class) isError} {@link #getError(Class) getError} to check for specific exception types. */
    public boolean isError() {
        return !isSuccess();
    }

    /** Gets the success value of this result. */
    public T getValue() {
        return rawResult.getValue();
    }

    /** If this is an erroneous result, returns the exception representing the error returned by the backend.
     *  Otherwise throws an {@code IllegalStateException}. */
    public @Nonnull SphereBackendException getGenericError() {
        if (isSuccess()) throw new IllegalStateException("Can't access error of a successful result.");
        return rawResult.getError();
    }

    /** Returns true if this is an erroneous result containing an exception of given type or subtype. */
    public <T> boolean isError(Class<T> exceptionClass) {
        return this.getError(exceptionClass) != null;
    }

    /** If this is an erroneous result containing an exception of given type (or its subtype), returns it.
     *  Otherwise returns null. */
    @Nullable public <T> T getError(Class<T> exceptionClass) {
        if (isSuccess()) return null;
        if (!specificError.isPresent()) return null;
        if (!exceptionClass.isInstance(specificError.get())) return null;
        return exceptionClass.cast(specificError.get());
    }

    // ------------------------
    // Only used internally
    // ------------------------

    /** Creates a new successful result. */
    public static <T> SphereResult<T> success(T value) {
        return new SphereResult<T>(SphereResultRaw.success(value), Optional.<SphereException>absent());
    }

    /** If error, creates a new error result of given type (doesn't transform anything, just changes the type).
     *  Otherwise throws an exception */
    public <R> SphereResult<R> castErrorInternal() {
        if (isSuccess()) throw new IllegalStateException("Can't call castError on a successful result.");
        return new SphereResult<R>(this.rawResult.<R>castError(), this.specificError);
    }

    /** If successful, transforms the success value. Otherwise does nothing. */
    public <R> SphereResult<R> transform(@Nonnull Function<T, R> successFunc) {
        return new SphereResult<R>(this.rawResult.transform(successFunc), this.specificError);
    }

    /** Returns true if this result has a specific error. */
    public Optional<SphereException> getSpecificErrorInternal() {
       return specificError;
    }

    /** Creates a {@code SphereResult} with a generic and optionally also a specific error, based on the generic one.
     *  @param transformError Function that further specifies the error.
     *                        The function can be null or return null if it doesn't wish to specify the error. */
    public static <T> SphereResult<T> withSpecificError(
            SphereResultRaw<T> rawResult,
            @Nullable Function<SphereBackendException, SphereException> transformError)
    {

        if (transformError == null || rawResult.isSuccess()) {
            return new SphereResult<T>(rawResult, Optional.<SphereException>absent());
        }
        // this can be null if `transformError` decided not to perform any transformation
        SphereException specificError = transformError.apply(rawResult.getError());
        return new SphereResult<T>(rawResult, Optional.fromNullable(specificError));
    }
}
