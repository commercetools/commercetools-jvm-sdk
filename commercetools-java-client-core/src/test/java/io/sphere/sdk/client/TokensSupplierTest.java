package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpHeaders;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.utils.CompletableFutureUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link TokensSupplier}.
 */
@RunWith(MockitoJUnitRunner.class)
public class TokensSupplierTest {
    final private String startWithUserAgent = "commercetools-sdk-java-v1/";
    @Mock
    private HttpClient httpClient;

    @Captor
    private ArgumentCaptor<HttpRequest> requestCaptor;

    private SphereAuthConfig authConfig = SphereAuthConfig.of("test-project", "client-id", "client-srecret");
    private TokensSupplier tokensSupplier;

    @Before
    public void setup() {
        tokensSupplier = TokensSupplier.of(authConfig, httpClient, false);
    }

    @Test
    public void shouldSendCorrelationId() throws Exception {
        final CompletableFuture<HttpResponse> successful = CompletableFutureUtils
                .successful(HttpResponse.of(200, "{\"access_token\": \"access_token\"}"));
        when(httpClient.execute(requestCaptor.capture())).thenReturn(successful);

        tokensSupplier.get().toCompletableFuture().get();

        final HttpRequest httpRequest = requestCaptor.getValue();
        final Optional<String> correlationIdHeader = httpRequest.getHeaders().findFlatHeader(HttpHeaders.X_CORRELATION_ID);
        assertThat(correlationIdHeader).isPresent();
        final String correlationId = correlationIdHeader.get();
        final String[] correlationIdParts = correlationId.split("/");
        assertThat(correlationIdParts).hasSize(2);
        assertThat(correlationIdParts[0]).isEqualTo(authConfig.getProjectKey());
    }

    @Test
    public void shouldSendUserAgent() throws Exception {
        final CompletableFuture<HttpResponse> successful = CompletableFutureUtils
                .successful(HttpResponse.of(200, "{\"access_token\": \"access_token\"}"));
        when(httpClient.execute(requestCaptor.capture())).thenReturn(successful);

        tokensSupplier.get().toCompletableFuture().get();

        final HttpRequest httpRequest = requestCaptor.getValue();
        final Optional<String> userAgentHeader = httpRequest.getHeaders().findFlatHeader(HttpHeaders.USER_AGENT);
        assertThat(userAgentHeader).isPresent();
        final String userAgent = userAgentHeader.get();
        assertThat(userAgent).startsWith(startWithUserAgent);
    }

    @Test
    public void shouldSendUserAgentWithSolution() throws Exception {
        final CompletableFuture<HttpResponse> successful = CompletableFutureUtils
                .successful(HttpResponse.of(200, "{\"access_token\": \"access_token\"}"));
        when(httpClient.execute(requestCaptor.capture())).thenReturn(successful);

        SolutionInfo info = new SolutionInfo();
        info.setEmergencyContact("John Doe");
        info.setName("sdk-test");
        info.setVersion("1.2.3");
        info.setWebsite("example.com");
        TokensSupplier.of(authConfig, httpClient, false, Collections.singletonList(info)).get().toCompletableFuture().get();

        final HttpRequest httpRequest = requestCaptor.getValue();
        final Optional<String> userAgentHeader = httpRequest.getHeaders().findFlatHeader(HttpHeaders.USER_AGENT);
        assertThat(userAgentHeader).isPresent();
        final String userAgent = userAgentHeader.get();
        assertThat(userAgent).startsWith(startWithUserAgent);
        assertThat(userAgent).endsWith(" sdk-test/1.2.3 (+example.com; +John Doe)");
    }
}
