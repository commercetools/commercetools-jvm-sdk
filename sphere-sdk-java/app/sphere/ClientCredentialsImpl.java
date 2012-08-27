package sphere;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.sphere.client.shop.ClientCredentials;
import de.commercetools.sphere.client.Endpoints;
import sphere.util.OAuthClient;
import sphere.util.OAuthTokens;

import javax.annotation.Nullable;

/** Holds OAuth access tokens for accessing protected Sphere HTTP API endpoints.
 *  Refreshes the access token as needed automatically. */
// TODO auto refreshing
class ClientCredentialsImpl implements ClientCredentials {

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
    public static ClientCredentialsImpl create(Config config, OAuthClient oauthClient) {
        String authEndpoint = Endpoints.tokenEndpoint(config.authEndpoint());
        return new ClientCredentialsImpl(oauthClient, authEndpoint, config.projectID(), config.clientID(), config.clientSecret());
    }

    ClientCredentialsImpl(OAuthClient oauthClient, String tokenEndpoint, String projectID, String clientID, String clientSecret) {
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

    /** Asynchronously refreshes the tokens contained in this ClientCredentials instance. */
    public ListenableFuture<Void> refreshAsync() {
        return Futures.transform(getTokenAsync(), new AsyncFunction<OAuthTokens, Void>() {
            @Override
            public ListenableFuture<Void> apply(OAuthTokens tokens) throws Exception {
                ClientCredentialsImpl.this.update(tokens);
                return null;  // exceptions will be propagated to caller
            }
        });
    }

    private synchronized void update(OAuthTokens tokens) {
        this.lastUpdateTime = System.currentTimeMillis();
        this.accessToken = tokens.getAccessToken();
        this.originalExpiresInSeconds = tokens.getExpiresIn();
    }

    /** Asynchronously gets tokens from the auth server and updates this instance in place. */
    private ListenableFuture<OAuthTokens> getTokenAsync() {
        return oauthClient.getTokensForClient(tokenEndpoint, clientID, clientSecret, "project:" + projectID);
    }
}
