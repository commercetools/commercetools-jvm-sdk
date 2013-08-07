package io.sphere.client;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;
import net.jcip.annotations.Immutable;

import javax.annotation.Nonnull;

@Immutable
public interface DeleteRequest<T>{

    /** Executes the request and returns the deleted object. */
    Optional<T> execute();

    /** Executes the request asynchronously and returns a future of the deleted object. */
    ListenableFuture<Optional<T>> executeAsync();

}
