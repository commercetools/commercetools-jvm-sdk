package io.sphere.sdk.client;

public class SphereClientFactory implements ClientFactory<SphereClient> {
    private SphereClientFactory() {
    }

    @Override
    public SphereClient createClient(final SphereClientConfig config) {
        return new SphereClientImpl(config);
    }

    public static SphereClientFactory of() {
        return new SphereClientFactory();
    }
}
