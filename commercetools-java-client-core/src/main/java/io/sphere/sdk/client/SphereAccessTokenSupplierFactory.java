package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.models.Base;

import java.util.function.Supplier;

@Deprecated
public final class SphereAccessTokenSupplierFactory extends Base {
    private final Supplier<HttpClient> httpClientSupplier;

    private SphereAccessTokenSupplierFactory(final Supplier<HttpClient> httpClientSupplier) {
        this.httpClientSupplier = httpClientSupplier;
    }

    public static SphereAccessTokenSupplierFactory of(final Supplier<HttpClient> httpClientSupplier) {
        return new SphereAccessTokenSupplierFactory(httpClientSupplier);
    }

    /**
     * Provides a token generator which just returns a fixed token, so the client is usable
     * for the live time of this token.
     *
     * @param token the token which will be passed to the client
     * @return token service
     */
    public static SphereAccessTokenSupplier createSupplierOfFixedToken(final String token) {
        return SphereAccessTokenSupplier.ofConstantToken(token);
    }

    /**
     * Provides a token generator which tries to always provide a valid token.
     *
     * @param config the configuration to fetch a token
     * @return token service
     */
    public SphereAccessTokenSupplier createSupplierOfAutoRefresh(final SphereAuthConfig config) {
        return SphereAccessTokenSupplier.ofAutoRefresh(config, createHttpClient(), true);
    }

    public SphereAccessTokenSupplier createSupplierOfOneTimeFetchingToken(final SphereAuthConfig config) {
        return SphereAccessTokenSupplier.ofOneTimeFetchingToken(config, createHttpClient(), true);
    }

    private HttpClient createHttpClient() {
        return httpClientSupplier.get();
    }
}
