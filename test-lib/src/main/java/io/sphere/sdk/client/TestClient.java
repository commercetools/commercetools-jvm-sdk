package io.sphere.sdk.client;

import java.util.concurrent.ExecutionException;

public final class TestClient {
    private final SphereClient underlying;

    public TestClient(final SphereClient underlying) {
        this.underlying = underlying;
    }

    public <T> T execute(final ClientRequest<T> clientRequest) {
        try {
            return underlying.execute(clientRequest).get();
        } catch (final InterruptedException | ExecutionException e) {
            throw (e.getCause() instanceof RuntimeException) ? ((RuntimeException) e.getCause()) : new TestClientException(e);
        }
    }

    public void close() {
        underlying.close();
    }
}
