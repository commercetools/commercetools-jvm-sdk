package io.sphere.sdk.retry;

import io.sphere.sdk.client.SphereServiceException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public interface RetryRule {
    boolean isApplicable(RetryContext retryContext);

    RetryAction apply(RetryContext retryContext);

    static RetryRule ofOperation(final RetryAction op) {
        return ofAllExceptions(c -> op);
    }

    static RetryRule ofAllExceptions(final Function<RetryContext, RetryAction> function) {
        return ofLambdaSyntax(c -> true, function);
    }

    static RetryRule ofLambdaSyntax(final Predicate<RetryContext> matches, final Function<RetryContext, RetryAction> function) {
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

    static RetryRule of(final Predicate<RetryContext> matches, final RetryAction retryAction) {
        return ofLambdaSyntax(matches, (RetryContext c) -> retryAction);
    }

    //TODO revert extraction
    static Optional<RetryAction> findMatchingRetryAction(final List<RetryRule> retryRules, final RetryContext retryContext) {
        return retryRules.stream()
                .filter(rule -> rule.isApplicable(retryContext))
                .findFirst()
                .map(rule -> rule.apply(retryContext));
    }

    static RetryRule ofMatching(Class<? extends Throwable> errorClass, final Function<RetryContext, RetryAction> function) {
        return RetryRule.ofLambdaSyntax(c -> errorClass.isAssignableFrom(c.getLatestError().getClass()), function);
    }

    static Predicate<RetryContext> matchingResponseCodes(final int first, final int... more) {
        return retryContext -> {
            final Throwable latestError = retryContext.getLatestError();
            final Integer statusCode = ((SphereServiceException) latestError).getStatusCode();
            final boolean b = latestError instanceof SphereServiceException && IntStream.concat(IntStream.of(first), IntStream.of(more)).anyMatch(i -> i == statusCode);
            return b;
        };
    }
}
