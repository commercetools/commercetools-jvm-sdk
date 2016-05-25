package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;

public abstract class RetryRuleBase extends Base implements RetryRule {
    @Override
    public boolean test(final RetryContext retryContext) {
        return true;
    }

    @Override
    public abstract RetryOperation selectRetryOperation(final RetryContext retryContext);
}
