package io.sphere.sdk.client;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.*;

public final class SphereClientUtils {
    private SphereClientUtils() {
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
        return blockingWait(completionStage, duration.get(ChronoUnit.NANOS), TimeUnit.NANOSECONDS);
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
        try {
            return completionStage.toCompletableFuture().get(timeout, unit);
        } catch (InterruptedException | ExecutionException e) {
            final Throwable cause =
                    e.getCause() != null && e instanceof ExecutionException
                            ? e.getCause()
                            : e;
            throw cause instanceof RuntimeException? (RuntimeException) cause : new CompletionException(cause);
        } catch (final TimeoutException e) {
            throw new SphereTimeoutException(e);
        }
    }
}
