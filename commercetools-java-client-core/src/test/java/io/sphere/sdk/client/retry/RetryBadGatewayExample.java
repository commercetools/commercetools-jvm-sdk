package io.sphere.sdk.client.retry;

import io.sphere.sdk.client.RetrySphereClient;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.retry.RetryAction;
import io.sphere.sdk.retry.RetryRule;

import java.time.Duration;
import java.util.List;

import static io.sphere.sdk.http.HttpStatusCode.BAD_GATEWAY_502;
import static io.sphere.sdk.http.HttpStatusCode.GATEWAY_TIMEOUT_504;
import static io.sphere.sdk.http.HttpStatusCode.SERVICE_UNAVAILABLE_503;
import static io.sphere.sdk.retry.RetryRule.matchingResponseCodes;
import static java.util.Collections.singletonList;

public class RetryBadGatewayExample {
    public static SphereClient ofRetry(final SphereClient delegate) {
        final int maxAttempts = 10;
        final List<RetryRule> retryRules = singletonList(RetryRule.of(
                matchingResponseCodes(BAD_GATEWAY_502, SERVICE_UNAVAILABLE_503, GATEWAY_TIMEOUT_504),
                RetryAction.scheduledRetry(maxAttempts, c -> Duration.ofMillis(100)))
        );
        return RetrySphereClient.of(delegate, retryRules);
    }
}
