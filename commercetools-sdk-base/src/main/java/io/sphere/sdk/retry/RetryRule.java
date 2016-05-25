package io.sphere.sdk.retry;

import java.util.function.Function;
import java.util.function.Predicate;

public interface RetryRule {
    boolean test(RetryContext retryContext);

    RetryOperation selectRetryOperation(RetryContext retryContext);

    static RetryRule ofOperation(final RetryOperation op) {
        return ofAllExceptions(c -> op);
    }

    static RetryRule ofAllExceptions(final Function<RetryContext, RetryOperation> function) {
        return of(c -> true, function);
    }

    static RetryRule of(final Predicate<RetryContext> matches, final Function<RetryContext, RetryOperation> function) {
        return new RetryRuleBase() {
            @Override
            public RetryOperation selectRetryOperation(final RetryContext retryContext) {
                return function.apply(retryContext);
            }

            @Override
            public boolean test(final RetryContext retryContext) {
                return matches.test(retryContext);
            }
        };
    }
}
