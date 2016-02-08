package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

public class ClientTestWrapper extends Base {
    private final SphereClient sphereClient;

    private ClientTestWrapper(final SphereClient sphereClient) {
        this.sphereClient = sphereClient;
    }

    public static ClientTestWrapper of(final SphereClient sphereClient) {
        return new ClientTestWrapper(sphereClient);
    }

    public static <T> T execute(final SphereClient client, final SphereRequest<T> request) {
        return of(client).execute(request);
    }

    public <T> T execute(final SphereRequest<T> request) {
        final T res = sphereClient.execute(request).toCompletableFuture().join();
        return res;
    }
}
