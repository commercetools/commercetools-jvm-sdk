package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;

public abstract class RetryRuleBase extends Base implements RetryRule {
    @Override
    public boolean test(final RetryRuleContext retryRuleContext) {
        return true;
    }

    @Override
    public abstract RetryOperation selectRetryOperation(final RetryRuleContext retryRuleContext);
}
