package io.sphere.sdk.client;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import io.sphere.sdk.http.*;
import org.junit.Test;

import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.http.HttpMethod.GET;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomClientConfigDemoTest {
    /**
     * Creates a {@link HttpClient} that can be used by the {@link SphereClient}.
     * @return new http client with custom settings
     */
    public static HttpClient createCustomHttpClient() {
        final AsyncHttpClientConfig httpClientConfig = new AsyncHttpClientConfig.Builder()
                .setEnabledProtocols(new String[]{"TLSv1.2"})//required
                //examples for configuration
                .setMaxConnections(500)
                .setConnectTimeout(100)
//                .setProxyServer(proxy)
                .build();
        final AsyncHttpClient asyncHttpClient = new AsyncHttpClient(httpClientConfig);
        return AsyncHttpClientAdapter.of(asyncHttpClient);
    }

    /**
     * Shows the initialization of a {@link SphereClient} with a custom {@link HttpClient} provider.
     */
    private void demoCreateClient() {
        final SphereClient sphereClient = SphereClientFactory.of(CustomClientConfigDemoTest::createCustomHttpClient)
                .createClient("your projectKey", "your clientId", "your clientSecret");
    }

    @Test
    public void customClient() {
        final HttpClient httpClient = createCustomHttpClient();
        final CompletionStage<HttpResponse> completionStage =
                httpClient.execute(HttpRequest.of(GET, "https://auth.sphere.io"));
        final HttpResponse httpResponse = completionStage.toCompletableFuture().join();
        assertThat(httpResponse.getStatusCode()).isEqualTo(200);
    }
}
