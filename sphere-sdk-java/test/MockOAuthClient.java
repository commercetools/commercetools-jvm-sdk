package sphere;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import sphere.util.OAuthClient;
import sphere.util.OAuthTokens;

public class MockOAuthClient extends OAuthClient {

    public MockOAuthClient() {
        super(null);
    }

    /** Exposes OAuthClient's protected method for testing purposes. */
    public OAuthTokens parseResponse(Response resp, AsyncHttpClient.BoundRequestBuilder requestBuilder) {
        return super.parseResponse(resp, requestBuilder);
    }
}
