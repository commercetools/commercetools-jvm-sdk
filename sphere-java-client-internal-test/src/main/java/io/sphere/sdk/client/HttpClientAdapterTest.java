package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public abstract class HttpClientAdapterTest {

    protected abstract HttpClient createClient();

    @Test
    public final void testConnection() {
        final HttpClient client = createClient();
        final HttpResponse response = client.execute(HttpRequest.of(HttpMethod.GET, "http://sphere.io")).toCompletableFuture().join();
        client.close();
        final String body = new String(response.getResponseBody().get());
        assertThat(response.getStatusCode()).isLessThan(400);
        assertThat(body).containsIgnoringCase("commercetools");
    }
}
