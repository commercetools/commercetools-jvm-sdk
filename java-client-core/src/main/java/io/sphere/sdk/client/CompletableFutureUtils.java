package io.sphere.sdk.client;

import java.util.NoSuchElementException;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

final class CompletableFutureUtils {
    private CompletableFutureUtils() {
    }

    public static <T> CompletableFuture<T> successful(final T object) {
        return CompletableFuture.completedFuture(object);
    }

    public static <T> Throwable blockForFailure(final CompletableFuture<T> future) {
        try {
            future.join();
            throw new NoSuchElementException(future + " did not complete exceptionally.");
        } catch (final CompletionException e1) {
            return e1.getCause();
        }
    }

    public static <T> CompletableFuture<T> failed(final Throwable e) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(e);
        return future;
    }

    public static <T> void transferResult(final CompletableFuture<T> futureSource,
                                          final CompletableFuture<T> futureTarget) {
        futureSource.whenComplete((result, throwable) -> {
            final boolean isSuccessful = throwable == null;
            if (isSuccessful) {
                futureTarget.complete(result);
            } else {
                futureTarget.completeExceptionally(throwable);
            }
        });
    }

    public static <T> CompletableFuture<T> onFailure(final CompletableFuture<T> future, final Consumer<Throwable> consumer) {
        return future.whenCompleteAsync((value, throwable) -> {
            if (throwable != null) {
                consumer.accept(throwable);
            }
        });
    }

    public static <T> CompletableFuture<T> onSuccess(final CompletableFuture<T> future, final Consumer<T> consumer) {
        return future.whenCompleteAsync((value, throwable) -> {
            if (throwable == null) {
                consumer.accept(value);
            }
        });
    }

    public static <T> CompletableFuture<T> recover(final CompletableFuture<T> future, final Function<Throwable, T> f) {
        final CompletableFuture<T> result = new CompletableFuture<>();
        future.whenComplete((value, e) -> {
            if (e == null) {
                result.complete(value);
            } else {
                final T recoveredResult = f.apply(e);
                result.complete(recoveredResult);
            }
        });
        return result;
    }

    public static <T> CompletableFuture<T> recoverWith(final CompletableFuture<T> future, final Function<Throwable, CompletableFuture<T>> f) {
        final CompletableFuture<T> result = new CompletableFuture<>();
        final BiConsumer<T, Throwable> action = (value, error) -> {
            if (value != null) {
                result.complete(value);
            } else {
                final CompletableFuture<T> alternative = f.apply(error);
                alternative.whenComplete((alternativeValue, alternativeError) -> {
                    if (alternativeValue != null) {
                        result.complete(alternativeValue);
                    } else {
                        result.completeExceptionally(alternativeError);
                    }
                });
            }
        };
        future.whenComplete(action);
        return result;
    }

    public static <T, U> CompletableFuture<U> flatMap(final CompletableFuture<T> future, final Function<T, CompletableFuture<U>> f) {
        return future.thenCompose(f);
    }
}