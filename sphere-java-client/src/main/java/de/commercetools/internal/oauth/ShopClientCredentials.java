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

import java.util.concurrent.*;

/** Holds OAuth access tokens for accessing protected Sphere HTTP API endpoints.
 *  Refreshes the access token as needed automatically. */
@ThreadSafe
public final class ShopClientCredentials implements ClientCredentials {
    private final String tokenEndpoint;
    private final String projectID;
    private final String clientID;
    private final String clientSecret;
    private final OAuthClient oauthClient;

    private final Object accessTokenLock = new Object();
    @GuardedBy("accessTokenLock")
    private Optional<Validation<AccessToken>> accessToken = Optional.absent();

    /** Allows at most one refresh operation running in the background. */
    private final Executor refreshExecutor = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

    /** Creates an instance of ClientCredentials based on config. */
    public static ShopClientCredentials createAndBeginRefreshInBackground(ShopClientConfig config, OAuthClient oauthClient) {
        String authEndpoint = Endpoints.tokenEndpoint(config.getAuthHttpServiceUrl());
        ShopClientCredentials credentials = new ShopClientCredentials(oauthClient, authEndpoint, config.getProjectKey(), config.getClientId(), config.getClientSecret());
        credentials.beginRefresh(false);
        return credentials;
    }

    private ShopClientCredentials(OAuthClient oauthClient, String tokenEndpoint, String projectID, String clientID, String clientSecret) {
        this.oauthClient  = oauthClient;
        this.tokenEndpoint = tokenEndpoint;
        this.projectID = projectID;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }

    public String accessToken() {
        synchronized (accessTokenLock) {
            while (!accessToken.isPresent()) {
                try {
                    accessTokenLock.wait();
                } catch (InterruptedException e) { }
            }
            Validation<AccessToken> tokenValidation = accessToken.get();
            if (tokenValidation.isError()) {
                beginRefresh(false);
                throw tokenValidation.exception();
            }
            AccessToken token = tokenValidation.value();
            if (token.remainingMs().isPresent()) {
                if (token.remainingMs().get() < Defaults.tokenAboutToExpireMs) {
                    // token about to expire -> auto refresh
                    beginRefresh(true);
                }
            }
            return token.accessToken();
        }
    }

    /** Asynchronously refreshes the tokens contained in this instance. */
    private void beginRefresh(final boolean isAboutToExpire) {
        try {
            refreshExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.debug(isAboutToExpire ?
                            "[oauth] OAuth token about to expire, refreshing." :
                            "[oauth] Refreshing OAuth access token.");
                    Tokens tokens = null;
                    try {
                        tokens = oauthClient.getTokensForClient(tokenEndpoint, clientID, clientSecret, "project:" + projectID).get();
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
        if (e != null) {
            Log.error("Couldn't initialize OAuth credentials", e);
        }
        synchronized (accessTokenLock) {
            if (e == null) {
                this.accessToken = Optional.of(Validation.success(new AccessToken(
                        tokens.getAccessToken(), tokens.getExpiresIn(), System.currentTimeMillis())));
                Log.debug("[oauth] Refreshed OAuth access token.");
            } else {
                this.accessToken = Optional.of(Validation.<AccessToken>error(new SphereException(e)));
                Log.error("[oauth] Failed to refresh OAuth access token.", e);
            }
            accessTokenLock.notifyAll();
        }
    }
}
