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

    /**
     * Creates a {@link CompletableFuture} which is completed successfully with the given object.
     *
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#testSuccessful()}
     *
     * @param object the result of the future
     * @param <T> the type of the object
     * @return future
     */
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

    /**
     * Creates a {@link CompletableFuture} which is completed exceptionally with the given Exception.
     * Alias of {@link #failed(Throwable)}.
     *
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#testFailed()}
     *
     * @param e exception for the future
     * @param <T> the type of the value of the success case
     * @return future
     */
    public static <T> CompletableFuture<T> exceptionallyCompletedFuture(final Throwable e) {
        return failed(e);
    }

    /**
     * Creates a {@link CompletableFuture} which is completed exceptionally with the given Exception.
     *
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#testFailed()}
     *
     * @param e exception for the future
     * @param <T> the type of the value of the success case
     * @return future
     */
    public static <T> CompletableFuture<T> failed(final Throwable e) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(e);
        return future;
    }

    /**
     * Internal JVM SDK util.
     *
     * @param source the stage which may be completed at some time
     * @param target future which will receive the results of source
     * @param <T> type of the value of the future
     */
    public static <T> void transferResult(final CompletionStage<T> source,
                                          final CompletableFuture<T> target) {
        source.whenCompleteAsync((result, throwable) -> {
            final boolean isSuccessful = throwable == null;
            if (isSuccessful) {
                target.complete(result);
            } else {
                target.completeExceptionally(throwable);
            }
        });
    }

    /**
     * Executes a side-effect when the future completes exceptionally.
     *
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#onFailure()}
     *
     * @param future the observed future
     * @param consumer code which should be executed if the future completes exceptionally
     * @param <T> type of the futures value
     * @return stage which is completed when the consumer is done or the future completed successfully
     */
    public static <T> CompletionStage<T> onFailure(final CompletionStage<T> future, final Consumer<? super Throwable> consumer) {
        return future.whenCompleteAsync((value, throwable) -> {
            if (throwable != null) {
                consumer.accept(throwable);
            }
        });
    }

    /**
     * Executes a side-effect when the future completes successfully.
     *
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#onSuccess()}
     *
     * @param future the observed future
     * @param consumer code which should be executed if the future completes successfully
     * @param <T> type of the futures value
     * @return stage which is completed when the consumer is done or the future completed exceptionally
     */
    public static <T> CompletionStage<T> onSuccess(final CompletionStage<T> future, final Consumer<? super T> consumer) {
        return future.whenCompleteAsync((value, throwable) -> {
            if (throwable == null) {
                consumer.accept(value);
            }
        });
    }

    /**
     * Creates a {@link CompletionStage} which can be recovered if an error occurs. Alias for {@link CompletionStage#exceptionally(Function)}.
     * If the recovery function also requires to create a {@link CompletionStage} then use {@link #recoverWith(CompletionStage, Function)}.
     *
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#recoverFailure()}
     *
     * @param future future which may completes exceptionally
     * @param f function how the exception should be handled to create a successfully completed future, but it also can throw exceptions
     * @param <T> type of the value of the future
     * @return a future which can be recovered from errors
     */
    public static <T> CompletionStage<T> recover(final CompletionStage<T> future, final Function<Throwable, ? extends T> f) {
        return future.exceptionally(f);
    }

    /**
     * Creates a {@link CompletionStage} which can be recovered if an error occurs.
     * If the recovery function does not require to create a {@link CompletionStage} then use {@link #recover(CompletionStage, Function)}.
     *
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#recoverWithSuccessInSecond()}
     *
     * @param future future which may completes exceptionally
     * @param f function how the exception should be handled to create a successfully completed future
     * @param <T> type of the value of the future
     * @return a future which can be recovered from errors
     */
    public static <T> CompletableFuture<T> recoverWith(final CompletionStage<T> future, final Function<? super Throwable, CompletionStage<T>> f) {
        return recoverWith(future, f, ForkJoinPool.commonPool());
    }

    /**
     * Creates a {@link CompletionStage} which can be recovered if an error occurs by executing a function in a certain thread pool.
     * If the recovery does not require to create a {@link CompletionStage} then use {@link #recover(CompletionStage, Function)}.
     *
     * Have a look at {@link #recoverWith(CompletionStage, Function)} for an example without the thread pool.
     *
     * @param future future which may completes exceptionally
     * @param f function how the exception should be handled to create a successfully completed future
     * @param executor thread pool to execute the recover function
     * @param <T> type of the value of the future
     * @return a future which may recovered from errors
     */
    public static <T> CompletableFuture<T> recoverWith(final CompletionStage<T> future, final Function<? super Throwable, CompletionStage<T>> f, final Executor executor) {
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

    /**
     *
     * Tries to access the completed future if available and returns its value (or exception in case the future completed exceptionally), otherwise throws the given exception.
     *
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#orElseThrow()}
     *
     * @param stage the future
     * @param exceptionSupplier code which should be executed if the future is not yet completed (either successfully or exceptionally)
     * @param <T> the type of the future value
     * @param <X> the type of the exception to be thrown if the value is absent
     * @return value
     * @throws X exception in case the value is not available
     */
    public static <T, X extends Throwable> T orElseThrow(final CompletionStage<T> stage, Supplier<? extends X> exceptionSupplier) throws X {
        final CompletableFuture<T> future = stage.toCompletableFuture();
        if (future.isDone()) {
            return future.join();
        } else {
            throw exceptionSupplier.get();
        }
    }

    /**
     *
     * Tries to access the completed future if available and returns its value (or exception in case the future completed exceptionally), otherwise uses the supplier to get a default value.
     *
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#orElseGet()}
     *
     * @param stage the future
     * @param other code which should be executed when the value is not available yet or the future completed exceptionally
     * @param <T> the type of the future value
     * @return value
     */
    public static <T> T orElseGet(final CompletionStage<T> stage, final Supplier<T> other) {
        final CompletableFuture<T> future = stage.toCompletableFuture();
        return future.isDone() ? future.join() : other.get();
    }

    /**
     * Applies a function to the successful result of a future.
     * If the function needs to return a {@link CompletionStage} use {@link #flatMap(CompletionStage, Function)} instead.
     *
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#testMap()}
     *
     * @param future the future to map
     * @param f function which should be applied if the future completes successfully
     * @param <T> type of value of the future
     * @param <U> type of the value of the returned future which is also the return type of {@code f}
     * @return a new future which contains either the exception from the original future or as value the result of application of {@code f} to the value of the original future.
     */
    public static <T, U> CompletionStage<U> map(final CompletionStage<T> future, final Function<? super T,? extends U> f) {
        return future.thenApplyAsync(f);
    }

    /**
     * Applies a function to the successful result of a future.
     * If the function does not to return a {@link CompletionStage} use {@link #map(CompletionStage, Function)} instead.
     *
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#testFlatMap()}
     *
     * @param future the future to map
     * @param f function which should be applied if the future completes successfully
     * @param <T> type of value of the future
     * @param <U> type of the value of the returned future which is also the return type of {@code f}
     * @return a new future which contains either the exception from the original future or as value the result of application of {@code f} to the value of the original future.
     */
    public static <T, U> CompletionStage<U> flatMap(final CompletionStage<T> future, final Function<? super T, CompletionStage<U>> f) {
        return future.thenComposeAsync(f);
    }

    /**
     * Transforms a list of {@code CompletionStage} into a {@code CompletionStage} of a list,
     * that will be completed once all the elements of the given list are completed.
     * In case multiple stages end exceptionally only one error is kept.
     *
     * Alias of {@link #sequence(List)}.
     *
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#sequence()}
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#sequenceErrorCase()}
     *
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

    /**
     * Transforms a list of {@code CompletionStage} into a {@code CompletionStage} of a list,
     * that will be completed once all the elements of the given list are completed.
     * In case multiple stages end exceptionally only one error is kept.
     *
     * Alias of {@link #listOfFuturesToFutureOfList(List)}.
     *
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#sequence()}
     * {@include.example io.sphere.sdk.utils.CompletableFutureUtilsTest#sequenceErrorCase()}
     *
     * @param list list of {@code CompletionStage}
     * @param <T> the element obtained from the list of {@code CompletionStage}
     * @return the {@code CompletableFuture} of a list of elements
     */
    public static <T> CompletableFuture<List<T>> sequence(final List<? extends CompletionStage<T>> list) {
        return listOfFuturesToFutureOfList(list);
    }
}