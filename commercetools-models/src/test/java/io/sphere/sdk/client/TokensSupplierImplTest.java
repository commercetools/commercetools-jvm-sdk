package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpHeaders;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.meta.BuildInfo;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.concurrent.CompletionStage;

import static org.assertj.core.api.Assertions.assertThat;

public class TokensSupplierImplTest {
    @Test
    public void httpRequestsContainUserAgents() throws Exception {
        final SphereAuthConfig authConfig = SphereAuthConfig.of("a", "b", "c");
        final RecordHttpClient httpClient = new RecordHttpClient();
        SphereAccessTokenSupplier.ofOneTimeFetchingToken(authConfig, httpClient, true).get();
        final String expectedValue = UserAgentUtils.obtainUserAgent(httpClient);
        final String header = httpClient.request.getHeaders().findFlatHeader(HttpHeaders.USER_AGENT).orElse("");
        assertThat(header).matches("^(?<sdkOrClient>[^\\s\\/]+)(\\/(?<sdkOrClientVersion>\\S+))?(\\s+\\((?<sdkOrClientInfo>[^(]*)\\))?\\s+(?<environment>[^\\s\\/]+)(\\/(?<environmentVersion>\\S+))?(\\s+\\((?<environmentInfo>[^(]*)\\))?\\s+(?<solution>[^\\s\\/]+)(\\/(?<solutionVersion>\\S+))?(\\s+\\((?<solutionInfo>[^(]*)\\))?$").contains(expectedValue);
    }

    private static class RecordHttpClient implements HttpClient {
        private final HttpClient httpClient = SphereClientFactory.of().createHttpClient();

        HttpRequest request;

        @Override
        public void close() {
            httpClient.close();
        }

        @Nullable
        @Override
        public String getUserAgent() {
            return httpClient.getUserAgent();
        }

        @Override
        public CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
            this.request = httpRequest;
            return httpClient.execute(httpRequest);
        }
    }
}