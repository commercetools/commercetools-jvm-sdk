package io.sphere.sdk.retry;

import io.sphere.sdk.client.BadRequestException;
import io.sphere.sdk.client.GatewayTimeoutException;
import org.junit.Test;

import static io.sphere.sdk.http.HttpStatusCode.GATEWAY_TIMEOUT_504;
import static io.sphere.sdk.http.HttpStatusCode.SERVICE_UNAVAILABLE_503;
import static org.assertj.core.api.Assertions.assertThat;

public class RetryPredicateTest {
    @Test
    public void statusCodesVarargs() {
        final RetryPredicate predicate = RetryPredicate.ofMatchingStatusCodes(SERVICE_UNAVAILABLE_503, GATEWAY_TIMEOUT_504);
        assertThat(predicate.test(getRetryContext(new GatewayTimeoutException()))).isTrue();
        assertThat(predicate.test(getRetryContext(new BadRequestException("")))).isFalse();
    }

    @Test
    public void statusCodesPredicate() {
        final RetryPredicate predicate = RetryPredicate.ofMatchingStatusCodes(SERVICE_UNAVAILABLE_503, GATEWAY_TIMEOUT_504);
        assertThat(predicate.test(getRetryContext(new GatewayTimeoutException()))).isTrue();
        assertThat(predicate.test(getRetryContext(new BadRequestException("")))).isFalse();
    }

    private RetryContextImpl<Object, Object> getRetryContext(final Throwable latestError) {
        return new RetryContextImpl<>(null, null, null, null, latestError, null, null, null, null, null);
    }
}