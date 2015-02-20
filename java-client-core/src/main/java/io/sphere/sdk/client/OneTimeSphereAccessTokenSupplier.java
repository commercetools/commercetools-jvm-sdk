package io.sphere.sdk.client;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

//document auto closing
final class OneTimeSphereAccessTokenSupplier extends AutoCloseableService implements SphereAccessTokenSupplier {
    private final SphereAccessTokenSupplier delegate;
    private final boolean shouldCloseAutomatically;
    private boolean isClosed = false;
    private Optional<CompletableFuture<String>> tokenFuture = Optional.empty();

    private OneTimeSphereAccessTokenSupplier(final SphereAccessTokenSupplier delegate, final boolean shouldCloseAutomatically) {
        this.delegate = delegate;
        this.shouldCloseAutomatically = shouldCloseAutomatically;
    }

    @Override
    protected synchronized void internalClose() {
        if (shouldCloseAutomatically && !isClosed) {
            delegate.close();
            isClosed = true;
        }
    }

    @Override
    public final synchronized CompletableFuture<String> get() {
        final CompletableFuture<String> future = tokenFuture.orElseGet(() -> {
            final CompletableFuture<String> tokenFuture = fetchToken();
            this.tokenFuture = Optional.of(tokenFuture);
            return tokenFuture;
        });
        return future;
    }

    private CompletableFuture<String> fetchToken() {
        final CompletableFuture<String> result = delegate.get();
        if (shouldCloseAutomatically) {
            result.whenComplete((a, b) -> close());
        }
        return result;
    }

    public static SphereAccessTokenSupplier of(final SphereAccessTokenSupplier delegate, final boolean shouldCloseAutomatically) {
        return new OneTimeSphereAccessTokenSupplier(delegate, shouldCloseAutomatically);
    }
}
