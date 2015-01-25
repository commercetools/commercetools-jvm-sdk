package io.sphere.sdk.client;

public class SphereJavaClientFactory implements ClientFactory<SphereClient> {
    private SphereJavaClientFactory() {
    }

    @Override
    public SphereClient createClient(final SphereClientConfig config) {
        return new SphereClientImpl(config);
    }

    public static SphereJavaClientFactory of() {
        return new SphereJavaClientFactory();
    }
}
