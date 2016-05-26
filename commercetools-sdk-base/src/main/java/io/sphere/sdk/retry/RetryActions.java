package io.sphere.sdk.retry;

final class RetryActions {

    private RetryActions() {
    }

    static void validateMaxAttempts(final long maxAttempts) {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("Max attempts must be greater than 0.");
        }
    }
}
