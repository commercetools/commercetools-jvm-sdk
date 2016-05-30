package io.sphere.sdk.retry;

import io.sphere.sdk.client.SphereServiceException;

import java.util.function.Predicate;
import java.util.stream.IntStream;

@FunctionalInterface
public interface RetryPredicate extends Predicate<RetryContext> {
    @Override
    boolean test(RetryContext retryContext);

    static RetryPredicate ofMatchingErrors(final Class<? extends Throwable> errorClass) {
        return retryContext -> errorClass.isAssignableFrom(retryContext.getLatestError().getClass());
    }

    static RetryPredicate ofAlwaysTrue() {
        return retryContext -> true;
    }

    static RetryPredicate ofMatchingStatusCodes(final int first, final int... more) {
        final Predicate<Integer> predicate = statusCode -> IntStream.concat(IntStream.of(first), IntStream.of(more)).anyMatch(i -> i == statusCode);
        return ofMatchingStatusCodes(predicate);
    }

    static RetryPredicate ofMatchingStatusCodes(final Predicate<Integer> predicate) {
        return retryContext -> {
            final Throwable latestError = retryContext.getLatestError();
            final Integer statusCode = ((SphereServiceException) latestError).getStatusCode();
            final boolean b = latestError instanceof SphereServiceException && predicate.test(statusCode);
            return b;
        };
    }
}
