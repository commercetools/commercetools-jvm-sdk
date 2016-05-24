package io.sphere.sdk.client;

public final class RetrySphereClient {
    public static SphereClient of(final SphereClient delegate) {
        return new RetrySphereClientImpl(delegate);
    }
}
