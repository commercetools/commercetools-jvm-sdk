package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;

abstract class RetryRuleImpl extends Base implements RetryRule {
    @Override
    public abstract boolean test(final RetryContext retryContext);

    @Override
    public abstract RetryStrategy apply(final RetryContext retryContext);
}
