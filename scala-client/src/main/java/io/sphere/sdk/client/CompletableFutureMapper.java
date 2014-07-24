package io.sphere.sdk.client;

import scala.concurrent.Promise;

import java.util.function.BiConsumer;

//default methods of Java interfaces do not work in Scala
final class CompletableFutureMapper<T> implements BiConsumer<T, Throwable> {
    private final Promise<T> promise;

    public CompletableFutureMapper(final Promise<T> promise) {
        this.promise = promise;
    }

    @Override
    public void accept(final T value, final Throwable throwable) {
        final boolean hasError = throwable != null;
        if (hasError) {
            promise.failure(throwable);
        } else {
            promise.success(value);
        }
    }
}
