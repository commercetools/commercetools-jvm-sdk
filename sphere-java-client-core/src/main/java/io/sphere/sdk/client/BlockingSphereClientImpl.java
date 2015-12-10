package io.sphere.sdk.client;

import java.util.concurrent.*;

final class BlockingSphereClientImpl implements BlockingSphereClient {

    private final SphereClient delegate;
    private final long defaultTimeout;
    private final TimeUnit unit;

    public BlockingSphereClientImpl(final SphereClient delegate, final long defaultTimeout, final TimeUnit unit) {
        this.delegate = delegate;
        this.defaultTimeout = defaultTimeout;
        this.unit = unit;
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
        return delegate.execute(sphereRequest);
    }

    @Override
    public <T> T executeBlocking(final SphereRequest<T> sphereRequest) {
        return executeBlocking(sphereRequest, defaultTimeout, unit);
    }

    @Override
    public <T> T executeBlocking(final SphereRequest<T> sphereRequest, final long timeout, final TimeUnit unit) {
        try {
            return execute(sphereRequest).toCompletableFuture().get(timeout, unit);
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
