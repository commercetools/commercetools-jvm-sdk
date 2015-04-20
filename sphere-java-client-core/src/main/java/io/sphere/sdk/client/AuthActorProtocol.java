package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

final class AuthActorProtocol {
    static class TokenDeliveryFailedMessage extends Base {
        final Throwable cause;

        public TokenDeliveryFailedMessage(final Throwable cause) {
            this.cause = cause;
        }
    }

    static class TokenDeliveredMessage extends Base {
        final Tokens tokens;

        public TokenDeliveredMessage(final Tokens tokens) {
            this.tokens = tokens;
        }
    }

    static class SubscribeMessage extends Base {
        final Actor subscriber;

        public SubscribeMessage(final Actor subscriber) {
            this.subscriber = subscriber;
        }
    }

    static class FailedTokenFetchMessage extends Base {
        final Throwable cause;
        final long attempt;

        public FailedTokenFetchMessage(final Throwable cause, final long attempt) {
            this.cause = cause;
            this.attempt = attempt;
        }
    }

    static class SuccessfulTokenFetchMessage extends Base {
        final Tokens tokens;

        public SuccessfulTokenFetchMessage(final Tokens tokens) {
            this.tokens = tokens;
        }
    }

    static class FetchTokenFromSphereMessage extends Base {
        public final long attempt;

        public FetchTokenFromSphereMessage() {
            this(0);
        }

        public FetchTokenFromSphereMessage(final long attempt) {
            this.attempt = attempt;
        }
    }
}
