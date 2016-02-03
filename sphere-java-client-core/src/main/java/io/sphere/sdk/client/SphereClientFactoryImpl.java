package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.models.Base;

import java.util.function.Supplier;

final class SphereClientFactoryImpl extends Base implements SphereClientFactory {
    private final Supplier<HttpClient> httpClientSupplier;

    SphereClientFactoryImpl(final Supplier<HttpClient> httpClientSupplier) {
        this.httpClientSupplier = httpClientSupplier;
    }

    @Override
    public HttpClient createHttpClient() {
        return httpClientSupplier.get();
    }
}
