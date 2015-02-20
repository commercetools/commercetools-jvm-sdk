package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Realm;
import com.ning.http.client.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import static io.sphere.sdk.utils.SphereInternalLogger.*;
import static java.lang.String.format;

class OAuthClient {
    private final AsyncHttpClient httpClient;

    public OAuthClient(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /** Asynchronously gets access and refresh tokens for given user from the authorization server
     *  using the Resource owner credentials flow. */
    public CompletableFuture<Tokens> getTokensForClient(
            final String tokenEndpoint, final String clientId, final String clientSecret, final String scope) {
        if (!httpClient.isClosed()) {
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

                final CompletableFuture<Tokens> tokensCompletableFuture;
                if (httpClient.isClosed()) {
                    tokensCompletableFuture = new CompletableFuture<>();
                    tokensCompletableFuture.completeExceptionally(new IllegalStateException("client already closed"));
                } else {
                    tokensCompletableFuture = wrap(requestBuilder.execute()).thenApply((Response resp) -> parseResponse(resp, requestBuilder));
                }
                return tokensCompletableFuture;
            } catch (IOException e) {
                throw new RuntimeException(e);//TODO
            }
        } else {
            throw new IllegalStateException(format("Http client %s is already closed.", httpClient));
        }
    }

    /**
     * Creates a {@link java.util.concurrent.CompletableFuture} from a {@link com.ning.http.client.ListenableFuture}.
     * @param listenableFuture the future of the ning library
     * @param executor the executor to run the future in
     * @param <T> Type of the value that will be returned.
     * @return the Java 8 future implementation
     */
    private static <T> CompletableFuture<T> wrap(final ListenableFuture<T> listenableFuture, final Executor executor) {
        final CompletableFuture<T> result = new CompletableFuture<>();
        final Runnable listener = () -> {
            try {
                final T value = listenableFuture.get();
                result.complete(value);
            } catch (final InterruptedException | ExecutionException e) {
                result.completeExceptionally(e.getCause());
            }
        };
        listenableFuture.addListener(listener, executor);
        return result;
    }

    private static CompletableFuture<Response> wrap(final ListenableFuture<Response> listenableFuture) {
        return wrap(listenableFuture, ForkJoinPool.commonPool());
    }

    /** Parses Tokens from a response from the backend authorization service.
     *  @param resp Response from the authorization service.
     *  @param requestBuilder The request, used for error reporting. */
    protected Tokens parseResponse(Response resp, AsyncHttpClient.BoundRequestBuilder requestBuilder) {
        try {
            getLogger("oauth").debug(() -> requestBuilder.build().toString() + "\n(auth server response not logged for security reasons)");
            if (resp.getStatusCode() != 200) {
                throw new AuthorizationException(requestBuilder.build().toString() + " " + resp);//TODO
            }
            JsonNode json = new ObjectMapper().readValue(resp.getResponseBody(), JsonNode.class);
            String accessToken = json.path("access_token").textValue();
            boolean hasExpiresIn = json.path("expires_in").isNumber();
            Optional<Long> expiresIn = hasExpiresIn ? Optional.of(json.path("expires_in").longValue()) : Optional.<Long>empty();
            String refreshToken = json.path("refresh_token").textValue();
            return new Tokens(accessToken, refreshToken, expiresIn);
        } catch (IOException e) {
            throw new RuntimeException(e);//TODO
        }
    }
}
