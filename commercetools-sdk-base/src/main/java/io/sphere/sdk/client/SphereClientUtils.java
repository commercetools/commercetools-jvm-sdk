package io.sphere.sdk.client;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public final class SphereClientUtils {
    private SphereClientUtils() {
    }

    /**
     * Waits with a timeout for RESPONSES of a commercetools client wrapped in a {@link CompletionStage}.
     * This method should not be used for other {@link CompletionStage}s since it is throwing {@link io.sphere.sdk.models.SphereException}s.
     *
     * @param completionStage the future monad to wait for
     * @param sphereRequest the request belonging to the completionStage
     * @param duration the maximum duration to wait for this single request
     * @param <T> type of the result for the request
     * @return the wrapped value of {@code completionStage}
     * @throws SphereTimeoutException if a timeout occurs
     */
    public static <T> T blockingWait(final CompletionStage<T> completionStage, final SphereRequest<T> sphereRequest, final Duration duration) {
        return blockingWait(completionStage, sphereRequest, duration.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Waits with a timeout for RESPONSES of a commercetools client wrapped in a {@link CompletionStage}.
     * This method should not be used for other {@link CompletionStage}s since it is throwing {@link io.sphere.sdk.models.SphereException}s.
     *
     * @param completionStage the future monad to wait for
     * @param duration the maximum duration to wait for this single request
     * @param <T> type of the result for the request
     * @return the wrapped value of {@code completionStage}
     * @throws SphereTimeoutException if a timeout occurs
     */
    public static <T> T blockingWait(final CompletionStage<T> completionStage, final Duration duration) {
        return blockingWait(completionStage, duration.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Waits with a timeout for RESPONSES of a commercetools client wrapped in a {@link CompletionStage}.
     * This method should not be used for other {@link CompletionStage}s since it is throwing {@link io.sphere.sdk.models.SphereException}s.
     *
     * @param completionStage the future monad to wait for
     * @param timeout the maximum time to wait for this single request
     * @param unit the time unit of the timeout argument
     * @param <T> type of the result for the request
     * @return the wrapped value of {@code completionStage}
     * @throws SphereTimeoutException if a timeout occurs
     */
    public static <T> T blockingWait(final CompletionStage<T> completionStage, final long timeout, final TimeUnit unit) {
        return blockingWait(completionStage, null, timeout, unit);
    }

    /**
     * Waits with a timeout for RESPONSES of a commercetools client wrapped in a {@link CompletionStage}.
     * This method should not be used for other {@link CompletionStage}s since it is throwing {@link io.sphere.sdk.models.SphereException}s.
     *
     * @param completionStage the future monad to wait for
     * @param sphereRequest the request belonging to the completionStage
     * @param timeout the maximum time to wait for this single request
     * @param unit the time unit of the timeout argument
     * @param <T> type of the result for the request
     * @return the wrapped value of {@code completionStage}
     * @throws SphereTimeoutException if a timeout occurs
     */
    public static <T> T blockingWait(final CompletionStage<T> completionStage, final SphereRequest<T> sphereRequest, final long timeout, final TimeUnit unit) {
        try {
            return completionStage.toCompletableFuture().get(timeout, unit);
        } catch (InterruptedException | ExecutionException e) {
            final Throwable cause =
                    e.getCause() != null && e instanceof ExecutionException
                            ? e.getCause()
                            : e;
            throw cause instanceof RuntimeException? (RuntimeException) cause : new CompletionException(cause);
        } catch (final TimeoutException e) {
            final SphereTimeoutException sphereTimeoutException = new SphereTimeoutException(e);
            if (sphereRequest != null) {
                sphereTimeoutException.setSphereRequest(sphereRequest);
            }
            throw sphereTimeoutException;
        }
    }

    public static <T> List<T> blockingWaitForEach(final Stream<? extends CompletionStage<T>> stream, final Duration duration) {
        return blockingWaitForEach(stream, duration.toMillis(), TimeUnit.MILLISECONDS);
    }

    public static <T> List<T> blockingWaitForEach(final Stream<? extends CompletionStage<T>> stream, final long timeout, final TimeUnit unit) {
        return stream
                .map(stage -> blockingWait(stage, timeout, unit))
                .collect(toList());
    }

    public static <T> List<T> blockingWaitForEach(final List<? extends CompletionStage<T>> list, final Duration duration) {
        return blockingWaitForEach(list, duration.toMillis(), TimeUnit.MILLISECONDS);
    }

    public static <T> List<T> blockingWaitForEach(final List<? extends CompletionStage<T>> list, final long timeout, final TimeUnit unit) {
        return blockingWaitForEach(list.stream(), timeout, unit);
    }

    public static <S extends CompletionStage<T>, T> Collector<S, ?, List<T>> blockingWaitForEachCollector(final long timeout, final TimeUnit unit) {
        final Function<CompletionStage<T>, T> mapper = stage -> blockingWait(stage, timeout, unit);
        return Collectors.mapping(mapper, toList());
    }

    public static <S extends CompletionStage<T>, T> Collector<S, ?, List<T>> blockingWaitForEachCollector(final Duration duration) {
        return blockingWaitForEachCollector(duration.toMillis(), TimeUnit.MILLISECONDS);
    }
}
