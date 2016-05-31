package io.sphere.sdk.retry;

import io.sphere.sdk.client.SphereServiceException;

import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Predicate if a retryContext can be handled. The static factory methods provide predicates for typical use cases.
 *
 */
@FunctionalInterface
public interface RetryPredicate extends Predicate<RetryContext> {
    @Override
    boolean test(RetryContext retryContext);

    /**
     * Creates a predicate which will be true if the latest error is a subclass of {@code errorClass}.
     *
     * @param errorClass error class to match
     * @return predicate
     */
    static RetryPredicate ofMatchingErrors(final Class<? extends Throwable> errorClass) {
        return retryContext -> errorClass.isAssignableFrom(retryContext.getLatestError().getClass());
    }

    /**
     * Creates a predicate that yields always true.
     * @return predicate
     */
    static RetryPredicate ofAlwaysTrue() {
        return retryContext -> true;
    }

    /**
     * Creates a predicate which matches specific status codes for {@link SphereServiceException}s.
     *
     * {@include.example io.sphere.sdk.client.retry.RetryBadGatewayExample}
     *
     * @param first the mandatory status code which might match
     * @param more varargs parameter for more status codes to match
     * @return predicate
     */
    static RetryPredicate ofMatchingStatusCodes(final int first, final int ... more) {
        final Predicate<Integer> predicate = statusCode -> IntStream.concat(IntStream.of(first), IntStream.of(more)).anyMatch(i -> i == statusCode);
        return ofMatchingStatusCodes(predicate);
    }

    /**
     * Creates a predicate which matches another predicate for status codes for {@link SphereServiceException}s.
     *
     * @param predicate predicate which tests if a status code should match the {@link RetryPredicate}
     * @return predicate
     */
    static RetryPredicate ofMatchingStatusCodes(final Predicate<Integer> predicate) {
        return retryContext -> retryContext.getLatestError() instanceof SphereServiceException
                && predicate.test(((SphereServiceException) retryContext.getLatestError()).getStatusCode());
    }
}
