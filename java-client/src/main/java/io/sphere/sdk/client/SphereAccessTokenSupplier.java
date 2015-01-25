package io.sphere.sdk.client;


import com.ning.http.client.AsyncHttpClient;

import java.io.Closeable;
import java.util.function.Supplier;

/** Provides an OAuth token for accessing protected Sphere HTTP API endpoints.
 *
 * There a no guarantees concerning the token providing mechanism.
 */
public interface SphereAccessTokenSupplier extends Closeable, Supplier<String> {
    /** Returns the OAuth access token. */
    public String get();

    public void close();

    /**
     * Provides a token generator which just returns a fixed token, so the client is usable
     * for the live time of this token.
     *
     * @param token the token which will be passed to the client
     * @return token service
     */
    static SphereAccessTokenSupplier ofFixedToken(final String token) {
        return new SphereFixedAccessTokenSupplierImpl(token);
    }

    /**
     * Provides a token generator which tries to always provide a valid token.
     *
     * @param config the configuration to fetch a token
     * @return token service
     */
    static SphereAccessTokenSupplier ofAutoRefresh(final SphereAuthConfig config) {
        return SphereAccessTokenSupplierImpl.createAndBeginRefreshInBackground(config, new OAuthClient(new AsyncHttpClient()));
    }
}
