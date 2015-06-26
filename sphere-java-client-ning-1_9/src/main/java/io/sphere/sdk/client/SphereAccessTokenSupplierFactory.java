package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.NingHttpClientAdapter;

public final class SphereAccessTokenSupplierFactory {
    private SphereAccessTokenSupplierFactory() {
    }

    public static SphereAccessTokenSupplierFactory of() {
        return new SphereAccessTokenSupplierFactory();
    }

    /**
     * Provides a token generator which just returns a fixed token, so the client is usable
     * for the live time of this token.
     *
     * @param token the token which will be passed to the client
     * @return token service
     */
    public SphereAccessTokenSupplier createSupplierOfFixedToken(final String token) {
        return SphereAccessTokenSupplier.ofConstantToken(token);
    }

    /**
     * Provides a token generator which tries to always provide a valid token.
     *
     * @param config the configuration to fetch a token
     * @return token service
     */
    public SphereAccessTokenSupplier createSupplierOfAutoRefresh(final SphereAuthConfig config) {
        return SphereAccessTokenSupplier.ofAutoRefresh(config, defaultHttpClient(), true);
    }

    public SphereAccessTokenSupplier createSupplierOfOneTimeFetchingToken(final SphereAuthConfig config) {
        return SphereAccessTokenSupplier.ofOneTimeFetchingToken(config, defaultHttpClient(), true);
    }

    private HttpClient defaultHttpClient() {
        return NingHttpClientAdapterFactory.create();
    }
}
