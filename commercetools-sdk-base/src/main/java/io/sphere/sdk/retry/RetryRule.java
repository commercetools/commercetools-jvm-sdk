package io.sphere.sdk.retry;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A rule for dealing with errors to retry executions or even shutdown services.
 *
 * <p>The method {@link #test(RetryContext)} checks of a rule applies and {@link #apply(RetryContext)} executes the rule.</p>
 *
 * <p>Example which retries with a delay on gateway timeouts:</p>
 * {@include.example io.sphere.sdk.client.retry.RetryBadGatewayExample}
 */
public interface RetryRule {
    /**
     * Tests if this rule can be applied to the context.
     * @param retryContext the context to check if this rule can be applied
     * @return true if the rule can be applied, otherwise false
     */
    boolean test(RetryContext retryContext);

    /**
     * Applies the retry rule.
     * Should not be called if {@link #test(RetryContext)} would yield false for {@code retryContext}.
     *
     * @param retryContext
     * @return
     */
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
