package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpException;
import io.sphere.sdk.retry.*;
import io.sphere.sdk.utils.CompletableFutureUtils;

import java.net.UnknownHostException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Arrays.asList;

/**
 *  Holds OAuth access tokenCache for accessing protected Sphere HTTP API endpoints.
 *  Refreshes the access token as needed automatically.
 */
final class AutoRefreshSphereAccessTokenSupplierImpl extends AutoCloseableService implements RefreshableSphereAccessTokenSupplier {
    private final TokensSupplier tokensSupplier;//managed by the authActor
    private volatile CompletableFuture<String> currentAccessTokenFuture = new CompletableFuture<>();
    private volatile Optional<Tokens> currentTokensOption = Optional.empty();
    private final Actor authActor;
    private final List<RetryRule> retryRules = createRules();
    private final AsyncRetrySupervisor supervisor = AsyncRetrySupervisor.of(retryRules);

    private AutoRefreshSphereAccessTokenSupplierImpl(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        tokensSupplier = TokensSupplierImpl.of(config, httpClient, closeHttpClient);
        authActor = new AuthActor(tokensSupplier, this::supervisedTokenSupplier, this::requestUpdateTokens, this::requestUpdateFailedStatus);
        authActor.tell(new AuthActorProtocol.FetchTokenFromSphereMessage());
    }

    private List<RetryRule> createRules() {
        final Predicate<RetryContext> isFatal = r -> {
            final Throwable latestError = r.getLatestError();
            final boolean unknownHost = latestError instanceof HttpException && latestError != null && latestError instanceof UnknownHostException;
            final boolean unauthorized = latestError instanceof UnauthorizedException;
            return unknownHost || unauthorized;
        };
        final Predicate<RetryContext> isSuspended = r -> {
            final Throwable latestError = r.getLatestError();
            return latestError instanceof SuspendedProjectException;
        };
        final RetryRule fatalRetryRule = RetryRule.of(isFatal, RetryAction.ofShutdownServiceAndSendLatestException());
        final RetryRule retryScheduledRetryRule = RetryRule.of(RetryPredicate.ofAlwaysTrue(), RetryAction.ofScheduledRetry(2, retryContext -> Duration.ofMillis(retryContext.getAttempt() * retryContext.getAttempt() * 50)));
        final RetryRule suspendedRetryRule = RetryRule.of(isSuspended, RetryAction.ofExponentialBackoff(100, 1000, 60000));
        return asList(suspendedRetryRule, fatalRetryRule, retryScheduledRetryRule);
    }

    private CompletionStage<Tokens> supervisedTokenSupplier(final Supplier<CompletionStage<Tokens>> supplier) {
        return supervisor.supervise(this, nullParameter -> supplier.get(), null);
    }

    @Override
    public CompletionStage<String> get() {
        rejectExcutionIfClosed("Token supplier is already closed.");
        return currentAccessTokenFuture;
    }

    @Override
    public CompletionStage<String> getNewToken() {
        authActor.tell(new AuthActorProtocol.FetchTokenFromSphereMessage());
        /*
        Two times a token will be fetched, once to update the cached token
        and the other time to get a new token to the caller.
        This way it minimizes the possibility of race conditions but is less effective.
        But this should be okay since it should be a rare case that his happens.
         */
        return tokensSupplier.get().thenApplyAsync(tokens -> tokens.getAccessToken());
    }

    @Override
    protected void internalClose() {
        closeQuietly(supervisor);
        closeQuietly(authActor);
    }

    public static SphereAccessTokenSupplier createAndBeginRefreshInBackground(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        return new AutoRefreshSphereAccessTokenSupplierImpl(config, httpClient, closeHttpClient);
    }

    private void requestUpdateTokens(final Tokens tokens) {
        if (!currentTokensOption.isPresent() || currentTokenIsOlder(tokens)) {
            currentTokensOption = Optional.ofNullable(tokens);
            final String accessToken = tokens.getAccessToken();
            if (currentAccessTokenFuture.isDone()) {
                currentAccessTokenFuture = CompletableFutureUtils.successful(accessToken);
            } else {
                currentAccessTokenFuture.complete(accessToken);
            }
        }
    }

    private void requestUpdateFailedStatus(final Throwable error) {
        if (!currentTokensOption.isPresent()) {
            currentAccessTokenFuture.completeExceptionally(error);
        } else if (lastTokenIsStillValid()) {
            //keep the old token
        } else {
            currentTokensOption = Optional.empty();
            currentAccessTokenFuture = CompletableFutureUtils.failed(error);
        }
        authActor.tell(new AuthActorProtocol.FetchTokenFromSphereMessage());//
    }

    /**
     * if the last token has no expire time it true
     * @return
     */
    private boolean lastTokenIsStillValid() {
        if (currentTokensOption.isPresent()) {
            final Tokens oldTokens = currentTokensOption.get();
            return Optional.ofNullable(oldTokens.getExpiresZonedDateTime()).map(expireTime -> expireTime.isAfter(ZonedDateTime.now())).orElse(true);
        } else {
            return false;
        }
    }

    private boolean currentTokenIsOlder(final Tokens newTokens) {
        return (currentTokensOption.isPresent() && oldExpiringZonedDateTime().isBefore(newExpiringZonedDateTime(newTokens)));
    }

    private ZonedDateTime newExpiringZonedDateTime(final Tokens newTokens) {
        return Optional.ofNullable(newTokens.getExpiresZonedDateTime()).orElseGet(() -> ZonedDateTime.now().plusSeconds(30 * 60));
    }

    private ZonedDateTime oldExpiringZonedDateTime() {
        return Optional.ofNullable(currentTokensOption.get().getExpiresZonedDateTime()).orElseGet(() -> ZonedDateTime.now());
    }
}
