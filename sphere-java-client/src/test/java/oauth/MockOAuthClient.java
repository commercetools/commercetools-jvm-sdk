package de.commercetools.sphere.client.oauth;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

public class MockOAuthClient extends OAuthClient {

    public MockOAuthClient() {
        super(null);
    }

    /** Exposes OAuthClient's protected method for testing purposes. */
    public Tokens parseResponse(Response resp, AsyncHttpClient.BoundRequestBuilder requestBuilder) {
        return super.parseResponse(resp, requestBuilder);
    }
}
