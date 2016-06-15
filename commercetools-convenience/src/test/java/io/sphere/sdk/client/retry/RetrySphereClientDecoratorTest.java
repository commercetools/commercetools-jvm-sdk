package io.sphere.sdk.client.retry;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.client.SphereAccessTokenSupplier;
import io.sphere.sdk.client.SphereApiConfig;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.queries.ProjectGet;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static io.sphere.sdk.client.SphereClientUtils.blockingWait;
import static io.sphere.sdk.http.HttpStatusCode.*;
import static java.util.Arrays.asList;

public class RetrySphereClientDecoratorTest {

    @Test
    public void retryOnGatewayProblems() {
        final SphereClient client = getSphereClient(getHttpClient());
        final SphereClient retryClient = RetryBadGatewayExample.ofRetry(client);
        final Project result = blockingWait(retryClient.execute(ProjectGet.of()), 16, TimeUnit.SECONDS);
        Assertions.assertThat(result.getKey()).isEqualTo("foo");
    }

    private SphereClient getSphereClient(final HttpClient httpClient) {
        return SphereClient.of(SphereApiConfig.of("projectKey"), httpClient, SphereAccessTokenSupplier.ofConstantToken("accessToken"));
    }

    @Test
    public void retryDelete() throws InterruptedException {
        final SphereClient client = getSphereClient(getDeleteHttpClient());
        final SphereClient retryClient = RetryDeleteExample.ofRetry(client);
        final Category result = blockingWait(retryClient.execute(CategoryDeleteCommand.of(Versioned.of("some-id", 5L))), 800, TimeUnit.MILLISECONDS);//thread pool needs warm-up
        Assertions.assertThat(result.getVersion()).isEqualTo(7L);
    }

    private HttpClient getDeleteHttpClient() {
        return new HttpClient() {
            @Override
            public CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
                if (httpRequest.getUrl().contains("version=5")) {
                    return CompletableFuture.completedFuture(HttpResponse.of(409, "{\n" +
                            "  \"statusCode\" : 409,\n" +
                            "  \"message\" : \"Version mismatch. Concurrent modification.\",\n" +
                            "  \"errors\" : [ {\n" +
                            "    \"code\" : \"ConcurrentModification\",\n" +
                            "    \"message\" : \"Version mismatch. Concurrent modification.\",\n" +
                            "    \"currentVersion\" : 7\n" +
                            "  } ]\n" +
                            "}"));
                } else if (httpRequest.getUrl().contains("version=7")) {
                    return CompletableFuture.completedFuture(HttpResponse.of(200, "{\"id\": \"some-id\", \"version\": 7}"));
                } else {
                    throw new IllegalArgumentException("unexpected input");
                }
            }

            @Override
            public void close() {

            }
        };
    }

    private HttpClient getHttpClient() {
        return new HttpClient() {
            private final AtomicInteger counter = new AtomicInteger(0);

            @Override
            public CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
                final int counterValue = counter.getAndIncrement();
                final List<Integer> statusCodes = asList(BAD_GATEWAY_502, SERVICE_UNAVAILABLE_503, GATEWAY_TIMEOUT_504);
                final int statusCode = counterValue >= statusCodes.size() ? OK_200 : statusCodes.get(counterValue);
                return CompletableFuture.completedFuture(HttpResponse.of(statusCode, "{\"key\": \"foo\"}"));
            }

            @Override
            public void close() {

            }
        };
    }
}