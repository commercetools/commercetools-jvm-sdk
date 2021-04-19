package io.sphere.sdk.client.retry;

import io.sphere.sdk.client.*;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.commands.CustomerUpdateCommand;
import io.sphere.sdk.customers.commands.updateactions.ChangeEmail;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.HttpStatusCode;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.retry.RetryAction;
import io.sphere.sdk.retry.RetryPredicate;
import io.sphere.sdk.retry.RetryRule;

import org.junit.Ignore;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static io.sphere.sdk.client.TestDoubleSphereClientFactory.createHttpTestDouble;
import static io.sphere.sdk.client.retry.RetryableSphereClientBuilder.DEFAULT_INITIAL_RETRY_DELAY;
import static io.sphere.sdk.client.retry.RetryableSphereClientBuilder.DEFAULT_MAX_DELAY;
import static io.sphere.sdk.client.retry.RetryableSphereClientBuilder.DEFAULT_MAX_PARALLEL_REQUESTS;
import static io.sphere.sdk.client.retry.RetryableSphereClientBuilder.DEFAULT_MAX_RETRY_ATTEMPT;
import static io.sphere.sdk.http.HttpStatusCode.BAD_GATEWAY_502;
import static io.sphere.sdk.http.HttpStatusCode.GATEWAY_TIMEOUT_504;
import static io.sphere.sdk.http.HttpStatusCode.SERVICE_UNAVAILABLE_503;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class RetryableSphereClientBuilderTest {

    @Test
    public void of_WithClientConfigAndDefaults_ReturnsSphereClient() {
        final SphereClientConfig clientConfig =
                SphereClientConfig.of("project-key", "client-id", "client-secret");
        final SphereClient sphereClient =
            RetryableSphereClientBuilder.of(clientConfig, mock(HttpClient.class)).build();

        assertThat(sphereClient.getConfig().getProjectKey()).isEqualTo("project-key");
    }

    @Test
    public void of_WithClientConfigAndOtherConfigValues_ReturnsSphereClient() {
        final SphereClientConfig clientConfig =
                SphereClientConfig.of("project-key", "client-id", "client-secret");

        final SphereClient sphereClient = RetryableSphereClientBuilder
                .of(clientConfig, mock(HttpClient.class))
                .withMaxDelay(DEFAULT_MAX_DELAY)
                .withInitialDelay(DEFAULT_INITIAL_RETRY_DELAY)
                .withMaxRetryAttempt(DEFAULT_MAX_RETRY_ATTEMPT)
                .withMaxParallelRequests(DEFAULT_MAX_PARALLEL_REQUESTS)
                .withStatusCodesToRetry(Arrays.asList(500, 502, 503, 504))
                .build();

        assertThat(sphereClient.getConfig().getProjectKey()).isEqualTo("project-key");
    }

    @Test
    public void of_WithInitialDelayGreaterThanMaxDelay_ThrowsIllegalArgumentException() {
        final SphereClientConfig clientConfig =
            SphereClientConfig.of("project-key", "client-id", "client-secret");

        assertThatThrownBy(() -> RetryableSphereClientBuilder
            .of(clientConfig, mock(HttpClient.class))
            .withMaxDelay(1)
            .withInitialDelay(2).build())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void of_WithMaxRetryAttemptLessThanZero_ThrowsIllegalArgumentException() {
        final SphereClientConfig clientConfig =
            SphereClientConfig.of("project-key", "client-id", "client-secret");

        assertThatThrownBy(() -> RetryableSphereClientBuilder
            .of(clientConfig, mock(HttpClient.class))
            .withMaxRetryAttempt(-1).build())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void of_WithMaxParallelRequestsLessThanOne_ThrowsIllegalArgumentException() {
        final SphereClientConfig clientConfig =
            SphereClientConfig.of("project-key", "client-id", "client-secret");

        assertThatThrownBy(() -> RetryableSphereClientBuilder
            .of(clientConfig, mock(HttpClient.class))
            .withMaxParallelRequests(0).build())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void of_withRetryDecorator_ShouldRetryWhen500HttpResponse() {
        //preparation
        final long maxRetryAttempt = 1L;
        final RetryAction retryAction = RetryAction.ofExponentialBackoff(maxRetryAttempt, 1000, 1);

        final FakeUnderlyingClient fakeUnderlyingClient = FakeUnderlyingClient.of(HttpStatusCode.INTERNAL_SERVER_ERROR_500);
        final RetryableSphereClientBuilder client = fakeUnderlyingClient.toRetryClient();

        final SphereClient decoratedSphereClient = client.decorateSphereClient(
            fakeUnderlyingClient.getSphereClient(), retryAction, DEFAULT_MAX_PARALLEL_REQUESTS);

        final CustomerUpdateCommand customerUpdateCommand = getCustomerUpdateCommand();

        assertThatThrownBy(() -> decoratedSphereClient.execute(customerUpdateCommand).toCompletableFuture().get())
                .isInstanceOf(ExecutionException.class)
                .hasCauseExactlyInstanceOf(InternalServerErrorException.class)
                .hasMessageContaining("500");

        // first request + retry.
        assertThat(fakeUnderlyingClient.getRetryCount()).isEqualTo(2);
    }

    @Test
    public void of_withRetryDecorator_ShouldRetryWhen502HttpResponse() {
        //preparation
        final long maxRetryAttempt = 1L;
        final RetryAction retryAction = RetryAction.ofExponentialBackoff(maxRetryAttempt, 1000, 1);

        final FakeUnderlyingClient fakeUnderlyingClient = FakeUnderlyingClient.of(BAD_GATEWAY_502);
        final RetryableSphereClientBuilder client = fakeUnderlyingClient.toRetryClient();

        final SphereClient decoratedSphereClient = client.decorateSphereClient(
            fakeUnderlyingClient.getSphereClient(), retryAction, DEFAULT_MAX_PARALLEL_REQUESTS);

        final CustomerUpdateCommand customerUpdateCommand = getCustomerUpdateCommand();


        assertThatThrownBy(() -> decoratedSphereClient.execute(customerUpdateCommand).toCompletableFuture().get())
                .isInstanceOf(ExecutionException.class)
                .hasCauseExactlyInstanceOf(BadGatewayException.class)
                .hasMessageContaining("502");

        // first request + retry.
        assertThat(fakeUnderlyingClient.getRetryCount()).isEqualTo(2);
    }

    @Test
    public void createClient_stucked_on_duration_calculation() {
        final SphereClient mockSphereUnderlyingClient =
                createHttpTestDouble(intent -> HttpResponse.of(HttpStatusCode.BAD_GATEWAY_502));
        final int maxAttempts = 5;
        final List<RetryRule> retryRules = Collections.singletonList(RetryRule.of(
                RetryPredicate.ofMatchingStatusCodes(HttpStatusCode.BAD_GATEWAY_502),
                RetryAction.ofScheduledRetry(maxAttempts, context -> {
                    // durationFunction.apply() in retry action.
                    throw new IllegalArgumentException();
                }))
        );
        final SphereClient sphereClient = RetrySphereClientDecorator.of(mockSphereUnderlyingClient, retryRules);

        final CustomerUpdateCommand customerUpdateCommand = getCustomerUpdateCommand();

        assertThatThrownBy(() -> sphereClient.execute(customerUpdateCommand).toCompletableFuture().get())
                .isInstanceOf(ExecutionException.class)
                .hasCauseExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void of_withRetryDecorator_ShouldRetryWhen503HttpResponse() {
        //preparation
        final long maxRetryAttempt = 2L;
        final RetryAction retryAction = RetryAction.ofExponentialBackoff(maxRetryAttempt, 1000, 1);

        final FakeUnderlyingClient fakeUnderlyingClient = FakeUnderlyingClient.of(SERVICE_UNAVAILABLE_503);
        final RetryableSphereClientBuilder client = fakeUnderlyingClient.toRetryClient();

        final SphereClient decoratedSphereClient = client.decorateSphereClient(
            fakeUnderlyingClient.getSphereClient(), retryAction, DEFAULT_MAX_PARALLEL_REQUESTS);

        final CustomerUpdateCommand customerUpdateCommand = getCustomerUpdateCommand();

        // test and assert
        assertThatThrownBy(() -> decoratedSphereClient.execute(customerUpdateCommand).toCompletableFuture().get())
                .isInstanceOf(ExecutionException.class)
                .hasCauseExactlyInstanceOf(ServiceUnavailableException.class)
                .hasMessageContaining("503");

        // first request + retries
        assertThat(fakeUnderlyingClient.getRetryCount()).isEqualTo(3);
    }

    @Test
    public void of_withRetryDecorator_ShouldRetryWhen504HttpResponse() {
        //preparation
        final long maxRetryAttempt = 3L;
        final RetryAction retryAction = RetryAction.ofExponentialBackoff(maxRetryAttempt, 1000, 1);

        final FakeUnderlyingClient fakeUnderlyingClient = FakeUnderlyingClient.of(GATEWAY_TIMEOUT_504);
        final RetryableSphereClientBuilder client = fakeUnderlyingClient.toRetryClient();

        final SphereClient decoratedSphereClient = client.decorateSphereClient(
            fakeUnderlyingClient.getSphereClient(), retryAction, DEFAULT_MAX_PARALLEL_REQUESTS);

        final CustomerUpdateCommand customerUpdateCommand = getCustomerUpdateCommand();

        // test and assert
        assertThatThrownBy(() -> decoratedSphereClient.execute(customerUpdateCommand).toCompletableFuture().get())
            .isInstanceOf(ExecutionException.class)
            .hasCauseExactlyInstanceOf(GatewayTimeoutException.class)
            .hasMessageContaining("504");

        // first request + retries
        assertThat(fakeUnderlyingClient.getRetryCount()).isEqualTo(4);
    }

    @Test
    public void of_withRetryDecorator_ShouldNotRetryWhen400HttpResponse() {
        //preparation
        final long maxRetryAttempt = 2L;
        final RetryAction retryAction = RetryAction.ofExponentialBackoff(maxRetryAttempt, 1000, 1);

        final FakeUnderlyingClient fakeUnderlyingClient = FakeUnderlyingClient.of(HttpStatusCode.BAD_REQUEST_400, "{\"statusCode\":\"400\"}");
        final RetryableSphereClientBuilder client = fakeUnderlyingClient.toRetryClient();

        final SphereClient decoratedSphereClient = client.decorateSphereClient(
            fakeUnderlyingClient.getSphereClient(), retryAction, DEFAULT_MAX_PARALLEL_REQUESTS);

        final CustomerUpdateCommand customerUpdateCommand = getCustomerUpdateCommand();

        assertThatThrownBy(() -> decoratedSphereClient.execute(customerUpdateCommand).toCompletableFuture().get())
            .isInstanceOf(ExecutionException.class)
            .hasCauseExactlyInstanceOf(ErrorResponseException.class)
            .hasMessageContaining("400");

        // No retry, only first request.
        assertThat(fakeUnderlyingClient.getRetryCount()).isEqualTo(1);
    }

    @Test
    public void calculateExponentialRandomBackoff_withRetries_ShouldReturnRandomisedDurations() {
        long maxDelay = 0;
        for (long failedRetryAttempt = 1; failedRetryAttempt <= 10; failedRetryAttempt++) {

            maxDelay += DEFAULT_INITIAL_RETRY_DELAY * ((long) Math.pow(2, failedRetryAttempt - 1)) * 2;

            /* One example of wait times of retries:
            Retry 1: 226 millisecond
            Retry 2: 788 millisecond
            Retry 3: 1214 millisecond
            Retry 4: 2135 millisecond
            Retry 5: 3332 millisecond
            Retry 6: 8662 millisecond
            Retry 7: 24898 millisecond
            Retry 8: 28659 millisecond
            Retry 9: 60000 millisecond
            Retry 10: 60000 millisecond
            */
            final Duration duration =
                RetryAction.calculateDurationWithExponentialRandomBackoff(
                            failedRetryAttempt, DEFAULT_INITIAL_RETRY_DELAY, DEFAULT_MAX_DELAY);

            assertThat(duration.toMillis())
                .isLessThanOrEqualTo(maxDelay)
                .isLessThanOrEqualTo(DEFAULT_MAX_DELAY);
        }
    }

    private RetryableSphereClientBuilder of_RetryableSphereClientWithExponentialBackoff() {
        final SphereClientConfig clientConfig =
            SphereClientConfig.of("project-key", "client-id", "client-secret");
        return RetryableSphereClientBuilder.of(clientConfig, mock(HttpClient.class));
    }

    private CustomerUpdateCommand getCustomerUpdateCommand() {
        final List<UpdateAction<Customer>> updateActions = singletonList(ChangeEmail.of(""));
        return CustomerUpdateCommand.of(
            Versioned.of(UUID.randomUUID().toString(), 1L),
            updateActions
        );
    }

    private final static class FakeUnderlyingClient implements HttpClient {
        private final AtomicInteger retryCounter = new AtomicInteger(0);
        private final int statusCode;
        private final String responseBody;

        private FakeUnderlyingClient(int statusCode, String responseBody) {
            this.statusCode = statusCode;
            this.responseBody = responseBody;
        }

        public static FakeUnderlyingClient of(int statusCode) {
            return new FakeUnderlyingClient(statusCode, "");
        }

        public static FakeUnderlyingClient of(int statusCode, String responseBody) {
            return new FakeUnderlyingClient(statusCode, responseBody);
        }

        public SphereClient getSphereClient() {
            return SphereClient.of(SphereApiConfig.of("project-key"), this,
                SphereAccessTokenSupplier.ofConstantToken("accessToken"));
        }

        public int getRetryCount() {
            return retryCounter.get();
        }

        @Override
        public CompletionStage<HttpResponse> execute(HttpRequest httpRequest) {
            retryCounter.getAndIncrement();
            return CompletableFuture.completedFuture(HttpResponse.of(statusCode, responseBody));
        }

        @Override
        public void close() {
        }

        private RetryableSphereClientBuilder toRetryClient() {
            final SphereClientConfig clientConfig =
                SphereClientConfig.of("project-key", "client-id", "client-secret");
            return RetryableSphereClientBuilder.of(clientConfig, this);
        }
    }
}
