package de.commercetools.internal.oauth;

import de.commercetools.internal.Constants;
import de.commercetools.internal.util.Validation;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.shop.ShopClientConfig;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.oauth.OAuthClient;
import de.commercetools.sphere.client.oauth.Tokens;
import de.commercetools.sphere.client.Endpoints;
import de.commercetools.sphere.client.util.Log;
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

    private final Object locker = new Object();
    @GuardedBy("locker")
    private Optional<Validation<AccessToken>> accessToken = Optional.absent();

    /** Allows at most one refresh operation running in the background. */
    private final Executor refreshExecutor = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

    /** Creates an instance of ClientCredentials based on config. */
    public static ShopClientCredentials create(ShopClientConfig config, OAuthClient oauthClient) {
        String authEndpoint = Endpoints.tokenEndpoint(config.getAuthHttpServiceUrl());
        ShopClientCredentials credentials = new ShopClientCredentials(oauthClient, authEndpoint, config.getProjectKey(), config.getClientId(), config.getClientSecret());
        credentials.refreshAsync(false);
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
        synchronized (locker) {
            while (!accessToken.isPresent()) {
                try {
                    locker.wait();
                } catch (InterruptedException e) { }
            }
            Validation<AccessToken> tokenValidation = accessToken.get();
            if (tokenValidation.isError()) {
                refreshAsync(false);
                throw tokenValidation.exception();
            }
            AccessToken token = tokenValidation.value();
            if (token.remainingMs().isPresent()) {
                if (token.remainingMs().get() < Constants.tokenAboutToExpireMs) {
                    // token about to expire -> auto refresh
                    refreshAsync(true);
                }
            }
            return token.accessToken();
        }
    }

    /** Asynchronously refreshes the tokens contained in this instance. */
    private void refreshAsync(final boolean isAboutToExpire) {
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
        synchronized (locker) {
            if (e == null) {
                this.accessToken = Optional.of(Validation.success(new AccessToken(
                        tokens.getAccessToken(), tokens.getExpiresIn(), System.currentTimeMillis())));
                Log.debug("[oauth] Refreshed OAuth access token");
            } else {
                this.accessToken = Optional.of(Validation.<AccessToken>error(new SphereException(e)));
                Log.error("[oauth] Failed to refresh OAuth access token.", e);
            }
            locker.notifyAll();
        }
    }
}
