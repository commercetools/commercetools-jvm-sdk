package de.commercetools.sphere.client.shop.oauth;

import de.commercetools.sphere.client.shop.ShopClientConfig;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.oauth.OAuthClient;
import de.commercetools.sphere.client.oauth.Tokens;
import de.commercetools.sphere.client.Endpoints;
import de.commercetools.sphere.client.util.Log;
import com.google.common.base.Optional;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.*;

/** Holds OAuth access tokens for accessing protected Sphere HTTP API endpoints.
 *  Refreshes the access token as needed automatically. */
@ThreadSafe
public class ShopClientCredentials implements ClientCredentials {
    private String tokenEndpoint;
    private String projectID;
    private String clientID;
    private String clientSecret;
    private OAuthClient oauthClient;
    
    private String accessToken;
    private RuntimeException exception;
    private Optional<Long> originalExpiresInSeconds = Optional.<Long>absent();
    /** Time the tokens stored inside this instance were last refreshed, in System.currentTimeMillis(). */
    private long lastUpdateTime = -1L;

    /** Allows at most one refresh operation running in the background. */
    private Executor refreshExecutor = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

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
        refreshAsync(false);
    }

    private Object locker = new Object();

    public String accessToken() {
        synchronized (locker) {
            while (accessToken == null && exception == null) {
                try {
                    locker.wait();
                } catch (InterruptedException e) { }
            }
            if (exception != null) {
                refreshAsync(false);
                throw exception;
            }
            if (originalExpiresInSeconds.isPresent()) {
                // auto refresh
                long expiresAt = lastUpdateTime + 1000 * originalExpiresInSeconds.get();
                long remainingTime = expiresAt - System.currentTimeMillis();
                System.out.println(remainingTime);
                if (remainingTime < 60*1000) { // 1 minute
                    refreshAsync(true);
                }
            }
            return accessToken;
        }
    }

    /** Asynchronously refreshes the tokens contained in this instance. */
    private void refreshAsync(final boolean isAboutToExpire) {
        try {
            refreshExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    if (isAboutToExpire) {
                        Log.debug("[oauth] OAuth token about to expire, refreshing.");
                    } else {
                        Log.debug("[oauth] Refreshing OAuth access token.");
                    }
                    Tokens tokens = null;
                    try {
                        tokens = getTokens();
                    } catch (Exception e) {
                        update(null, e);
                        return;
                    }
                    update(tokens, null);
                }
            });
        } catch (RejectedExecutionException e) {
            // another refresh already in progress, ignore this one
        }
    }

    private void update(Tokens tokens, Exception e) {
        synchronized (locker) {
            if (e == null) {
                this.lastUpdateTime = System.currentTimeMillis();
                this.accessToken = tokens.getAccessToken();
                this.originalExpiresInSeconds = tokens.getExpiresIn();
                this.exception = null;
                Log.debug("[oauth] Refreshed OAuth access token");
            } else {
                this.lastUpdateTime = -1;
                this.accessToken = null;
                this.originalExpiresInSeconds = Optional.absent();
                this.exception = new RuntimeException(e);
                Log.error("[oauth] Failed to refresh OAuth access token.", e);
            }
            locker.notifyAll();
        }
    }

    /** Asynchronously gets tokens from the auth server and updates this instance in place. */
    private Tokens getTokens() throws Exception {
        return oauthClient.getTokensForClient(tokenEndpoint, clientID, clientSecret, "project:" + projectID).get();
    }
}
