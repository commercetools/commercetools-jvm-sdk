package io.sphere.client;

import com.google.common.base.Function;
import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.client.exceptions.SphereBackendException;
import io.sphere.client.exceptions.SphereException;
import net.jcip.annotations.Immutable;

import javax.annotation.Nonnull;

/** Request that sends a commands to the Sphere backend. */
@Immutable
public interface CommandRequest<T> {
    /** Executes the request and returns the result. */
    T execute();

    /** Executes the request asynchronously and returns a future providing the result. */
    ListenableFuture<SphereResult<T>> executeAsync();

    /** Transforms a generic error result from the Sphere backend into a specific error, depending on the use case. */
    CommandRequest<T> withErrorHandling(@Nonnull Function<SphereBackendException, SphereException> transformError);
}
