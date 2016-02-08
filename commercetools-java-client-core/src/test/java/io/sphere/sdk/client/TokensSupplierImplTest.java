package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpHeaders;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.meta.BuildInfo;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.assertj.core.api.Assertions.*;

public class TokensSupplierImplTest {
    @Test
    public void httpRequestsContainUserAgents() throws Exception {
        final SphereAuthConfig authConfig = SphereAuthConfig.of("a", "b", "c");
        final RecordHttpClient httpClient = new RecordHttpClient();
        SphereAccessTokenSupplier.ofOneTimeFetchingToken(authConfig, httpClient, true).get();
        final String expectedValue = BuildInfo.userAgent();
        assertThat(httpClient.request.getHeaders().findFlatHeader(HttpHeaders.USER_AGENT))
                .contains(expectedValue);
        assertThat(expectedValue)
                .contains("JVM")
                .contains("SPHERE");
    }

    private static class RecordHttpClient implements HttpClient {
        HttpRequest request;

        @Override
        public void close() {

        }

        @Override
        public CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
            this.request = httpRequest;
            return CompletableFuture.completedFuture(HttpResponse.of(200));
        }
    }
}