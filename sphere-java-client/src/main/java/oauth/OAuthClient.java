package de.commercetools.sphere.client.oauth;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import de.commercetools.sphere.client.async.ListenableFutureAdapter;
import de.commercetools.sphere.client.util.Headers;
import de.commercetools.sphere.client.AuthorizationException;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class OAuthClient {
    private AsyncHttpClient httpClient;

    public OAuthClient(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /** Asynchronously gets access and refresh tokens for given user from the authorization server
     *  using the Resource owner credentials flow. */
    public ListenableFuture<Tokens> getTokensForClient(
            final String tokenEndpoint, final String clientID, final String clientSecret, final String scope)
    {
        try {
            final AsyncHttpClient.BoundRequestBuilder requestBuilder = httpClient.preparePost(tokenEndpoint)
                    .setHeader("Authorization", Headers.encodeBasicAuthHeader(clientID, clientSecret))
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addQueryParameter("grant_type", "client_credentials")
                    .addQueryParameter("scope", scope);
            ListenableFuture<Response> getResponse = new ListenableFutureAdapter<Response>(requestBuilder.execute());
            return Futures.transform(getResponse, new Function<Response, Tokens>() {
                @Override
                public Tokens apply(Response resp) {
                    return parseResponse(resp, requestBuilder);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Parses Tokens from a response from the backend authorization service.
     *  @param resp Response from the authorization service.
     *  @param requestBuilder The request, used for error reporting. */
    protected Tokens parseResponse(Response resp, AsyncHttpClient.BoundRequestBuilder requestBuilder) {
        try {
            if (resp.getStatusCode() != 200) {
                throw new AuthorizationException(
                        "POST " + requestBuilder.build().getRawUrl() + " : " + resp.getStatusCode() + " " + resp.getResponseBody());
            }
            JsonNode json = new ObjectMapper().readValue(resp.getResponseBody(), JsonNode.class);
            String accessToken = json.path("access_token").getTextValue();
            boolean hasExpiresIn = json.path("expires_in").isNumber();
            Optional<Long> expiresIn = hasExpiresIn ? Optional.of(json.path("expires_in").getLongValue()) : Optional.<Long>absent();
            String refreshToken = json.path("refresh_token").getTextValue();
            return new Tokens(accessToken, refreshToken, expiresIn);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}