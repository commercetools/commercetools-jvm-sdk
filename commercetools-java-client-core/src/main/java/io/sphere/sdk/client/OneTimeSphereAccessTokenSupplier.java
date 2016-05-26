package io.sphere.sdk.client;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

//document auto closing
final class OneTimeSphereAccessTokenSupplier extends AutoCloseableService implements SphereAccessTokenSupplier {
    private final SphereAccessTokenSupplier delegate;
    private final boolean shouldCloseAutomatically;
    private boolean isClosed = false;
    private Optional<CompletionStage<String>> tokenFuture = Optional.empty();

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
    public final synchronized CompletionStage<String> get() {
        rejectExcutionIfClosed("token supplier already closed");
        final CompletionStage<String> future = tokenFuture.orElseGet(() -> {
            final CompletionStage<String> tokenFuture = fetchToken();
            this.tokenFuture = Optional.of(tokenFuture);
            return tokenFuture;
        });
        return future;
    }

    private CompletionStage<String> fetchToken() {
        final CompletionStage<String> result = delegate.get();
        if (shouldCloseAutomatically) {
            result.whenComplete((a, b) -> close());
        }
        return result;
    }

    public static SphereAccessTokenSupplier of(final SphereAccessTokenSupplier delegate, final boolean shouldCloseAutomatically) {
        return new OneTimeSphereAccessTokenSupplier(delegate, shouldCloseAutomatically);
    }
}
