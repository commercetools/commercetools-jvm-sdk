package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;

import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

/** Provides an OAuth token for accessing protected Sphere HTTP API endpoints.
 *
 * There a no guarantees concerning the token providing mechanism.
 *
 * <p>See also {@link TokensFacade} if you just want to fetch an access token for one time.</p>
 */
public interface SphereAccessTokenSupplier extends AutoCloseable, Supplier<CompletionStage<String>> {
    /** Returns the OAuth access token. */
    CompletionStage<String> get();

    void close();

    /**
     * Provides a token generator which tries to always provide a valid token.
     *
     * @param config the configuration to fetch a token
     * @param httpClient used http client
     * @param closeHttpClient set to true, if the httpClient should be closed with the created SphereAccessTokenSupplier
     * @return token service
     */
    static SphereAccessTokenSupplier ofAutoRefresh(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        return AutoRefreshSphereAccessTokenSupplierImpl.createAndBeginRefreshInBackground(config, httpClient, closeHttpClient);
    }

    /**
     * Provides a token generator which just returns a fixed token, so the client is only usable
     * for the live time of this token.
     *
     * @param token the token which will be passed to the client
     * @return token service
     */
    static SphereAccessTokenSupplier ofConstantToken(final String token) {
        return new SphereConstantAccessTokenSupplierImpl(token);
    }

    /**
     * Provides a token generator which fetches only one token. This could be useful for integration tests.
     *
     * @param config the configuration to fetch a token
     * @param httpClient used http client, will be closed with this generator on {@link #close()}
     * @param closeHttpClient set to true, if the httpClient should be closed with the created SphereAccessTokenSupplier
     * @return token service
     */
    static SphereAccessTokenSupplier ofOneTimeFetchingToken(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        final OnDemandSphereAccessTokenSupplier delegate = OnDemandSphereAccessTokenSupplier.of(config, httpClient, closeHttpClient);
        return OneTimeSphereAccessTokenSupplier.of(delegate, true);
    }
}
