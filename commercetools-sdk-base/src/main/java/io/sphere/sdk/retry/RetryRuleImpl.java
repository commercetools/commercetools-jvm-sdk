package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;

abstract class RetryRuleImpl extends Base implements RetryRule {
    @Override
    public boolean isApplicable(final RetryContext retryContext) {
        return true;
    }

    @Override
    public abstract RetryAction apply(final RetryContext retryContext);
}
