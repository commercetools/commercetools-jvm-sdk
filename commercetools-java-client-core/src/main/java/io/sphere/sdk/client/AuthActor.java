package io.sphere.sdk.client;

import io.sphere.sdk.client.AuthActorProtocol.FailedTokenFetchMessage;
import io.sphere.sdk.client.AuthActorProtocol.FetchTokenFromSphereMessage;
import io.sphere.sdk.client.AuthActorProtocol.SuccessfulTokenFetchMessage;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.sphere.sdk.client.SphereAuth.AUTH_LOGGER;
import static io.sphere.sdk.utils.CompletableFutureUtils.onFailure;
import static io.sphere.sdk.utils.CompletableFutureUtils.onSuccess;

/**
 * Actor which takes care that only one token needs to be fetched for many requests.
 */
final class AuthActor extends Actor {
    private final TokensSupplier internalTokensSupplier;
    private final Function<Supplier<CompletionStage<Tokens>>, CompletionStage<Tokens>> supervisedTokenSupplier;
    private final Consumer<Tokens> requestUpdateTokens;
    private final Consumer<Throwable> requestUpdateFailedStatus;
    private boolean isWaitingForToken = false;

    public AuthActor(final TokensSupplier internalTokensSupplier,
                     final Function<Supplier<CompletionStage<Tokens>>, CompletionStage<Tokens>> supervisedTokenSupplier,
                     final Consumer<Tokens> requestUpdateTokens,
                     final Consumer<Throwable> requestUpdateFailedStatus) {
        this.internalTokensSupplier = internalTokensSupplier;
        this.supervisedTokenSupplier = supervisedTokenSupplier;
        this.requestUpdateTokens = requestUpdateTokens;
        this.requestUpdateFailedStatus = requestUpdateFailedStatus;
    }

    @Override
    protected void receive(final Object message) {
        receiveBuilder(message)
                .when(FetchTokenFromSphereMessage.class, this::process)
                .when(SuccessfulTokenFetchMessage.class, this::process)
                .when(FailedTokenFetchMessage.class, this::process);
    }

    private void process(final FetchTokenFromSphereMessage m) {
        if (!isWaitingForToken) {
            isWaitingForToken = true;
            //for users it is fail fast but in the background it will be attempted again
            final CompletionStage<Tokens> future = m.attempt > 0
                    ? supervisedTokenSupplier.apply(() -> internalTokensSupplier.get())
                    : internalTokensSupplier.get();
            onSuccess(future, tokens -> tell(new SuccessfulTokenFetchMessage(tokens)));
            onFailure(future, e -> {
                requestUpdateFailedStatus.accept(e);
                tell(new FailedTokenFetchMessage(e));
                tell(new FetchTokenFromSphereMessage(1));
            });
        }
    }

    private void process(final SuccessfulTokenFetchMessage m) {
        isWaitingForToken = false;
        requestUpdateTokens.accept(m.tokens);
        scheduleNextTokenFetchFromSphere(m.tokens);
    }

    private void process(final FailedTokenFetchMessage m) {
        isWaitingForToken = false;
        AUTH_LOGGER.error(() -> "Can't fetch tokens.", m.cause);
        requestUpdateFailedStatus.accept(m.cause);
    }

    private void scheduleNextTokenFetchFromSphere(final Tokens tokens) {
        final Long delayInSecondsToFetchNewToken = Optional.ofNullable(tokens.getExpiresIn())
                .map(ttlInSeconds -> selectNextRetryTime(ttlInSeconds))
                .orElse(60L);
        schedule(new FetchTokenFromSphereMessage(), delayInSecondsToFetchNewToken, TimeUnit.SECONDS);
    }

    static Long selectNextRetryTime(final Long ttlInSeconds) {
        final long aDay = 60 * 60 * 24L;
        final long minimum = Math.min(ttlInSeconds / 2, aDay);
        final long aSecond = 1L;
        return Math.max(minimum, aSecond);
    }

    @Override
    protected void closeThisActor() {
        closeQuietly(internalTokensSupplier);
    }
}
