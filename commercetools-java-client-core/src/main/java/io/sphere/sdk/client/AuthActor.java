package io.sphere.sdk.client;

import io.sphere.sdk.client.AuthActorProtocol.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.sphere.sdk.client.SphereAuth.AUTH_LOGGER;
import static io.sphere.sdk.utils.CompletableFutureUtils.onFailure;
import static io.sphere.sdk.utils.CompletableFutureUtils.onSuccess;

/**
 * Actor which takes care that only one token needs to be fetched for many requests.
 */
final class AuthActor extends Actor {
    private static final int DEFAULT_WAIT_TIME_UNTIL_RETRY_MILLISECONDS = 100;
    private final TokensSupplier internalTokensSupplier;
    private final Function<Supplier<CompletionStage<Tokens>>, CompletionStage<Tokens>> supervisedTokenSupplier;
    private Optional<Tokens> tokensCache = Optional.empty();
    private boolean isWaitingForToken = false;
    private final List<Actor> subscribers = new LinkedList<>();

    public AuthActor(final TokensSupplier internalTokensSupplier, final Function<Supplier<CompletionStage<Tokens>>, CompletionStage<Tokens>> supervisedTokenSupplier) {
        this.internalTokensSupplier = internalTokensSupplier;
        this.supervisedTokenSupplier = supervisedTokenSupplier;
    }

    @Override
    protected void receive(final Object message) {
        receiveBuilder(message)
                .when(SubscribeMessage.class, this::process)
                .when(FetchTokenFromSphereMessage.class, this::process)
                .when(SuccessfulTokenFetchMessage.class, this::process)
                .when(FailedTokenFetchMessage.class, this::process);
    }

    private void process(final SubscribeMessage m) {
        subscribers.add(m.subscriber);
        if (tokensCache.isPresent()) {
            tellSubscriberNewTokens(tokensCache.get(), m.subscriber);
        } else if (!isWaitingForToken) {
            tell(new FetchTokenFromSphereMessage());
        } else {
            //fetching token is in progress
        }
    }

    private void process(final FetchTokenFromSphereMessage m) {
        if (!isWaitingForToken) {
            isWaitingForToken = true;
            final CompletionStage<Tokens> future = supervisedTokenSupplier.apply(() -> internalTokensSupplier.get());
            onSuccess(future, tokens -> tell(new SuccessfulTokenFetchMessage(tokens)));
            onFailure(future, e -> tell(new FailedTokenFetchMessage(e)));
        }
    }

    private void process(final SuccessfulTokenFetchMessage m) {
        isWaitingForToken = false;
        tokensCache = Optional.of(m.tokens);
        subscribers.forEach(subscriber -> tellSubscriberNewTokens(m.tokens, subscriber));
        scheduleNextTokenFetchFromSphere(m.tokens);
    }

    private void tellSubscriberNewTokens(final Tokens tokens, final Actor subscriber) {
        subscriber.tell(new TokenDeliveredMessage(tokens));
    }

    private void process(final FailedTokenFetchMessage m) {
        isWaitingForToken = false;
        final boolean failReasonIsInvalidCredentials = m.cause.getCause() != null && m.cause.getCause() instanceof InvalidClientCredentialsException;
        if (failReasonIsInvalidCredentials) {
            AUTH_LOGGER.error(() -> "Can't fetch tokens due to invalid credentials.", m.cause);
            subscribers.forEach(subscriber -> subscriber.tell(new TokenDeliveryFailedMessage(m.cause)));
        } else {
            AUTH_LOGGER.error(() -> "Can't fetch tokens.", m.cause);
            subscribers.forEach(subscriber -> subscriber.tell(new TokenDeliveryFailedMessage(m.cause)));
        }
    }

    private void scheduleNextTokenFetchFromSphere(final Tokens tokens) {
        final Long delayInSecondsToFetchNewToken = Optional.ofNullable(tokens.getExpiresIn()).map(ttlInSeconds -> ttlInSeconds - 60 * 60).orElse(60 * 30L);
        schedule(new FetchTokenFromSphereMessage(), delayInSecondsToFetchNewToken, TimeUnit.SECONDS);
    }

    @Override
    protected void closeThisActor() {
        closeQuietly(internalTokensSupplier);
    }
}
