package io.sphere.sdk.retry;

public interface RetryRule {
    boolean test(RetryContext retryContext);

    RetryOperation selectRetryOperation(RetryContext retryContext);
}
