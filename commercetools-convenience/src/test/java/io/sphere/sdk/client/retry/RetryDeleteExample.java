package io.sphere.sdk.client.retry;

import io.sphere.sdk.client.*;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.retry.RetryAction;
import io.sphere.sdk.retry.RetryContext;
import io.sphere.sdk.retry.RetryResult;
import io.sphere.sdk.retry.RetryRule;

import java.util.List;

import static java.util.Collections.singletonList;

public class RetryDeleteExample {
    public static SphereClient ofRetry(final SphereClient delegate) {
        final List<RetryRule> retryRules = singletonList(RetryRule.of(
                retryContext -> {
                    boolean matches = false;
                    final Throwable error = retryContext.getLatestError();
                    if (error instanceof ConcurrentModificationException && ((ConcurrentModificationException) error).getCurrentVersion() != null) {
                        final Object latestParameter = retryContext.getLatestParameter();
                        if (latestParameter instanceof SphereRequest) {
                            final SphereRequest sphereRequest = (SphereRequest) latestParameter;
                            final HttpRequestIntent httpRequestIntent = sphereRequest.httpRequestIntent();
                            if (httpRequestIntent.getHttpMethod() == HttpMethod.DELETE) {
                                matches = true;
                            }
                        }
                    }
                    return matches;
                },
                c -> getRetryAction()));
        return RetrySphereClient.of(delegate, retryRules);
    }

    @SuppressWarnings("unchecked")
    private static RetryAction getRetryAction() {
        return (RetryContext c) -> {
            final Object latestParameter = c.getLatestParameter();
            final SphereRequest sphereRequest = (SphereRequest) latestParameter;
            final Object newParameter = new SphereRequestDecorator(sphereRequest) {
                @Override
                public HttpRequestIntent httpRequestIntent() {
                    final HttpRequestIntent original = super.httpRequestIntent();
                    final Long currentVersion = ((ConcurrentModificationException) c.getLatestError()).getCurrentVersion();
                    final String path = original.getPath().replaceAll("\\bversion=\\d+", "version=" + currentVersion);
                    return original.withPath(path);
                }
            };
            return RetryResult.retryImmediately(newParameter);
        };
    }
}
