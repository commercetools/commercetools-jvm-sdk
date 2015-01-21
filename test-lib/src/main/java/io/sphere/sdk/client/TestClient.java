package io.sphere.sdk.client;

import io.sphere.sdk.http.ClientRequest;

import java.util.concurrent.ExecutionException;

public final class TestClient {
    private final JavaClient underlying;

    public TestClient(final JavaClient underlying) {
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
