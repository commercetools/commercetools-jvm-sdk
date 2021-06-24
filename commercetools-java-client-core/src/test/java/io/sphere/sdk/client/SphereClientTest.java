package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpHeaders;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.utils.CompletableFutureUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link SphereClient}.
 */
@RunWith(MockitoJUnitRunner.class)
public class SphereClientTest {
    @Mock
    private HttpClient httpClient;
    @Mock
    private SphereApiConfig sphereApiConfig;
    @Mock
    private SphereAccessTokenSupplier sphereAccessTokenSupplier;
    @Captor
    private ArgumentCaptor<HttpRequest> httpRequestArgumentCaptor;

    @Test
    public void shouldSendCorrelationId() throws ExecutionException, InterruptedException {
        final String projectKey = "my-project";

        when: {
            when(sphereApiConfig.getProjectKey()).thenReturn(projectKey);
            when(sphereApiConfig.getApiUrl()).thenReturn("http://api.commercetools.de");
            when(sphereApiConfig.getCorrelationIdGenerator()).thenReturn(CorrelationIdGenerator.of(projectKey));
            when(httpClient.getUserAgent()).thenReturn("user-agent");
            when(sphereAccessTokenSupplier.get()).thenReturn(CompletableFutureUtils.successful("token"));

            when(httpClient.execute(httpRequestArgumentCaptor.capture()))
                    .thenReturn(CompletableFuture.completedFuture(HttpResponse.of(200, "ok")));
        }
        then:
        {
            final SphereClient sphereClient = SphereClient.of(sphereApiConfig, httpClient, sphereAccessTokenSupplier);
            final DummySphereRequest sphereRequest = DummySphereRequest.of();

            final CompletableFuture<String> completionStage = (CompletableFuture<String>) sphereClient.execute(sphereRequest);
            final String response = completionStage.get();
            assertThat(response).isEqualTo(DummySphereRequest.DEFAULT_RESPONSE_OBJECT);

            final HttpHeaders headers = httpRequestArgumentCaptor.getValue().getHeaders();
            final Optional<String> correlationId = headers.findFlatHeader(HttpHeaders.X_CORRELATION_ID);

            assertThat(correlationId).isPresent();

            final List<String> correlationIdParts = Arrays.asList(correlationId.get().split("/"));

            assertThat(correlationIdParts).hasSize(2);
            assertThat(correlationIdParts.get(0)).isEqualTo(projectKey);

            final UUID uuid = UUID.fromString(correlationIdParts.get(1));

            assertThat(uuid).isNotNull();
        }
    }

    @Test
    public void shouldUseCustomGenerator() throws ExecutionException, InterruptedException  {
        final String projectKey = "my-project";

        when: {
            when(sphereApiConfig.getProjectKey()).thenReturn(projectKey);
            when(sphereApiConfig.getApiUrl()).thenReturn("http://api.commercetools.de");
            when(sphereApiConfig.getCorrelationIdGenerator()).thenReturn(() -> "custom-id");
            when(httpClient.getUserAgent()).thenReturn("user-agent");
            when(sphereAccessTokenSupplier.get()).thenReturn(CompletableFutureUtils.successful("token"));

            when(httpClient.execute(httpRequestArgumentCaptor.capture()))
                    .thenReturn(CompletableFuture.completedFuture(HttpResponse.of(200, "ok")));
        }
        then: {
            final SphereClient sphereClient = SphereClient.of(sphereApiConfig, httpClient, sphereAccessTokenSupplier);
            final DummySphereRequest sphereRequest = DummySphereRequest.of();

            final CompletableFuture<String> completionStage = (CompletableFuture<String>) sphereClient.execute(sphereRequest);
            final String response = completionStage.get();
            assertThat(response).isEqualTo(DummySphereRequest.DEFAULT_RESPONSE_OBJECT);

            final HttpHeaders headers = httpRequestArgumentCaptor.getValue().getHeaders();
            final Optional<String> correlationId = headers.findFlatHeader(HttpHeaders.X_CORRELATION_ID);

            assertThat(correlationId).isPresent().contains("custom-id");
        }
    }
}
