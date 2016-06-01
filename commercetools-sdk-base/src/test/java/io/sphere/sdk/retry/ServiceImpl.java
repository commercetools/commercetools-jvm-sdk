package io.sphere.sdk.retry;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ServiceImpl implements Service {
    private boolean closed = false;

    @Override
    public CompletionStage<Integer> apply(final String s) {
        return CompletableFuture.completedFuture(s.length());
    }

    @Override
    public final void close() throws Exception {
        closed = true;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
