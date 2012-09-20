package de.commercetools.internal;

import com.ning.http.client.AsyncHttpClient;
import de.commercetools.sphere.client.oauth.ClientCredentials;

public class SetCredentials {
    /** Sets OAuth authorization header for a request. */
    public static AsyncHttpClient.BoundRequestBuilder forRequest(AsyncHttpClient.BoundRequestBuilder builder, ClientCredentials credentials) {
        return builder.setHeader("Authorization", "Bearer " + credentials.accessToken());
    }
}
