package io.sphere.sdk.retry;

import java.util.function.Function;
import java.util.function.Predicate;

public interface RetryRule {
    boolean test(RetryRuleContext retryRuleContext);

    RetryOperation selectRetryOperation(RetryRuleContext retryRuleContext);

    static RetryRule ofOperation(final RetryOperation op) {
        return ofAllExceptions(c -> op);
    }

    static RetryRule ofAllExceptions(final Function<RetryRuleContext, RetryOperation> function) {
        return of(c -> true, function);
    }

    static RetryRule of(final Predicate<RetryRuleContext> matches, final Function<RetryRuleContext, RetryOperation> function) {
        return new RetryRuleBase() {
            @Override
            public RetryOperation selectRetryOperation(final RetryRuleContext retryRuleContext) {
                return function.apply(retryRuleContext);
            }

            @Override
            public boolean test(final RetryRuleContext retryRuleContext) {
                return matches.test(retryRuleContext);
            }
        };
    }
}
