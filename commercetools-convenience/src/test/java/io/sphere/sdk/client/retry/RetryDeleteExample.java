package io.sphere.sdk.client.retry;

import io.sphere.sdk.client.*;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.retry.RetryAction;
import io.sphere.sdk.retry.RetryContext;
import io.sphere.sdk.retry.RetryStrategy;
import io.sphere.sdk.retry.RetryRule;

import java.util.List;
import java.util.function.Predicate;

import static java.util.Collections.singletonList;

public class RetryDeleteExample {
    public static SphereClient ofRetry(final SphereClient delegate) {
        final List<RetryRule> retryRules = singletonList(RetryRule.of(isDeleteAndNewVersionIsKnown(), retryWithNewVersion()));
        return RetrySphereClientDecorator.of(delegate, retryRules);
    }

    private static Predicate<RetryContext> isDeleteAndNewVersionIsKnown() {
        return retryContext -> retryContext.getLatestError() instanceof ConcurrentModificationException
                && ((ConcurrentModificationException) retryContext.getLatestError()).getCurrentVersion() != null
                && retryContext.getLatestParameter() instanceof SphereRequest
                && ((SphereRequest) retryContext.getLatestParameter()).httpRequestIntent().getHttpMethod() == HttpMethod.DELETE;
    }

    @SuppressWarnings("unchecked")
    private static RetryAction retryWithNewVersion() {
        return (RetryContext c) -> {
            final SphereRequest sphereRequest = (SphereRequest) c.getLatestParameter();
            final Object newParameter = new SphereRequestDecorator(sphereRequest) {
                @Override
                public HttpRequestIntent httpRequestIntent() {
                    final HttpRequestIntent original = super.httpRequestIntent();
                    final Long currentVersion = ((ConcurrentModificationException) c.getLatestError()).getCurrentVersion();
                    final String path = original.getPath().replaceAll("\\bversion=\\d+", "version=" + currentVersion);
                    return original.withPath(path);
                }
            };
            return RetryStrategy.retryImmediately(newParameter);
        };
    }
}
