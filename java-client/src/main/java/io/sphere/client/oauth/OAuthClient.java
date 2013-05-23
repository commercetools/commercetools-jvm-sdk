package io.sphere.client.oauth;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Realm;
import com.ning.http.client.Response;
import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import io.sphere.client.AuthorizationException;
import io.sphere.internal.ListenableFutureAdapter;
import io.sphere.internal.util.Log;
import io.sphere.internal.util.Util;

import java.io.IOException;

public class OAuthClient {
    private AsyncHttpClient httpClient;

    public OAuthClient(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /** Asynchronously gets access and refresh tokens for given user from the authorization server
     *  using the Resource owner credentials flow. */
    public ListenableFuture<Tokens> getTokensForClient(
            final String tokenEndpoint, final String clientId, final String clientSecret, final String scope)
    {
        try {
            Realm basicAuthRealm = new Realm.RealmBuilder()
                       .setPrincipal(clientId)
                       .setPassword(clientSecret)
                       .setScheme(Realm.AuthScheme.BASIC)
                       .build();
            final AsyncHttpClient.BoundRequestBuilder requestBuilder = httpClient.preparePost(tokenEndpoint)
                    .setRealm(basicAuthRealm)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addQueryParameter("grant_type", "client_credentials")
                    .addQueryParameter("scope", scope);
            return Futures.transform(new ListenableFutureAdapter<Response>(requestBuilder.execute()), new Function<Response, Tokens>() {
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
            if (Log.isDebugEnabled()) {
                Log.debug(Util.requestToString(requestBuilder.build()) + "\n(auth server response not logged for security reasons)");
            }
            if (resp.getStatusCode() != 200) {
                throw new AuthorizationException(Util.requestResponseToString(requestBuilder.build(), resp));
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
