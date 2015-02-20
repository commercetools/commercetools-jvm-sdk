package io.sphere.sdk.client;

import io.sphere.sdk.client.AuthActorProtocol.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static io.sphere.sdk.client.SphereAuth.*;
import static io.sphere.sdk.client.CompletableFutureUtils.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Actor which takes care that only one token needs to be fetched for many requests.
 */
final class AuthActor extends Actor {
    private static final int DEFAULT_WAIT_TIME_UNTIL_RETRY_MILLISECONDS = 100;
    private final TokensSupplier internalTokensSupplier;
    private Optional<Tokens> tokensCache = Optional.empty();
    private boolean isWaitingForToken = false;
    private final List<Actor> subscribers = new LinkedList<>();

    public AuthActor(final TokensSupplier internalTokensSupplier) {
        this.internalTokensSupplier = internalTokensSupplier;
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
            final CompletableFuture<Tokens> future = internalTokensSupplier.get();
            onSuccess(future, tokens -> tell(new SuccessfulTokenFetchMessage(tokens)));
            onFailure(future, e -> tell(new FailedTokenFetchMessage(e, m.attempt + 1)));
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
        AUTH_LOGGER.error(() -> "Can't fetch tokens.", m.cause);
        final long tryAgainIn = m.attempt * DEFAULT_WAIT_TIME_UNTIL_RETRY_MILLISECONDS;
        schedule(new FetchTokenFromSphereMessage(m.attempt), tryAgainIn, MILLISECONDS);
        if (m.attempt > 2) {
            subscribers.forEach(subscriber -> subscriber.tell(new TokenDeliveryFailedMessage(m.cause)));
        }
    }

    private void scheduleNextTokenFetchFromSphere(final Tokens tokens) {
        final Long delayInSecondsToFetchNewToken = tokens.getExpiresIn().map(ttlInSeconds -> ttlInSeconds - 60 * 5).orElse(60 * 30L);
        schedule(new FetchTokenFromSphereMessage(), delayInSecondsToFetchNewToken, TimeUnit.SECONDS);
    }

    @Override
    protected void closeThisActor() {
        closeQuietly(internalTokensSupplier);
    }
}
