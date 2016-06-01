package io.sphere.sdk.client.retry;

import io.sphere.sdk.client.RetrySphereClientDecorator;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.retry.RetryAction;
import io.sphere.sdk.retry.RetryPredicate;
import io.sphere.sdk.retry.RetryRule;

import java.time.Duration;
import java.util.List;

import static io.sphere.sdk.http.HttpStatusCode.BAD_GATEWAY_502;
import static io.sphere.sdk.http.HttpStatusCode.GATEWAY_TIMEOUT_504;
import static io.sphere.sdk.http.HttpStatusCode.SERVICE_UNAVAILABLE_503;
import static java.util.Collections.singletonList;

public class RetryBadGatewayExample {
    public static SphereClient ofRetry(final SphereClient delegate) {
        final int maxAttempts = 5;
        final List<RetryRule> retryRules = singletonList(RetryRule.of(
                RetryPredicate.ofMatchingStatusCodes(BAD_GATEWAY_502, SERVICE_UNAVAILABLE_503, GATEWAY_TIMEOUT_504),
                RetryAction.ofScheduledRetry(maxAttempts, context -> Duration.ofSeconds(context.getAttempt() * 2)))
        );
        return RetrySphereClientDecorator.of(delegate, retryRules);
    }
}
