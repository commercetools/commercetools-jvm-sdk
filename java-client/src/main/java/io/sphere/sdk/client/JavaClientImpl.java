package io.sphere.sdk.client;

import com.typesafe.config.Config;
import io.sphere.sdk.requests.ClientRequest;

import java.util.concurrent.CompletableFuture;

public class JavaClientImpl implements JavaClient {
    private final SphereRequestExecutor sphereRequestExecutor;


    public JavaClientImpl(final Config config) {
        this(config, new NingAsyncHttpClient(config));
    }

    public JavaClientImpl(final Config config, final HttpClient httpClient) {
        this(config, new HttpSphereRequestExecutor(httpClient, config));
    }

    public JavaClientImpl(final Config config, final SphereRequestExecutor sphereRequestExecutor) {
        this.sphereRequestExecutor = sphereRequestExecutor;
    }

    public <T> CompletableFuture<T> execute(final ClientRequest<T> requestable) {
        return sphereRequestExecutor.execute(requestable);
    }

    @Override
    public void close() {
        sphereRequestExecutor.close();
    }
}
