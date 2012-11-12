package de.commercetools.internal.oauth;

import de.commercetools.internal.Defaults;
import de.commercetools.internal.util.Validation;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.shop.ShopClientConfig;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.oauth.OAuthClient;
import de.commercetools.sphere.client.oauth.Tokens;
import de.commercetools.sphere.client.Endpoints;
import de.commercetools.internal.util.Log;
import com.google.common.base.Optional;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/** Holds OAuth access tokens for accessing protected Sphere HTTP API endpoints.
 *  Refreshes the access token as needed automatically. */
@ThreadSafe
public final class ShopClientCredentials implements ClientCredentials {
    private final String tokenEndpoint;
    private final String projectKey;
    private final String clientID;
    private final String clientSecret;
    private final OAuthClient oauthClient;

    private final Object accessTokenLock = new Object();
    @GuardedBy("accessTokenLock")
    private Optional<Validation<AccessToken>> accessToken = Optional.absent();

    /** Allows at most one refresh operation running in the background. */
    private final Executor refreshExecutor = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    private final Timer refreshTimer = new Timer("access token refresh timer", true);

    /** Creates an instance of ClientCredentials based on config. */
    public static ShopClientCredentials createAndBeginRefreshInBackground(ShopClientConfig config, OAuthClient oauthClient) {
        String tokenEndpoint = Endpoints.tokenEndpoint(config.getAuthHttpServiceUrl());
        ShopClientCredentials credentials = new ShopClientCredentials(oauthClient, tokenEndpoint, config.getProjectKey(), config.getClientId(), config.getClientSecret());
        credentials.beginRefresh();
        return credentials;
    }

    private ShopClientCredentials(OAuthClient oauthClient, String tokenEndpoint, String projectKey, String clientID, String clientSecret) {
        this.oauthClient  = oauthClient;
        this.tokenEndpoint = tokenEndpoint;
        this.projectKey = projectKey;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }

    public String accessToken() {
        synchronized (accessTokenLock) {
            Optional<Validation<AccessToken>> token = waitForTokenAndClearIfExpired();
            if (!token.isPresent()) {
                Log.warn("[oauth] Access token expired, blocking until a new one is available.");
                beginRefresh();
                token = waitForTokenAndClearIfExpired();
            }
            if (!token.isPresent()) {
                throw new AssertionError("Access token expired immediately after refresh.");
            }
            if (token.get().isError()) {
                beginRefresh();   // retry on backend error
                throw token.get().exception();
            }
            return token.get().value().accessToken();
        }
    }

    /** If there is an access token present, check whether it's not expired yet and returns it.
     *  If it's already expired, clears the token. */
    private Optional<Validation<AccessToken>> waitForTokenAndClearIfExpired() {
        while (!accessToken.isPresent()) {
            try {
                accessTokenLock.wait();
            } catch (InterruptedException e) { }
        }
        if (accessToken.get().isError()) {
            return accessToken;
        }
        Optional<Long> remainingMs = accessToken.get().value().remainingMs();
        if (remainingMs.isPresent()) {
            // Have some tolerance here so that we don't send tokens with validity 100ms to the server
            // and they expire "on the way"
            if (remainingMs.get() <= 2000) {
                // if the token expired, clear it
                accessToken = Optional.absent();
            }
        }
        return accessToken;
    }

    /** Asynchronously refreshes the tokens contained in this instance. */
    private void beginRefresh() {
        try {
            refreshExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.debug("[oauth] Refreshing access token.");
                    Tokens tokens = null;
                    try {
                        tokens = oauthClient.getTokensForClient(tokenEndpoint, clientID, clientSecret, "project:" + projectKey).get();
                    } catch (Exception e) {
                        update(null, e);
                        return;
                    }
                    update(tokens, null);
                }
            });
        } catch (RejectedExecutionException e) {
            // another refresh is already in progress, ignore this one
        }
    }

    private void update(Tokens tokens, Exception e) {
        synchronized (accessTokenLock) {
            if (e == null) {
                AccessToken newToken = new AccessToken(tokens.getAccessToken(), tokens.getExpiresIn(), System.currentTimeMillis());
                this.accessToken = Optional.of(Validation.success(newToken));
                Log.debug("[oauth] Refreshed access token.");
                if (tokens.getExpiresIn().isPresent()) {
                    if (tokens.getExpiresIn().get() * 1000 > Defaults.tokenAboutToExpireMs) {
                        // don't wait until the very last moment
                        long refreshTimeout = tokens.getExpiresIn().get() * 1000 - Defaults.tokenAboutToExpireMs;
                        Log.debug("[oauth] Scheduling next token refresh " + refreshTimeout / 1000 + "s from now.");
                        refreshTimer.schedule(new TimerTask() {
                            @Override public void run() {
                                beginRefresh();
                            }
                        }, refreshTimeout);
                    } else {
                        Log.warn("[oauth] Authorization server returned an access token with very low validity of only " +
                                tokens.getExpiresIn().get() + "s!");
                    }
                } else {
                    Log.warn("[oauth] Authorization server did not provide expires_in for the access token.");
                }
            } else {
                this.accessToken = Optional.of(Validation.<AccessToken>error(new SphereException(e)));
                Log.error("[oauth] Failed to refresh access token.", e);
            }
            accessTokenLock.notifyAll();
        }
    }
}
