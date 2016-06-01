package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

final class AuthActorProtocol {
    static class FailedTokenFetchMessage extends Base {
        final Throwable cause;

        public FailedTokenFetchMessage(final Throwable cause) {
            this.cause = cause;
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
