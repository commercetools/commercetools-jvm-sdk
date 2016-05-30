package io.sphere.sdk.retry;

import java.util.function.Function;
import java.util.function.Predicate;

public interface RetryRule {
    boolean test(RetryContext retryContext);

    RetryAction apply(RetryContext retryContext);

    static RetryRule of(final Predicate<RetryContext> matches, final Function<RetryContext, RetryAction> function) {
        return new RetryRuleImpl() {
            @Override
            public RetryAction apply(final RetryContext retryContext) {
                return function.apply(retryContext);
            }

            @Override
            public boolean test(final RetryContext retryContext) {
                return matches.test(retryContext);
            }
        };
    }
}
