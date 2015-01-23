package io.sphere.sdk.client;

public class JavaClientFactory implements ClientFactory<JavaClient> {
    private JavaClientFactory() {
    }

    @Override
    public JavaClient createClient(final SphereClientConfig config) {
        return new JavaClientImpl(config);
    }

    public static JavaClientFactory of() {
        return new JavaClientFactory();
    }
}
