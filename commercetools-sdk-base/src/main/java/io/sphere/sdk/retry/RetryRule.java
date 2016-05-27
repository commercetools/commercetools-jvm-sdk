package io.sphere.sdk.retry;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface RetryRule {
    boolean isApplicable(RetryContext retryContext);

    RetryAction apply(RetryContext retryContext);

    static RetryRule ofOperation(final RetryAction op) {
        return ofAllExceptions(c -> op);
    }

    static RetryRule ofAllExceptions(final Function<RetryContext, RetryAction> function) {
        return of(c -> true, function);
    }

    static RetryRule of(final Predicate<RetryContext> matches, final Function<RetryContext, RetryAction> function) {
        return new RetryRuleImpl() {
            @Override
            public RetryAction apply(final RetryContext retryContext) {
                return function.apply(retryContext);
            }

            @Override
            public boolean isApplicable(final RetryContext retryContext) {
                return matches.test(retryContext);
            }
        };
    }

    //TODO revert extraction
    static Optional<RetryAction> findMatchingRetryAction(final List<RetryRule> retryRules, final RetryContext retryContext) {
        return retryRules.stream()
                .filter(rule -> rule.isApplicable(retryContext))
                .findFirst()
                .map(rule -> rule.apply(retryContext));
    }

    static RetryRule ofMatching(Class<? extends Throwable> errorClass, final Function<RetryContext, RetryAction> function) {
        return RetryRule.of(c -> errorClass.isAssignableFrom(c.getLatestError().getClass()), function);
    }
}
