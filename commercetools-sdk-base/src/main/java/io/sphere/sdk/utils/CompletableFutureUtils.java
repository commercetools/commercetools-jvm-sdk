package io.sphere.sdk.utils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Tools to simplify the work with {@link CompletionStage} and {@link CompletableFuture}.
 */
public final class CompletableFutureUtils {
    private CompletableFutureUtils() {
    }

    public static <T> CompletableFuture<T> successful(final T object) {
        return CompletableFuture.completedFuture(object);
    }

    static <T> Throwable blockForFailure(final CompletionStage<T> future) {
        try {
            future.toCompletableFuture().join();
            throw new NoSuchElementException(future + " did not complete exceptionally.");
        } catch (final CompletionException e1) {
            return e1.getCause();
        }
    }

    public static <T> CompletableFuture<T> exceptionallyCompletedFuture(final Throwable e) {
        return failed(e);
    }

    public static <T> CompletableFuture<T> failed(final Throwable e) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(e);
        return future;
    }

    public static <T> void transferResult(final CompletionStage<T> source,
                                          final CompletableFuture<T> target) {
        source.whenComplete((result, throwable) -> {
            final boolean isSuccessful = throwable == null;
            if (isSuccessful) {
                target.complete(result);
            } else {
                target.completeExceptionally(throwable);
            }
        });
    }

    public static <T> CompletionStage<T> onFailure(final CompletionStage<T> future, final Consumer<? super Throwable> consumer) {
        return future.whenCompleteAsync((value, throwable) -> {
            if (throwable != null) {
                consumer.accept(throwable);
            }
        });
    }

    public static <T> CompletionStage<T> onSuccess(final CompletionStage<T> future, final Consumer<? super T> consumer) {
        return future.whenCompleteAsync((value, throwable) -> {
            if (throwable == null) {
                consumer.accept(value);
            }
        });
    }

    public static <T> CompletionStage<T> recover(final CompletionStage<T> future, final Function<Throwable, ? extends T> f) {
        return future.exceptionally(f);
    }

    public static <T> CompletionStage<T> recoverWith(final CompletionStage<T> future, final Function<? super Throwable, CompletionStage<T>> f) {
        return recoverWith(future, f, ForkJoinPool.commonPool());
    }

    public static <T> CompletionStage<T> recoverWith(final CompletionStage<T> future, final Function<? super Throwable, CompletionStage<T>> f, final Executor executor) {
        final CompletableFuture<T> result = new CompletableFuture<>();
        final BiConsumer<T, Throwable> action = (value, error) -> {
            if (value != null) {
                result.complete(value);
            } else {
                final CompletionStage<T> alternative = f.apply(error);
                alternative.whenCompleteAsync((alternativeValue, alternativeError) -> {
                    if (alternativeValue != null) {
                        result.complete(alternativeValue);
                    } else {
                        result.completeExceptionally(alternativeError);
                    }
                }, executor);
            }
        };
        future.whenCompleteAsync(action, executor);
        return result;
    }

    public static <T, X extends Throwable> T orElseThrow(final CompletionStage<T> stage, Supplier<? extends X> exceptionSupplier) throws X, ExecutionException, InterruptedException {
        final CompletableFuture<T> future = stage.toCompletableFuture();
        if (future.isDone()) {
            return future.get();
        } else {
            throw exceptionSupplier.get();
        }
    }

    public static <T> T orElseGet(final CompletionStage<T> stage, final Supplier<T> other) throws ExecutionException, InterruptedException {
        final CompletableFuture<T> future = stage.toCompletableFuture();
        return future.isDone() ? future.get() : other.get();
    }

    public static <T, U> CompletionStage<U> map(final CompletionStage<T> future, final Function<? super T,? extends U> f) {
        return future.thenApplyAsync(f);
    }

    public static <T, U> CompletionStage<U> flatMap(final CompletionStage<T> future, final Function<? super T, CompletionStage<U>> f) {
        return future.thenComposeAsync(f);
    }

    /**
     * Transforms a list of {@code CompletionStage} into a {@code CompletionStage} of a list,
     * that will be completed once all the elements of the given list are completed.
     * In case multiple stages end exceptionally only one error is kept.
     * @param list list of {@code CompletionStage}
     * @param <T> the element obtained from the list of {@code CompletionStage}
     * @return the {@code CompletableFuture} of a list of elements
     */
    public static <T> CompletableFuture<List<T>> listOfFuturesToFutureOfList(final List<? extends CompletionStage<T>> list) {
        final List<CompletableFuture<T>> futureList = list.stream()
                .map(CompletionStage::toCompletableFuture)
                .collect(toList());
        final CompletableFuture[] futuresAsArray = futureList.toArray(new CompletableFuture[futureList.size()]);
        return CompletableFuture.allOf(futuresAsArray)
                .thenApplyAsync(x -> futureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    }

    public static <T> CompletableFuture<List<T>> sequence(final List<? extends CompletionStage<T>> stageList) {
        return listOfFuturesToFutureOfList(stageList);
    }
}