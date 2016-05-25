package io.sphere.sdk.retry;

final class RetryActions {

    private RetryActions() {
    }

    static RetryContext convert(final RetryOperationContext<?> c) {
        return new RetryContextImpl<>(c.getAttempt(), c.getLatest().getError(), c.getLatest().getParameter());
    }

    static void validateMaxAttempts(final long maxAttempts) {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("Max attempts must be greater than 0.");
        }
    }
}
