package de.commercetools.sphere.client.shop.oauth;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.sphere.client.shop.ShopClientConfig;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.oauth.OAuthClient;
import de.commercetools.sphere.client.oauth.Tokens;
import de.commercetools.sphere.client.Endpoints;
import net.jcip.annotations.ThreadSafe;

import javax.annotation.Nullable;

/** Holds OAuth access tokens for accessing protected Sphere HTTP API endpoints.
 *  Refreshes the access token as needed automatically. */
// TODO auto refreshing
@ThreadSafe
public class ShopClientCredentials implements ClientCredentials {
    private String tokenEndpoint;
    private String projectID;
    private String clientID;
    private String clientSecret;
    private OAuthClient oauthClient;
    
    private String accessToken ;
    private Optional<Long> originalExpiresInSeconds = Optional.<Long>absent();
    /** Time the tokens stored inside this instance were last refreshed, in System.currentTimeMillis(). */
    private long lastUpdateTime = -1L;

    /** Creates an instance of ClientCredentials based on config. */
    public static ShopClientCredentials create(ShopClientConfig config, OAuthClient oauthClient) {
        String authEndpoint = Endpoints.tokenEndpoint(config.getAuthHttpServiceUrl());
        return new ShopClientCredentials(oauthClient, authEndpoint, config.getProjectKey(), config.getClientId(), config.getClientSecret());
    }

    ShopClientCredentials(OAuthClient oauthClient, String tokenEndpoint, String projectID, String clientID, String clientSecret) {
        this.oauthClient  = oauthClient;
        this.tokenEndpoint = tokenEndpoint;
        this.projectID = projectID;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }

    public synchronized String accessToken() {
        return accessToken;
    }
    /** Original validity of the access token in seconds. */
    public synchronized Optional<Long> originalExpiresInSeconds() {
        return originalExpiresInSeconds;
    }
    /** Remaining validity of the access token in seconds. */
    public synchronized Optional<Long> expiresInSeconds() {
        return originalExpiresInSeconds.transform(new Function<Long, Long>() {
            public Long apply(@Nullable Long exp) {
                return Math.max(0, exp - (System.currentTimeMillis() - lastUpdateTime) / 1000L);
            }
        });
    }

    /** Asynchronously refreshes the tokens contained in this instance. */
    public ListenableFuture<Void> refreshAsync() {
        return Futures.transform(getTokenAsync(), new Function<Tokens, Void>() {
            @Override
            public Void apply(Tokens tokens) {
                ShopClientCredentials.this.update(tokens);
                return null;
            }
        });
    }

    private synchronized void update(Tokens tokens) {
        this.lastUpdateTime = System.currentTimeMillis();
        this.accessToken = tokens.getAccessToken();
        this.originalExpiresInSeconds = tokens.getExpiresIn();
    }

    /** Asynchronously gets tokens from the auth server and updates this instance in place. */
    private ListenableFuture<Tokens> getTokenAsync() {
        return oauthClient.getTokensForClient(tokenEndpoint, clientID, clientSecret, "project:" + projectID);
    }
}
