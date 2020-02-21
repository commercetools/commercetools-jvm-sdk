package io.sphere.sdk.client;

import io.sphere.sdk.http.AsyncHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.junit.Test;

import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.http.HttpMethod.GET;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomClientConfigDemoIntegrationTest {
    /**
     * Creates a {@link HttpClient} that can be used by the {@link SphereClient}.
     * @return new http client with custom settings
     */
    public static HttpClient createCustomHttpClient() {
        final AsyncHttpClientConfig httpClientConfig = new DefaultAsyncHttpClientConfig.Builder()
                .setEnabledProtocols(new String[]{"TLSv1.2"})//required
                //examples for configuration
                .setMaxConnections(500)
                .setConnectTimeout(10000)
//                .setProxyServer(proxy)
                .build();
        final AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient(httpClientConfig);
        return AsyncHttpClientAdapter.of(asyncHttpClient);
    }

    /**
     * Shows the initialization of a {@link SphereClient} with a custom {@link HttpClient} provider.
     */
    private void demoCreateClient() {
        final SphereClient sphereClient = SphereClientFactory.of(CustomClientConfigDemoIntegrationTest::createCustomHttpClient)
                .createClient("your projectKey", "your clientId", "your clientSecret");
    }

    @Test
    public void customClient() {
        final HttpClient httpClient = createCustomHttpClient();
        final CompletionStage<HttpResponse> completionStage =
                httpClient.execute(HttpRequest.of(GET, "https://commercetools.com"));
        final HttpResponse httpResponse = completionStage.toCompletableFuture().join();
        assertThat(httpResponse.getStatusCode()).isLessThanOrEqualTo(302);
        httpClient.close();
    }
}
