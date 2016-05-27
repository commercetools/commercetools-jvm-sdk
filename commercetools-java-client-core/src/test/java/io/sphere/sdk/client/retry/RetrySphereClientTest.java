package io.sphere.sdk.client.retry;

import io.sphere.sdk.client.DummySphereRequest;
import io.sphere.sdk.client.SphereAccessTokenSupplier;
import io.sphere.sdk.client.SphereApiConfig;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static io.sphere.sdk.client.SphereClientUtils.blockingWait;
import static io.sphere.sdk.http.HttpStatusCode.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class RetrySphereClientTest {

    public static final String RESULT = DummySphereRequest.DEFAULT_RESPONSE_OBJECT;

    @Test
    public void retryOnGatewayProblems() {
        final SphereClient client = SphereClient.of(SphereApiConfig.of("projectKey"), getHttpClient(), SphereAccessTokenSupplier.ofConstantToken("accessToken"));
        final SphereClient retryClient = RetryBadGatewayExample.ofRetry(client);
        final String result = blockingWait(retryClient.execute(DummySphereRequest.of()), 200, TimeUnit.MILLISECONDS);
        assertThat(result).isEqualTo(RESULT);
    }

    private HttpClient getHttpClient() {
        return new HttpClient() {
            private final AtomicInteger counter = new AtomicInteger(0);

            @Override
            public CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
                final int counterValue = counter.getAndIncrement();
                final List<Integer> statusCodes = asList(BAD_GATEWAY_502, SERVICE_UNAVAILABLE_503, GATEWAY_TIMEOUT_504);
                final int statusCode = counterValue >= statusCodes.size() ? OK_200 : statusCodes.get(counterValue);
                return CompletableFuture.completedFuture(HttpResponse.of(statusCode, RESULT));
            }

            @Override
            public void close() {

            }
        };
    }
}