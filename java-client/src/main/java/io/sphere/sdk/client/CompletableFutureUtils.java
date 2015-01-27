package io.sphere.sdk.client;

import java.util.concurrent.*;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;

final class CompletableFutureUtils {
    private CompletableFutureUtils() {
    }

    /**
     * Creates a {@link java.util.concurrent.CompletableFuture} from a {@link com.ning.http.client.ListenableFuture}.
     * @param listenableFuture the future of the ning library
     * @param executor the executor to run the future in
     * @param <T> Type of the value that will be returned.
     * @return the Java 8 future implementation
     */
    static <T> CompletableFuture<T> wrap(final ListenableFuture<T> listenableFuture, final Executor executor) {
        final CompletableFuture<T> result = new CompletableFuture<>();
        final Runnable listener = () -> {
            try {
                final T value = listenableFuture.get();
                result.complete(value);
            } catch (final InterruptedException | ExecutionException e) {
                result.completeExceptionally(e.getCause());
            }
        };
        listenableFuture.addListener(listener, executor);
        return result;
    }

    public static CompletableFuture<Response> wrap(final ListenableFuture<Response> listenableFuture) {
        return wrap(listenableFuture, ForkJoinPool.commonPool());
    }

    public static <T> CompletableFuture<T> fullFilled(final T object) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        future.complete(object);
        return future;
    }
}
