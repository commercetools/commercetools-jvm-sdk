package io.sphere.sdk.client;

import java.util.Optional;

import io.sphere.sdk.concurrent.JavaConcurrentUtils;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.SphereInternalLogger;
import io.sphere.sdk.utils.UrlUtils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import static io.sphere.sdk.utils.SphereInternalLogger.*;

/** Holds OAuth access tokens for accessing protected Sphere HTTP API endpoints.
 *  Refreshes the access token as needed automatically. */
final class SphereAccessTokenSupplierImpl extends Base implements SphereAccessTokenSupplier {
    /** Amount of time indicating that an OAuth token is about to expire and should be refreshed.
     *  See {@link SphereAccessTokenSupplier}. */
    private static final long TOKEN_ABOUT_TO_EXPIRE_MS = 60*1000L;  // 1 minute //TODO use Typesafe Config
    public static final SphereInternalLogger AUTH_LOGGER = getLogger("oauth");
    private final String tokenEndpoint;
    private final String projectKey;
    private final String clientId;
    private final String clientSecret;
    private final OAuthClient oauthClient;
    private boolean isClosed = false;

    private final Object accessTokenLock = new Object();

    private Optional<ValidationE<AccessToken>> accessTokenResult = Optional.empty();

    /** Allows at most one refresh operation running in the background. */
    private final ThreadPoolExecutor refreshExecutor = JavaConcurrentUtils.singleTaskExecutor("Sphere-ClientCredentials-refresh");
    private final Timer refreshTimer = new Timer("Sphere-ClientCredentials-refreshTimer", true);

    public static String tokenEndpoint(String authEndpoint) {
        return UrlUtils.combine(authEndpoint, "/oauth/token");
    }

    public static SphereAccessTokenSupplier createAndBeginRefreshInBackground(final SphereAuthConfig config, OAuthClient oauthClient) {
        final String tokenEndpoint = tokenEndpoint(config.getAuthUrl());
        final SphereAccessTokenSupplierImpl credentials = new SphereAccessTokenSupplierImpl(
                oauthClient, tokenEndpoint, config.getProjectKey(), config.getClientId(), config.getClientSecret());
        credentials.beginRefresh();
        return credentials;
    }

    private SphereAccessTokenSupplierImpl(OAuthClient oauthClient, String tokenEndpoint, String projectKey, String clientId, String clientSecret) {
        this.oauthClient  = oauthClient;
        this.tokenEndpoint = tokenEndpoint;
        this.projectKey = projectKey;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public String get() {
        synchronized (accessTokenLock) {
            Optional<ValidationE<AccessToken>> tokenResult = waitForToken();
            if (!tokenResult.isPresent()) {
                // Shouldn't happen as the timer should refresh the token soon enough.
                AUTH_LOGGER.warn(() -> "Access token expired, blocking until a new one is available.");
                beginRefresh();
                tokenResult = waitForToken();
                if (!tokenResult.isPresent()) {
                    throw new SphereClientException("Access token expired immediately after refresh.");
                }
            }
            if (tokenResult.get().isError()) {
                beginRefresh();   // retry on error (essential to recover from backend errors)
                throw tokenResult.get().getError();
            }
            return tokenResult.get().getValue().getAccessToken();
        }
    }

    /** If there is an access token present, checks whether it's not expired yet and returns it.
     *  If the token already expired, clears the token.
     *  Called only from {@link #get()} so {@link #accessTokenLock} is already acquired. */
    private Optional<ValidationE<AccessToken>> waitForToken() {
        while (!accessTokenResult.isPresent()) {
            try {
                accessTokenLock.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (accessTokenResult.get().isError()) {
            return accessTokenResult;
        }
        Optional<Long> remainingMs = accessTokenResult.get().getValue().getRemaniningMs();
        if (remainingMs.isPresent()) {
            // Have some tolerance here so that we don't send tokens with 100ms validity to the server,
            // expiring "on the way".
            if (remainingMs.get() <= 2000) {
                // if the token expired, clear it
                accessTokenResult = Optional.empty();
            }
        }
        return accessTokenResult;
    }

    /** Asynchronously refreshes the tokens contained in this instance. */
    private void beginRefresh() {
        if (!isClosed) {
            try {
                refreshExecutor.execute(() -> {
                    AUTH_LOGGER.debug(() -> "Refreshing access token.");
                    Tokens tokens = null;
                    try {
                        if (!isClosed) {
                            final CompletableFuture<Tokens> tokensForClientFuture = oauthClient.getTokensForClient(tokenEndpoint, clientId, clientSecret, "manage_project:" + projectKey);
                            tokens = tokensForClientFuture.get();
                        }
                    } catch (Exception e) {
                        update(null, e);
                        return;
                    }
                    update(tokens, null);
                });
            } catch (RejectedExecutionException e) {
                // another refresh is already in progress, ignore this one
            }
        }
    }

    private void update(Tokens tokens, Exception e) {
        if (!isClosed) {
            synchronized (accessTokenLock) {
                try {
                    if (e == null) {
                        AccessToken newToken = new AccessToken(tokens.getAccessToken(), tokens.getExpiresIn(), System.currentTimeMillis());
                        this.accessTokenResult = Optional.of(new ValidationE<>(newToken, null));
                        AUTH_LOGGER.debug(() -> "Refreshed access token.");
                        scheduleNextRefresh(tokens);
                    } else {
                        this.accessTokenResult = Optional.of(ValidationE.<AccessToken>error(new SphereClientException(e)));
                        final boolean isShuttingDown = e instanceof InterruptedException;
                        if (!isShuttingDown) {
                            AUTH_LOGGER.error(() -> "Failed to refresh access token.", e);
                        }
                    }
                } finally {
                    accessTokenLock.notifyAll();
                }
            }
        }
    }

    private void scheduleNextRefresh(Tokens tokens) {
        if (!isClosed) {
            if (!tokens.getExpiresIn().isPresent()) {
                AUTH_LOGGER.warn(() -> "Authorization server did not provide expires_in for the access token.");
                return;
            }
            if (tokens.getExpiresIn().get() * 1000 < TOKEN_ABOUT_TO_EXPIRE_MS) {
                AUTH_LOGGER.warn(() -> "Authorization server returned an access token with a very short validity of " +
                        tokens.getExpiresIn().get() + "s!");
                return;
            }
            long refreshTimeout = tokens.getExpiresIn().get() * 1000 - TOKEN_ABOUT_TO_EXPIRE_MS;
            AUTH_LOGGER.debug(() -> "Scheduling next token refresh " + refreshTimeout / 1000 + "s from now.");
            refreshTimer.schedule(new TimerTask() {
                public void run() {
                    beginRefresh();
                }
            }, refreshTimeout);
        }
    }

    /** Shuts down internal thread pools. */
    public void close() {
        refreshExecutor.shutdownNow();
        refreshTimer.cancel();
        isClosed = true;
    }
}
